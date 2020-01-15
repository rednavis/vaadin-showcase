package com.rednavis.vaadin.showcase.backend.config;

/**
 * Interface representing PostgreSql database config properties.
 *
 * @see UniformSourceConfig
 */
public interface PostgreSqlConfig extends UniformSourceConfig {

  @Key("postgresql.jdbi.connection.uri")
  String getUri();

  @Key("postgresql.jdbi.connection.userName")
  String getUserName();

  @Key("postgresql.jdbi.connection.password")
  String getPassword();
}