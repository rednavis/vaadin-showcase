package com.rednavis.vaadin.showcase.backend;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.NameType;

@ApplicationScoped
public class BackendServiceImpl implements BackendService {

  private static final List<Employee> EMPLOYEES;

  static {
    MockNeat mockNeat = MockNeat.threadLocal();
    EMPLOYEES = mockNeat.reflect(Employee.class)
        .field("firstName", mockNeat.names().type(NameType.FIRST_NAME))
        .field("lastName", mockNeat.names().type(NameType.LAST_NAME))
        .field("title", mockNeat.departments())
        .field("email", mockNeat.emails())
        .field("birthDay", mockNeat.localDates())
        .list(200)
        .val();
  }

  @Override
  public List<Employee> getEmployees() {
    return BackendServiceImpl.EMPLOYEES;
  }
}
