package com.rednavis.vaadin.showcase.backend;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BackendServiceTest {

  @Spy
  private BackendService backendService = new BackendServiceImpl();

  @Test
  public void shouldTestGetEmployees() {
    List<Employee> employeeList = backendService.getEmployees();
    assertFalse(employeeList.isEmpty());
  }
}
