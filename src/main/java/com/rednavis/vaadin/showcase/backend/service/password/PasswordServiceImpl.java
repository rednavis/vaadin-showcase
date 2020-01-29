package com.rednavis.vaadin.showcase.backend.service.password;

import edu.vt.middleware.password.Password;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.RuleResult;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

/**
 * {@inheritDoc}
 */
@Slf4j
@ApplicationScoped
public class PasswordServiceImpl implements PasswordService {

  @Inject
  private PasswordValidator passwordValidator;

  @Override
  public List<String> checkPasswordComplexity(String password) {
    PasswordData passwordData = new PasswordData(new Password(password));
    RuleResult result = passwordValidator.validate(passwordData);
    if (!result.isValid()) {
      return passwordValidator.getMessages(result);
    }
    return Collections.emptyList();
  }

  @Override
  public String generatePassword(String password) throws CannotPerformOperationException {
    return PasswordUtils.createHash(password);
  }

  @Override
  public boolean validatePassword(String passwordDb, String password) throws CannotPerformOperationException, InvalidHashException {
    return PasswordUtils.verifyPassword(password, passwordDb);
  }
}
