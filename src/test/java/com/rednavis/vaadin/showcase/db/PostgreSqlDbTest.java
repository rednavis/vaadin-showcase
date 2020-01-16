package com.rednavis.vaadin.showcase.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rednavis.vaadin.showcase.extension.testcontainer.PostgreSqlExtension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@Slf4j
public class PostgreSqlDbTest {
  
  private static final String TABLE_NAME = "test";
  private static final String COLUMN_ID = "id";
  private static final String COLUMN_NAME = "name";

  @RegisterExtension
  public PostgreSqlExtension dbExtension = new PostgreSqlExtension();

  @Test
  public void testWriteInDb() throws SQLException {
    Map<String, String> columns = new HashMap<>();
    columns.put(COLUMN_ID, "int");
    columns.put(COLUMN_NAME, "varchar(255)");
    dbExtension.createTable(TABLE_NAME, columns);
    
    int result = dbExtension.executeQuery(String.format("insert into %s (%s, %s) values (1, 'name')", TABLE_NAME, COLUMN_ID, COLUMN_NAME));
    assertEquals(1, result);
    
    ResultSet selectResultSet = dbExtension.selectQuery("select * from " + TABLE_NAME);
    if (selectResultSet.next()) {
      assertEquals(1, selectResultSet.getInt(COLUMN_ID));
      assertEquals("name", selectResultSet.getString(COLUMN_NAME));
    } else {
      log.info("Not empty select query result set is expected");
      throw new IllegalArgumentException("Not empty select query result set is expected");
    }
  }
}