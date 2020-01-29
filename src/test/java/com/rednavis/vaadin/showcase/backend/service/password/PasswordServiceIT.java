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

  private static final String PASSWORD_WRONG = "wrongPass";
  private static final String PASSWORP_GOOD = "1@QWaszx";

  @Inject
  private PasswordService passwordService;

  @Test
  public void checkWrongPasswordComplexity() {
    List<String> errorList = passwordService.checkPasswordComplexity(PASSWORD_WRONG);
    assertEquals(2, errorList.size());
  }

  @Test
  public void checkGoodPasswordComplexity() {
    List<String> errorList = passwordService.checkPasswordComplexity(PASSWORP_GOOD);
    assertEquals(0, errorList.size());
  }

  @Test
  public void validateRightPassword() throws Exception {
    String encryptPassword = passwordService.generatePassword(PASSWORP_GOOD);
    boolean valid = passwordService.validatePassword(encryptPassword, PASSWORP_GOOD);
    assertTrue(valid);
  }

  @Test
  public void validateWrongPassword() throws Exception {
    String encryptPassword = passwordService.generatePassword(PASSWORP_GOOD);
    boolean valid = passwordService.validatePassword(encryptPassword, PASSWORP_GOOD + PASSWORD_WRONG);
    assertFalse(valid);
  }
}
