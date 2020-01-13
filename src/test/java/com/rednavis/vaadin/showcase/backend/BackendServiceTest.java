package com.rednavis.vaadin.showcase.backend;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BackendServiceTest {
  
  @InjectMocks
  private BackendService service;

  @Test
  public void shouldTestGetEmployees() {
    assertFalse(service.getEmployees().isEmpty());
  }
}
