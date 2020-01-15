package com.rednavis.vaadin.showcase.backend.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

/**
 * Super interface for all config interfaces based on the same @Sources({"..."})
 */
@LoadPolicy(LoadType.MERGE)
@Sources({"classpath:server_config.properties"})
public interface UniformSourceConfig extends Config {

}