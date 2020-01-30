package com.rednavis.vaadin.showcase.backend.db;

import com.rednavis.vaadin.showcase.backend.config.PostgreSqlConfig;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

@Getter
@Slf4j
@Singleton
public class Dbi {

  private Jdbi jdbi;

  @Inject
  public Dbi(PostgreSqlConfig postgreSqlConfig) {
    jdbi = Jdbi.create(postgreSqlConfig.getUri(), postgreSqlConfig.getUserName(), postgreSqlConfig.getPassword());
    jdbi.installPlugin(new SqlObjectPlugin());
  }

  //It's only for test don't use in real work
  public void reconfigureJdbi(String connectionUri, String username, String passwrod) {
    log.info("connectionUri = {}, username = {}, passwrod = {}", connectionUri, username, passwrod);
    jdbi = Jdbi.create(connectionUri, username, passwrod);
    jdbi.installPlugin(new SqlObjectPlugin());
  }
}
