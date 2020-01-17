package com.rednavis.vaadin.showcase.backend.db;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DbConstantUtils {

  // general constants
  public static final String ID = "id";

  // table roles
  public static final String ROLE_PREFIX = "role";
  public static final String ROLE_PREFIX_ID = ROLE_PREFIX + "_" + ID;
}