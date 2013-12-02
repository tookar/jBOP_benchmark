package de.tuberlin.uebb.jbop.output;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringTableTest {
  
  @Test
  public void test() {
    // INIT
    final Object[] rowData = {
        "s", 1, 1.0
    };
    final String[] headers = {
        "string", "int", "double"
    };
    final String[] formats = {
        "%15s", "%15d", "%15.9f"
    };
    
    // RUN
    final StringTable table = new StringTable();
    for (int i = 0; i < headers.length; ++i) {
      table.addColumn(headers[i], formats[i]);
    }
    table.addRow(rowData);
    
    // ASSERT
    assertEquals("" +    //
        "+-----------------+-----------------+-----------------+\n" + //
        "|     string      |       int       |     double      |\n" +    //
        "+-----------------+-----------------+-----------------+\n" + //
        "|               s |               1 |     1,000000000 |\n" +        //
        "+-----------------+-----------------+-----------------+\n" //
    , table.toString());
  }
}
