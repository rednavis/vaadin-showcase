package com.rednavis.vaadin.showcase.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rednavis.vaadin.showcase.rule.testcontainer.PostgreSqlDbRule;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@Slf4j
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

  /**
   * Destroying after test.
   */
  @After
  public void destroy() throws SQLException {
    dbRule.dropTable(TABLE_NAME);
  }

  @Test
  public void testWriteInDb() throws SQLException {
    int result = dbRule.executeQuery(String.format("insert into %s (%s, %s) values (1, 'name')", TABLE_NAME, COLUMN_ID, COLUMN_NAME));
    assertEquals(1, result);
    
    ResultSet selectResultSet = dbRule.selectQuery("select * from " + TABLE_NAME);
    if (selectResultSet.next()) {
      assertEquals(1, selectResultSet.getInt(COLUMN_ID));
      assertEquals("name", selectResultSet.getString(COLUMN_NAME));
    } else {
      log.info("Not empty select query result set is expected");
      throw new IllegalArgumentException("Not empty select query result set is expected");
    }
  }
}