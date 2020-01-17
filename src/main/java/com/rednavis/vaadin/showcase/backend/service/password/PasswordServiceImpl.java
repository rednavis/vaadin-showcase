package com.rednavis.vaadin.showcase.backend.service.password;

import com.rednavis.vaadin.showcase.backend.config.PasswordConfig;
import edu.vt.middleware.password.AlphabeticalCharacterRule;
import edu.vt.middleware.password.AlphabeticalSequenceRule;
import edu.vt.middleware.password.DigitCharacterRule;
import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.LowercaseCharacterRule;
import edu.vt.middleware.password.NonAlphanumericCharacterRule;
import edu.vt.middleware.password.NumericalSequenceRule;
import edu.vt.middleware.password.Password;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.QwertySequenceRule;
import edu.vt.middleware.password.RepeatCharacterRegexRule;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.UppercaseCharacterRule;
import edu.vt.middleware.password.WhitespaceRule;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class PasswordServiceImpl implements PasswordService {

  private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
  // These constants may be changed without breaking existing hashes.
  private static final int SALT_BYTE_SIZE = 24;
  private static final int HASH_BYTE_SIZE = 18;
  private static final int PBKDF2_ITERATIONS = 64000;
  // These constants define the encoding and may not be changed.
  private static final int HASH_SECTIONS = 5;
  private static final int HASH_ALGORITHM_INDEX = 0;
  private static final int ITERATION_INDEX = 1;
  private static final int HASH_SIZE_INDEX = 2;
  private static final int SALT_INDEX = 3;
  private static final int PBKDF2_INDEX = 4;

  private final PasswordValidator passwordValidator;

  @Inject
  public PasswordServiceImpl(PasswordConfig passwordConfig) {
    List<Rule> ruleList = new ArrayList<>();

    if (passwordConfig.getMinLength() > 0 && passwordConfig.getMaxLength() > 0 && passwordConfig.getMinLength() < passwordConfig.getMaxLength()) {
      ruleList.add(new LengthRule(passwordConfig.getMinLength(), passwordConfig.getMaxLength()));
    }
    if (passwordConfig.getRequireLeastNumberOfDigits() > 0) {
      ruleList.add(new DigitCharacterRule(passwordConfig.getRequireLeastNumberOfDigits()));
    }
    if (passwordConfig.getRequireLeastNumberOfNotAlphanumerics() > 0) {
      ruleList.add(new NonAlphanumericCharacterRule(passwordConfig.getRequireLeastNumberOfNotAlphanumerics()));
    }
    if (passwordConfig.getRequireLeastNumberOfLowercases() > 0) {
      ruleList.add(new LowercaseCharacterRule(passwordConfig.getRequireLeastNumberOfLowercases()));
    }
    if (passwordConfig.getRequireLeastNumberOfUppercases() > 0) {
      ruleList.add(new UppercaseCharacterRule(passwordConfig.getRequireLeastNumberOfUppercases()));
    }
    if (passwordConfig.getAllowRepeatCharactersMax() > 0) {
      ruleList.add(new RepeatCharacterRegexRule(passwordConfig.getAllowRepeatCharactersMax()));
    }
    if (!passwordConfig.isWhitespaceAllowed()) {
      ruleList.add(new WhitespaceRule());
    }
    if (!passwordConfig.isAllowAlphabeticalSequence()) {
      ruleList.add(new AlphabeticalSequenceRule());
    }
    if (!passwordConfig.isAllowNumericalSequence()) {
      ruleList.add(new NumericalSequenceRule());
    }
    if (!passwordConfig.isAllowQwertySequence()) {
      ruleList.add(new QwertySequenceRule());
    }
    if (passwordConfig.getRequireLeastNumberOfAlphabeticals() > 0) {
      ruleList.add(new AlphabeticalCharacterRule(passwordConfig.getRequireLeastNumberOfAlphabeticals()));
    }

    passwordValidator = new PasswordValidator(ruleList);
  }

  public static String createHash(String password) throws CannotPerformOperationException {
    return createHash(password.toCharArray());
  }

  public static String createHash(char[] password) throws CannotPerformOperationException {
    // Generate a random salt
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[SALT_BYTE_SIZE];
    random.nextBytes(salt);

    // Hash the password
    byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
    int hashSize = hash.length;

    // format: algorithm:iterations:hashSize:salt:hash
    String parts = "sha1:" +
        PBKDF2_ITERATIONS +
        ":" + hashSize +
        ":" +
        toBase64(salt) +
        ":" +
        toBase64(hash);
    return parts;
  }

  public static boolean verifyPassword(String password, String correctHash)
      throws CannotPerformOperationException, InvalidHashException {
    return verifyPassword(password.toCharArray(), correctHash);
  }

  public static boolean verifyPassword(char[] password, String correctHash)
      throws CannotPerformOperationException, InvalidHashException {
    // Decode the hash into its parameters
    String[] params = correctHash.split(":");
    if (params.length != HASH_SECTIONS) {
      throw new InvalidHashException(
          "Fields are missing from the password hash."
      );
    }

    // Currently, Java only supports SHA1.
    if (!params[HASH_ALGORITHM_INDEX].equals("sha1")) {
      throw new CannotPerformOperationException(
          "Unsupported hash type."
      );
    }

    int iterations = 0;
    try {
      iterations = Integer.parseInt(params[ITERATION_INDEX]);
    } catch (NumberFormatException ex) {
      throw new InvalidHashException(
          "Could not parse the iteration count as an integer.",
          ex
      );
    }

    if (iterations < 1) {
      throw new InvalidHashException(
          "Invalid number of iterations. Must be >= 1."
      );
    }

    byte[] salt = null;
    try {
      salt = fromBase64(params[SALT_INDEX]);
    } catch (IllegalArgumentException ex) {
      throw new InvalidHashException(
          "Base64 decoding of salt failed.",
          ex
      );
    }

    byte[] hash = null;
    try {
      hash = fromBase64(params[PBKDF2_INDEX]);
    } catch (IllegalArgumentException ex) {
      throw new InvalidHashException(
          "Base64 decoding of pbkdf2 output failed.",
          ex
      );
    }

    int storedHashSize = 0;
    try {
      storedHashSize = Integer.parseInt(params[HASH_SIZE_INDEX]);
    } catch (NumberFormatException ex) {
      throw new InvalidHashException(
          "Could not parse the hash size as an integer.",
          ex
      );
    }

    if (storedHashSize != hash.length) {
      throw new InvalidHashException(
          "Hash length doesn't match stored hash length."
      );
    }

    // Compute the hash of the provided password, using the same salt,
    // iteration count, and hash length
    byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
    // Compare the hashes in constant time. The password is correct if
    // both hashes match.
    return slowEquals(hash, testHash);
  }

  private static boolean slowEquals(byte[] a, byte[] b) {
    int diff = a.length ^ b.length;
    for (int i = 0; i < a.length && i < b.length; i++) {
      diff |= a[i] ^ b[i];
    }
    return diff == 0;
  }

  private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
      throws CannotPerformOperationException {
    try {
      PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
      SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
      return skf.generateSecret(spec).getEncoded();
    } catch (NoSuchAlgorithmException ex) {
      throw new CannotPerformOperationException(
          "Hash algorithm not supported.",
          ex
      );
    } catch (InvalidKeySpecException ex) {
      throw new CannotPerformOperationException(
          "Invalid key spec.",
          ex
      );
    }
  }

  private static byte[] fromBase64(String hex)
      throws IllegalArgumentException {
    return Base64.getDecoder().decode(hex);
  }

  private static String toBase64(byte[] array) {
    return Base64.getEncoder().encodeToString(array);
  }

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
  public String generatePassword(String password) {
    try {
      return createHash(password);
    } catch (Exception e) {
      log.error("Password generatePassword failed.", e);
      throw new RuntimeException("Password generatePassword failed.", e);
    }
  }

  @Override
  public boolean validatePassword(String passwordDb, String password) {
    try {
      return verifyPassword(password, passwordDb);
    } catch (Exception e) {
      log.error("Password validation failed.", e);
      return false;
    }
  }

  static private class InvalidHashException extends Exception {

    public InvalidHashException(String message) {
      super(message);
    }

    public InvalidHashException(String message, Throwable source) {
      super(message, source);
    }
  }

  static private class CannotPerformOperationException extends Exception {

    public CannotPerformOperationException(String message) {
      super(message);
    }

    public CannotPerformOperationException(String message, Throwable source) {
      super(message, source);
    }
  }
}
