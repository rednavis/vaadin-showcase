package com.rednavis.vaadin.showcase.backend.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import org.aeonbits.owner.ConfigFactory;

@ApplicationScoped
public class ConfigProvider {

  @Produces
  @Dependent
  public PostgreSqlConfig producePostgreSqlConfig() {
    return ConfigFactory.create(PostgreSqlConfig.class);
  }

  @Produces
  @Dependent
  public PasswordConfig producePasswordConfig() {
    return ConfigFactory.create(PasswordConfig.class);
  }
}
