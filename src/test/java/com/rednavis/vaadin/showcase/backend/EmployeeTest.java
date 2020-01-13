package com.rednavis.vaadin.showcase.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {

  private Employee employee;
  
  @BeforeEach
  public void init() {
    employee = new Employee("firstName", "lastName", "email", "title");
  }
  
  @Test
  public void shouldTestToStringOverride() {
    assertEquals(employee.toString(), employee.getFirstname() + " " + employee.getLastname() + "(" + employee.getEmail() + ")");
  }
}
