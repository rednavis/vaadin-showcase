package com.rednavis.vaadin.showcase.backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class BackendServiceTest {
  
  private BackendService service;
  
  @BeforeAll
  public void init() {
    service = new BackendService();
  }

  @Test
  public void should_test_get_employees() {
    assertFalse(service.getEmployees().isEmpty());
  }
}
