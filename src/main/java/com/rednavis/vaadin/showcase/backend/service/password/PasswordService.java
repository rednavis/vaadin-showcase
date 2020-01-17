package com.rednavis.vaadin.showcase.backend.service.password;

import java.util.List;

public interface PasswordService {

  List<String> checkPasswordComplexity(String password);

  String generatePassword(String password);

  boolean validatePassword(String passwordDb, String password);
}
