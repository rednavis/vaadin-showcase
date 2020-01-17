package com.rednavis.vaadin.showcase.backend;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;

@Singleton
public class BackendServiceImpl implements BackendService {

  private static List<Employee> employees = new ArrayList<Employee>() {{
    // Init dummy data
    add(new Employee("Rowena", "Leeming", "rleeming0@bbc.co.uk", "Food Chemist"));
    add(new Employee("Alvinia", "Delong", "adelong1@altervista.org", "Recruiting Manager"));
    add(new Employee("Leodora", "Burry", "lburry2@example.com", "Food Chemist"));
    add(new Employee("Karen", "Oaten", "koaten3@ihg.com", "VP Sales"));
    add(new Employee("Mariele", "Huke", "mhuke4@washingtonpost.com", "Research Assistant IV"));
    add(new Employee("Grata", "Widdowes", "gwiddowes5@cargocollective.com", "Actuary"));
    add(new Employee("Donna", "Roadknight", "droadknight6@apache.org", "Mechanical Systems Engineer"));
    add(new Employee("Tommi", "Nowland", "tnowland7@biblegateway.com", "Senior Developer"));
    add(new Employee("Tonya", "Teresia", "tteresia8@boston.com", "Assistant Manager"));
    add(new Employee("Steffen", "Yon", "syon9@ocn.ne.jp", "Senior Sales Associate"));
    add(new Employee("Consalve", "Willes", "cwillesa@linkedin.com", "Programmer I"));
    add(new Employee("Jeanelle", "Lambertz", "jlambertzb@nymag.com", "Operator"));
    add(new Employee("Odelia", "Loker", "olokerc@gov.uk", "Developer I"));
    add(new Employee("Briano", "Shawell", "bshawelld@posterous.com", "Research Assistant IV"));
    add(new Employee("Tarrance", "Mainston", "tmainstone@cmu.edu", "Research Nurse"));
    add(new Employee("Torrence", "Gehring", "tgehringf@a8.net", "Geological Engineer"));
    add(new Employee("Augie", "Pionter", "apionterg@ehow.com", "Senior Financial Analyst"));
    add(new Employee("Marillin", "Aveson", "mavesonh@shop-pro.jp", "Technical Writer"));
    add(new Employee("Jacquelyn", "Moreby", "jmorebyi@slashdot.org", "Executive Secretary"));
    add(new Employee("Glenn", "Bangley", "gbangleyj@prlog.org", "Account Executive"));
    add(new Employee("Isidoro", "Glave", "iglavek@tamu.edu", "Compensation Analyst"));
    add(new Employee("Cchaddie", "Spatarul", "cspatarull@sun.com", "Business Systems Development Analyst"));
  }};

  @Override
  public List<Employee> getEmployees() {
    return employees;
  }
}
