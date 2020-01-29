package com.rednavis.vaadin.showcase.backend.service.password;

import java.util.List;

/**
 * Service for work with passwords. Checking the password for validity. Generate a token for the password. Validation of the token for a given
 * password
 */
public interface PasswordService {

  List<String> checkPasswordComplexity(String password);

  String generatePassword(String password) throws CannotPerformOperationException;

  boolean validatePassword(String passwordDb, String password) throws CannotPerformOperationException, InvalidHashException;
}
