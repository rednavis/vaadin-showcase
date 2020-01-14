package com.rednavis.vaadin.showcase.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rednavis.vaadin.showcase.rule.testcontainer.PostgreSqlDbRule;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class PostgreSqlDbTest {

  private static final String TABLE_NAME = "test";
  private static final String COLUMN_ID = "id";
  private static final String COLUMN_NAME = "name";
  
  @Rule
  public PostgreSqlDbRule dbRule = new PostgreSqlDbRule();

  /**
   * Initialization before test.
   */
  @Before
  public void init() throws SQLException {
    Map<String, String> columns = new HashMap<>();
    columns.put(COLUMN_ID, "int");
    columns.put(COLUMN_NAME, "varchar(255)");
    dbRule.createTable(TABLE_NAME, columns);
  }

  @After
  public void destroy() throws SQLException {
    dbRule.dropTable(TABLE_NAME);
  }

  @Test
  public void test_successfully_write_in_db() throws SQLException {
    int result = dbRule.executeQuery(String.format("insert into %s (%s, %s) values (1, 'name')", TABLE_NAME, COLUMN_ID, COLUMN_NAME));
    assertEquals(1, result);
    
    ResultSet selectResultSet = dbRule.selectQuery("select * from " + TABLE_NAME);
    selectResultSet.next();
    assertEquals(1, selectResultSet.getInt(COLUMN_ID));
    assertEquals("name", selectResultSet.getString(COLUMN_NAME));
  }
}