package com.rednavis.vaadin.showcase.rule.testcontainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.rules.ExternalResource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;

@Slf4j
public class PostgreSqlDbRule extends ExternalResource {
  
  private static final int EXPOSED_PORT = 5432;
  private static final String TEST_DATABASE_NAME = "testDb";
  private static final String TEST_ROOT_USER_NAME = "rootUser";
  private static final String TEST_ROOT_PASSWORD = "rootPass";

  private final GenericContainer<?> db;
  private final String databaseName;
  private final String rootUserName;
  private final String rootPassword;

  public PostgreSqlDbRule() {
    this(TEST_DATABASE_NAME, TEST_ROOT_USER_NAME, TEST_ROOT_PASSWORD);
  }

  /**
   * PostgreSqlDbRule.
   *
   * @param databaseName databaseName
   * @param rootUserName rootUserName
   * @param rootPassword rootPassword
   */
  public PostgreSqlDbRule(String databaseName, String rootUserName, String rootPassword) {
    this.databaseName = databaseName;
    this.rootUserName = rootUserName;
    this.rootPassword = rootPassword;
    this.db = new GenericContainer<>("postgres:12.1")
        .withExposedPorts(EXPOSED_PORT)
        .withEnv("POSTGRES_PASSWORD", rootPassword)
        .withEnv("POSTGRES_USER", rootUserName)
        .withEnv("POSTGRES_DB", databaseName)
        .waitingFor(new HostPortWaitStrategy());
  }

  @Override
  public void before() {
    log.info("Starting the PostgreSql server");
    db.start();
    log.info("PostgreSql server started with host {} and port {}", getHost(), getPort());
  }

  @Override
  public void after() {
    log.info("Stopping PostgreSql server");
    db.stop();
    log.info("PostgreSql server stopped");
  }

  public String getHost() {
    return db.getContainerIpAddress();
  }

  public int getPort() {
    return db.getMappedPort(EXPOSED_PORT);
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