package com.rednavis.vaadin.showcase.backend.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.aeonbits.owner.Config.HotReload;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@HotReload
@LoadPolicy(LoadType.MERGE)
@Sources( {"classpath:ServerConfig.properties"})
public @interface GlobalConfig {

}
