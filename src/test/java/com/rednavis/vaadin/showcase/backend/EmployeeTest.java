package com.rednavis.vaadin.showcase.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EmployeeTest {
  
  @Test
  public void shouldTestToStringOverride() {
    Employee employee = new Employee("firstName", "lastName", "email", "title");
    assertEquals(employee.toString(), employee.getFirstname() + " " + employee.getLastname() + "(" + employee.getEmail() + ")");
  }
}
