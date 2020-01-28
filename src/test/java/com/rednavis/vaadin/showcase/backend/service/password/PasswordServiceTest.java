package com.rednavis.vaadin.showcase.backend.service.password;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

import edu.vt.middleware.password.AlphabeticalCharacterRule;
import edu.vt.middleware.password.AlphabeticalSequenceRule;
import edu.vt.middleware.password.DigitCharacterRule;
import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.LowercaseCharacterRule;
import edu.vt.middleware.password.NonAlphanumericCharacterRule;
import edu.vt.middleware.password.NumericalSequenceRule;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.QwertySequenceRule;
import edu.vt.middleware.password.RepeatCharacterRegexRule;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.UppercaseCharacterRule;
import edu.vt.middleware.password.WhitespaceRule;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PasswordServiceTest {

  private static final String PASSWORD = "1@QWaszx";

  private static PasswordValidator passwordValidator;
  @InjectMocks
  private PasswordService passwordService = new PasswordServiceImpl();

  @BeforeAll
  public static void setup() {
    List<Rule> ruleList = new ArrayList<>();
    ruleList.add(new AlphabeticalCharacterRule(1));
    ruleList.add(new QwertySequenceRule());
    ruleList.add(new NumericalSequenceRule());
    ruleList.add(new AlphabeticalSequenceRule());
    ruleList.add(new WhitespaceRule());
    ruleList.add(new RepeatCharacterRegexRule(4));
    ruleList.add(new UppercaseCharacterRule(1));
    ruleList.add(new LowercaseCharacterRule(1));
    ruleList.add(new NonAlphanumericCharacterRule(1));
    ruleList.add(new DigitCharacterRule(1));
    ruleList.add(new LengthRule(8, 128));

    passwordValidator = spy(new PasswordValidator(ruleList));
  }

  @Test
  public void checkPasswordComplexity() {
    List<String> errorList = passwordService.checkPasswordComplexity("wrongPass");
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