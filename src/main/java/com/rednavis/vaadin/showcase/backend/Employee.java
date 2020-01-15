package com.rednavis.vaadin.showcase.backend;

import lombok.Getter;
import lombok.Setter;

/**
 *  Model.
 */
@Getter
@Setter
public class Employee {

  private String firstname;
  private String lastname;
  private String title;
  private String email;
  private String notes = "";

  /**
   * Employee.
   *
   * @param firstname firstname
   * @param lastname  lastname
   * @param email     email
   * @param title     title
   */
  public Employee(String firstname, String lastname, String email, String title) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.title = title;
  }

  @Override
  public String toString() {
    return firstname + " " + lastname + "(" + email + ")";
  }
}
