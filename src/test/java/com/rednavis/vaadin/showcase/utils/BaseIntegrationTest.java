package com.rednavis.vaadin.showcase.utils;

import com.rednavis.vaadin.showcase.backend.config.ConfigProvider;
import com.rednavis.vaadin.showcase.backend.db.Dbi;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;

/**
 * BaseIntegrationTest.
 */
@Slf4j
@EnableAutoWeld
@AddBeanClasses({ConfigProvider.class, Dbi.class})
public abstract class BaseIntegrationTest {

  private static final int EXPOSED_PORT = 5432;
  private static final String TEST_DATABASE_NAME = "test";
  private static final String TEST_ROOT_USER_NAME = "root";
  private static final String TEST_ROOT_PASSWORD = "root";
  private static final String SCRIPT_LOCATION = "classpath:db/migration";
  private static final GenericContainer<?> DB_CONTAINER = new GenericContainer<>("postgres:12.1")
      .withExposedPorts(EXPOSED_PORT)
      .withEnv("POSTGRES_DB", TEST_DATABASE_NAME)
      .withEnv("POSTGRES_USER", TEST_ROOT_USER_NAME)
      .withEnv("POSTGRES_PASSWORD", TEST_ROOT_PASSWORD)
      .waitingFor(new HostPortWaitStrategy());

  @Inject
  private Dbi dbi;
  
  static {
    startDbContainer();
  }

  @BeforeEach
  public void beforeEach() {
    reconfiguringJdbi();
    cleanDataBase();
  }

  private static void startDbContainer() {
    log.info("Starting the PostgreSql server");
    DB_CONTAINER.start();
    log.info("PostgreSql server started with host {} and port {}", getHost(), getPort());
  }

  private void reconfiguringJdbi() {
    log.info("Reconfiguring Jdbi");
    dbi.reconfigureJdbi(getUrl(), TEST_ROOT_USER_NAME, TEST_ROOT_PASSWORD);
  }

  private static void cleanDataBase() {
    log.info("Clean DataBase");
    FluentConfiguration fluentConfiguration = Flyway.configure()
        .locations(SCRIPT_LOCATION)
        .dataSource(getUrl(), TEST_ROOT_USER_NAME, TEST_ROOT_PASSWORD);
    Flyway flyway = new Flyway(fluentConfiguration);
    flyway.clean();
    flyway.migrate();
  }
  
  private static String getHost() {
    return DB_CONTAINER.getContainerIpAddress();
  }

  private static int getPort() {
    return DB_CONTAINER.getMappedPort(EXPOSED_PORT);
  }

  private static String getUrl() {
    return "jdbc:postgresql://" + getHost() + ":" + getPort() + "/" + TEST_DATABASE_NAME;
  }
}
