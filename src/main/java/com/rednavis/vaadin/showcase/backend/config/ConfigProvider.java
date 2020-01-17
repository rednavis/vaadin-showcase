package com.rednavis.vaadin.showcase.backend.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import org.aeonbits.owner.ConfigFactory;

@ApplicationScoped
public class ConfigProvider {

  @Produces
  @Singleton
  public PasswordConfig producePasswordConfig() {
    PasswordConfig passwordConfig = ConfigFactory.create(PasswordConfig.class);
    return passwordConfig;
  }
}
