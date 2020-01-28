package com.rednavis.vaadin.showcase.backend.service.password;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rednavis.vaadin.showcase.backend.config.ConfigProvider;
import java.util.List;
import javax.inject.Inject;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

/**
 * Test the serviceability of the service {@link PasswordService}
 */
@EnableAutoWeld
@AddBeanClasses( {ConfigProvider.class,
    PasswordProvider.class,
    PasswordServiceImpl.class})
public class PasswordServiceIT {

  private static final String PASSWORD = "1@QWaszx";

  @Inject
  private PasswordService passwordService;

  @Test
  public void checkPasswordComplexity() {
    List<String> errorList = passwordService.checkPasswordComplexity("wrong pass");
    assertEquals(2, errorList.size());

    errorList = passwordService.checkPasswordComplexity(PASSWORD);
    assertEquals(0, errorList.size());
  }

  @Test
  public void validateRightPassword() throws Exception {
    String encryptPassword = passwordService.generatePassword(PASSWORD);
    boolean valid = passwordService.validatePassword(encryptPassword, PASSWORD);
    assertTrue(valid);
  }

  @Test
  public void validateWrongPassword() throws Exception {
    String encryptPassword = passwordService.generatePassword(PASSWORD);
    boolean valid = passwordService.validatePassword(encryptPassword, PASSWORD + "123");
    assertFalse(valid);
  }
}
