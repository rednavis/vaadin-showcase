package com.rednavis.vaadin.showcase.backend.service.password;

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

//Meta-annotation that allows test classes to be extended with {@code @EnableWeld} instead of {@code @ExtendWith(WeldJunit5Extension.class)}
@EnableWeld
class PasswordServiceTest {

  private static final String PASSWORD = "1@QWaszx";

  //An annotation used to denote a WeldInitiator field. This is then picked up by {@link WeldJunit5Extension} and used for configuration.
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