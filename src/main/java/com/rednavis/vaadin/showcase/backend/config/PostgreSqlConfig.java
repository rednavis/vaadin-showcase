package com.rednavis.vaadin.showcase.backend.config;

import java.util.concurrent.TimeUnit;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.HotReload;
import org.aeonbits.owner.Config.HotReloadType;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

@HotReload(value = 1, unit = TimeUnit.MINUTES, type = HotReloadType.ASYNC)
@LoadPolicy(LoadType.MERGE)
@Sources( {"classpath:ServerConfig.properties"})
public interface PostgreSqlConfig extends Config {

  @Key("postgresql.jdbi.connection.uri")
  String getUri();

  @Key("postgresql.jdbi.connection.userName")
  String getUserName();

  @Key("postgresql.jdbi.connection.password")
  String getPassword();
}