package com.rednavis.vaadin.showcase.backend.config;

import com.google.common.reflect.ClassPath;
import com.rednavis.vaadin.showcase.exception.InitConfigError;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

/**
 * Lazily loads configuration for application from properties files. Scan {@link Config} classes located in the same
 * package as current ConfigManager class that have no zero declared methods. For every found class use {@link
 * ConfigFactory#create(Class, Map[])} method to create config. Use internal _configMap to store created configs.
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigManager {

  private static final Object LOCK = new Object();
  private static ConfigManager INSTANCE;
  private Map<Class<? extends Config>, Config> _configMap;

  public static ConfigManager instance() {
    if (INSTANCE != null) {
      return INSTANCE;
    }
    synchronized (LOCK) {
      if (INSTANCE == null) {
        INSTANCE = new ConfigManager();
        INSTANCE.init();
      }
      return INSTANCE;
    }
  }

  /**
   * Returns the {@link Config} implementation by {@link Class}
   *
   * @param clazz the clazz name whose associated config is to be returned
   * @return the config implementation to which the specified clazz is mapped, or {@code null} if this map contains no
   * mapping for the clazz
   */
  @SuppressWarnings("unchecked")
  public <T extends Config> T get(Class<? extends T> clazz) {
    return (T) _configMap.get(clazz);
  }

  private void init() {
    Class parent = Config.class;
    Class me = ConfigManager.class;

    try {
      ClassPath classPath = ClassPath.from(me.getClassLoader());
      Set<ClassPath.ClassInfo> set = classPath.getTopLevelClassesRecursive(me.getPackage().getName());

      Map<Class<? extends Config>, Config> map = new HashMap<>();
      set.forEach(classInfo -> {
        Class clazz = classInfo.load();

        if (parent.isAssignableFrom(clazz) && clazz.getDeclaredMethods().length > 0) {
          map.put(clazz, ConfigFactory.create(clazz, System.getProperties(), System.getenv()));
          log.info("Put {} into config", clazz.getCanonicalName());
        }
      });

      _configMap = Collections.unmodifiableMap(map);
    } catch (IOException e) {
      throw new InitConfigError("Cannot init config");
    }

  }
  
}