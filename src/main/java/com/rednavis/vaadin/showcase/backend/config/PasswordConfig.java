package com.rednavis.vaadin.showcase.backend.config;

import java.util.concurrent.TimeUnit;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.HotReload;
import org.aeonbits.owner.Config.HotReloadType;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

// Password validation
@HotReload(value = 1, unit = TimeUnit.MINUTES, type = HotReloadType.ASYNC)
@LoadPolicy(LoadType.MERGE)
@Sources( {"classpath:ServerConfig.properties"})
public interface PasswordConfig extends Config {

  @Key("userPasswordComplexity.minLength")
  int getMinLength();

  @Key("userPasswordComplexity.maxLength")
  int getMaxLength();

  @Key("userPasswordComplexity.whitespaceAllowed")
  boolean isWhitespaceAllowed();

  @Key("userPasswordComplexity.requireLeastNumberOfDigits")
  int getRequireLeastNumberOfDigits();

  @Key("userPasswordComplexity.requireLeastNumberOfAlphabeticals")
  int getRequireLeastNumberOfAlphabeticals();

  @Key("userPasswordComplexity.requireLeastNumberOfNotAlphanumerics")
  int getRequireLeastNumberOfNotAlphanumerics();

  @Key("userPasswordComplexity.requireLeastNumberOfLowercases")
  int getRequireLeastNumberOfLowercases();

  @Key("userPasswordComplexity.requireLeastNumberOfUppercases")
  int getRequireLeastNumberOfUppercases();

  @Key("userPasswordComplexity.allowAlphabeticalSequence")
  boolean isAllowAlphabeticalSequence();

  @Key("userPasswordComplexity.allowNumericalSequence")
  boolean isAllowNumericalSequence();

  @Key("userPasswordComplexity.allowQwertySequence")
  boolean isAllowQwertySequence();

  @Key("userPasswordComplexity.allowRepeatCharactersMax")
  int getAllowRepeatCharactersMax();
}