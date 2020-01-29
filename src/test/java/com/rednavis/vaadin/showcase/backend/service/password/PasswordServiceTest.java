package com.rednavis.vaadin.showcase.backend.service.password;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.RuleResultDetail;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PasswordServiceTest {

  private static final String PASSWORD_WRONG = "wrongPass";
  private static final String PASSWORP_GOOD = "1@QWaszx";

  @InjectMocks
  private PasswordServiceImpl passwordService;

  @Mock
  private PasswordValidator passwordValidator;

  private static RuleResult createWrongRuleResult() {
    List<RuleResultDetail> errList = new ArrayList<RuleResultDetail>() {{
      RuleResultDetail ruleResultDetail1 = new RuleResultDetail("INSUFFICIENT_CHARACTERS", new HashMap<String, Object>() {{
        put("minimumRequired", 1);
        put("characterType", "digit");
        put("validCharacterCount", 0);
        put("validCharacters", "0123456789");
      }});
      add(ruleResultDetail1);

      RuleResultDetail ruleResultDetail2 = new RuleResultDetail("INSUFFICIENT_CHARACTERS", new HashMap<String, Object>() {{
        put("minimumRequired", 1);
        put("characterType", "uppercase");
        put("validCharacterCount", 0);
        put("validCharacters", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
      }});
      add(ruleResultDetail2);
    }};

    RuleResult ruleResult = new RuleResult();
    ruleResult.setValid(false);
    ruleResult.setDetails(errList);
    return ruleResult;
  }

  @Test
  public void checkWrongPasswordComplexity() {
    when(passwordValidator.validate(any(PasswordData.class))).thenReturn(createWrongRuleResult());

    when(passwordValidator.getMessages(any(RuleResult.class))).thenReturn(new ArrayList<String>() {{
      add("Error 1");
      add("Error 2");
    }});

    List<String> errorList = passwordService.checkPasswordComplexity(PASSWORD_WRONG);
    assertEquals(2, errorList.size());
  }

  @Test
  public void checkGoodPasswordComplexity() {
    when(passwordValidator.validate(any(PasswordData.class))).thenReturn(new RuleResult() {{
      setValid(true);
    }});

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