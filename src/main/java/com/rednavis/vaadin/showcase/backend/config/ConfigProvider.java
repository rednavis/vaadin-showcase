package com.rednavis.vaadin.showcase.backend.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import org.aeonbits.owner.ConfigFactory;

@ApplicationScoped
public class ConfigProvider {

  @Produces
  @Singleton
  public PostgreSqlConfig producePostgreSqlConfig() {
    return ConfigFactory.create(PostgreSqlConfig.class);
  }
}