package com.rednavis.vaadin.showcase.backend.service.password;

import static com.rednavis.vaadin.showcase.TestUtils.PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rednavis.vaadin.showcase.backend.config.ConfigProvider;
import java.util.List;
import javax.inject.Inject;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;

@EnableWeld
class PasswordServiceTest {

  @WeldSetup
  public WeldInitiator weldInitiator = WeldInitiator.of(WeldInitiator.createWeld().addPackages(ConfigProvider.class, PasswordService.class));

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
  public void validateRightPassword() {
    String encryptPassword = passwordService.generatePassword(PASSWORD);
    boolean valid = passwordService.validatePassword(encryptPassword, PASSWORD);
    assertTrue(valid);
  }

  @Test
  public void validateWrongPassword() {
    String encryptPassword = passwordService.generatePassword(PASSWORD);
    boolean valid = passwordService.validatePassword(encryptPassword, PASSWORD + "123");
    assertFalse(valid);
  }
}