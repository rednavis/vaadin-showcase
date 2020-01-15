package com.rednavis.vaadin.showcase.backend.db;

import com.rednavis.vaadin.showcase.backend.config.ConfigManager;
import com.rednavis.vaadin.showcase.backend.config.PostgreSqlConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

@Getter
@Slf4j
public class Dbi {

  private static volatile Dbi INSTANCE;
  private Jdbi jdbi;

  private Dbi() {
    PostgreSqlConfig db = ConfigManager.instance().get(PostgreSqlConfig.class);
    jdbi = Jdbi.create(db.getUri(), db.getUserName(), db.getPassword());
    jdbi.installPlugin(new SqlObjectPlugin());
  }

  public static Dbi instance() {
    if (INSTANCE == null) {
      synchronized (Dbi.class) {
        if (INSTANCE == null) {
          INSTANCE = new Dbi();
        }
      }
    }
    return INSTANCE;
  }

  //It's only for test don't use in real work
  public void reconfigureJdbi(String connectionUri, String username, String passwrod) {
    log.info("connectionUri = {}, username = {}, passwrod = {}", connectionUri, username, passwrod);
    jdbi = Jdbi.create(connectionUri, username, passwrod);
    jdbi.installPlugin(new SqlObjectPlugin());
  }
}
