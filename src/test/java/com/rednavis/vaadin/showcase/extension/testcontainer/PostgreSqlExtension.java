package com.rednavis.vaadin.showcase.extension.testcontainer;

import com.rednavis.vaadin.showcase.backend.db.Dbi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;

@Slf4j
public class PostgreSqlExtension implements BeforeEachCallback, BeforeAllCallback, AfterEachCallback, AfterAllCallback {
  
  private static final int EXPOSED_PORT = 5432;
  private static final String TEST_DATABASE_NAME = "test";
  private static final String TEST_ROOT_USER_NAME = "root";
  private static final String TEST_ROOT_PASSWORD = "root";
  private static final String scriptLocation = "classpath:db/migration";

  private final GenericContainer<?> dbContainer;
  private final String databaseName;
  private final String rootUserName;
  private final String rootPassword;

  public PostgreSqlExtension() {
    this(TEST_DATABASE_NAME, TEST_ROOT_USER_NAME, TEST_ROOT_PASSWORD);
  }

  /**
   * PostgreSqlDbRule.
   *
   * @param databaseName databaseName
   * @param rootUserName rootUserName
   * @param rootPassword rootPassword
   */
  public PostgreSqlExtension(String databaseName, String rootUserName, String rootPassword) {
    this.databaseName = databaseName;
    this.rootUserName = rootUserName;
    this.rootPassword = rootPassword;
    this.dbContainer = new GenericContainer<>("postgres:12.1")
        .withExposedPorts(EXPOSED_PORT)
        .withEnv("POSTGRES_PASSWORD", rootPassword)
        .withEnv("POSTGRES_USER", rootUserName)
        .withEnv("POSTGRES_DB", databaseName)
        .waitingFor(new HostPortWaitStrategy());
  }

  @Override
  public void beforeEach(ExtensionContext extensionContext) {
    startDbContainer();
    reconfiguringJdbi();
    startMigration();
  }
  
  @Override
  public void beforeAll(ExtensionContext extensionContext) {
    startDbContainer();
    reconfiguringJdbi();
    startMigration();
  }

  @Override
  public void afterEach(ExtensionContext extensionContext) {
    stopDbContainer();
  }
  
  @Override
  public void afterAll(ExtensionContext extensionContext) {
    stopDbContainer();
  }
  
  private void startDbContainer() {
    log.info("Starting the PostgreSql server");
    dbContainer.start();
    log.info("PostgreSql server started with host {} and port {}", getHost(), getPort());
  }

  private void reconfiguringJdbi() {
    log.info("Reconfiguring Jdbi");
    Dbi.instance().reconfigureJdbi(getUrl(), rootUserName, rootPassword);
  }

  private void startMigration() {
    log.info("Starting migration");
    FluentConfiguration fluentConfiguration = Flyway.configure()
        .locations(scriptLocation)
        .dataSource(getUrl(), rootUserName, rootPassword);
    Flyway flyway = new Flyway(fluentConfiguration);
    flyway.clean();
    flyway.migrate();
  }

  private void stopDbContainer() {
    log.info("Stopping PostgreSql server");
    dbContainer.stop();
    log.info("PostgreSql server stopped");
  }

  public String getHost() {
    return dbContainer.getContainerIpAddress();
  }

  public int getPort() {
    return dbContainer.getMappedPort(EXPOSED_PORT);
  }

  /**
   * Create table.
   */
  public void createTable(String tableName, Map<String, String> column) throws SQLException {
    StringBuilder queryCreateTable = new StringBuilder("create table ");
    String columns = column.entrySet().stream()
        .map(entry -> entry.getKey() + " " + entry.getValue())
        .collect(Collectors.joining(",", "(", ")"));
    queryCreateTable.append(tableName).append(columns);

    executeQuery(queryCreateTable.toString());
  }

  /**
   * Drop table.
   */
  public void dropTable(String tableName) throws SQLException {
    String query = "drop table " + tableName;
    executeQuery(query);
  }

  /**
   * Execute query.
   */
  public int executeQuery(String query) throws SQLException {
    try (Connection connection = getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      return preparedStatement.executeUpdate();
    }
  }

  /**
   * Select query.
   */
  public ResultSet selectQuery(String query) throws SQLException {
    try (Connection connection = getConnection()) {
      PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
          ResultSet.CONCUR_UPDATABLE);
      return preparedStatement.executeQuery();
    }
  }

  private String getUrl() {
    return "jdbc:postgresql://" + getHost() + ":" + getPort() + "/" + databaseName;
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(getUrl(), rootUserName, rootPassword);
  }
}