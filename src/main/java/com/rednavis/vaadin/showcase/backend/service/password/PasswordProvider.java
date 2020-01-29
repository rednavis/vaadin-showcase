package com.rednavis.vaadin.showcase.backend.service.password;

import com.rednavis.vaadin.showcase.backend.config.PasswordConfig;
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
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class PasswordProvider {

  @Inject
  private PasswordConfig passwordConfig;

  @Produces
  @Dependent
  public PasswordValidator producePasswordValidator() {
    List<Rule> ruleList = new ArrayList<>();
    checkPasswordLength(passwordConfig, ruleList);
    checkRequireLeastNumberOfDigits(passwordConfig, ruleList);
    checkRequireLeastNumberOfNotAlphanumerics(passwordConfig, ruleList);
    checkRequireLeastNumberOfLowercases(passwordConfig, ruleList);
    checkRequireLeastNumberOfUppercases(passwordConfig, ruleList);
    checkAllowRepeatCharactersMax(passwordConfig, ruleList);
    checkWhitespaceAllowed(passwordConfig, ruleList);
    checkAllowAlphabeticalSequence(passwordConfig, ruleList);
    checkAllowNumericalSequence(passwordConfig, ruleList);
    checkAllowQwertySequence(passwordConfig, ruleList);
    checkRequireLeastNumberOfAlphabeticals(passwordConfig, ruleList);
    return new PasswordValidator(ruleList);
  }

  private void checkRequireLeastNumberOfAlphabeticals(PasswordConfig passwordConfig,
      List<Rule> ruleList) {
    if (passwordConfig.getRequireLeastNumberOfAlphabeticals() > 0) {
      ruleList.add(new AlphabeticalCharacterRule(passwordConfig.getRequireLeastNumberOfAlphabeticals()));
    }
  }

  private void checkAllowQwertySequence(PasswordConfig passwordConfig, List<Rule> ruleList) {
    if (!passwordConfig.isAllowQwertySequence()) {
      ruleList.add(new QwertySequenceRule());
    }
  }

  private void checkAllowNumericalSequence(PasswordConfig passwordConfig, List<Rule> ruleList) {
    if (!passwordConfig.isAllowNumericalSequence()) {
      ruleList.add(new NumericalSequenceRule());
    }
  }

  private void checkAllowAlphabeticalSequence(PasswordConfig passwordConfig, List<Rule> ruleList) {
    if (!passwordConfig.isAllowAlphabeticalSequence()) {
      ruleList.add(new AlphabeticalSequenceRule());
    }
  }

  private void checkWhitespaceAllowed(PasswordConfig passwordConfig, List<Rule> ruleList) {
    if (!passwordConfig.isWhitespaceAllowed()) {
      ruleList.add(new WhitespaceRule());
    }
  }

  private void checkAllowRepeatCharactersMax(PasswordConfig passwordConfig, List<Rule> ruleList) {
    if (passwordConfig.getAllowRepeatCharactersMax() > 0) {
      ruleList.add(new RepeatCharacterRegexRule(passwordConfig.getAllowRepeatCharactersMax()));
    }
  }

  private void checkRequireLeastNumberOfUppercases(PasswordConfig passwordConfig, List<Rule> ruleList) {
    if (passwordConfig.getRequireLeastNumberOfUppercases() > 0) {
      ruleList.add(new UppercaseCharacterRule(passwordConfig.getRequireLeastNumberOfUppercases()));
    }
  }

  private void checkRequireLeastNumberOfLowercases(PasswordConfig passwordConfig, List<Rule> ruleList) {
    if (passwordConfig.getRequireLeastNumberOfLowercases() > 0) {
      ruleList.add(new LowercaseCharacterRule(passwordConfig.getRequireLeastNumberOfLowercases()));
    }
  }

  private void checkRequireLeastNumberOfNotAlphanumerics(PasswordConfig passwordConfig,
      List<Rule> ruleList) {
    if (passwordConfig.getRequireLeastNumberOfNotAlphanumerics() > 0) {
      ruleList.add(new NonAlphanumericCharacterRule(passwordConfig.getRequireLeastNumberOfNotAlphanumerics()));
    }
  }

  private void checkRequireLeastNumberOfDigits(PasswordConfig passwordConfig, List<Rule> ruleList) {
    if (passwordConfig.getRequireLeastNumberOfDigits() > 0) {
      ruleList.add(new DigitCharacterRule(passwordConfig.getRequireLeastNumberOfDigits()));
    }
  }

  private void checkPasswordLength(PasswordConfig passwordConfig, List<Rule> ruleList) {
    if (passwordConfig.getMinLength() > 0 && passwordConfig.getMaxLength() > 0 && passwordConfig.getMinLength() < passwordConfig.getMaxLength()) {
      ruleList.add(new LengthRule(passwordConfig.getMinLength(), passwordConfig.getMaxLength()));
    }
  }

}
