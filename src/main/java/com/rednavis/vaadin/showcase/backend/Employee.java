package com.rednavis.vaadin.showcase.backend;

import java.time.LocalDate;
import lombok.Data;

/**
 * Model.
 */
@Data
public class Employee {

  private String firstName;
  private String lastName;
  private String title;
  private String email;
  private LocalDate birthDay;
}
