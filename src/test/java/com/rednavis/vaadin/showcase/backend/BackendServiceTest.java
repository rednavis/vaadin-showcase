package com.rednavis.vaadin.showcase.backend;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import javax.inject.Inject;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;

@EnableWeld
class BackendServiceTest {

  @WeldSetup
  private WeldInitiator weldInitiator = WeldInitiator.of(WeldInitiator.createWeld().addPackages(BackendService.class));

  @Inject
  private BackendService backendService;

  @Test
  public void shouldTestGetEmployees() {
    List<Employee> employeeList = backendService.getEmployees();
    assertFalse(employeeList.isEmpty());
  }
}
