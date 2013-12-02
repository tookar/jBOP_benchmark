package de.tuberlin.uebb.jbop.output;

import java.util.Locale;

public class StringColumn {
  
  private final String header;
  private final String format;
  private final int size;
  
  public StringColumn(final String header, final String format) {
    this.header = header;
    this.format = format;
    size = String.format(Locale.GERMAN, format, getObject(format)).length();
  }
  
  private static Object getObject(final String format) {
    final String conversion = format.substring(format.length() - 1);
    if ("f".equals(conversion)) {
      return 0.0;
    }
    if ("d".equals(conversion)) {
      return 1;
    }
    if ("b".equals(conversion)) {
      return false;
    }
    if ("c".equals(conversion)) {
      return 'c';
    }
    if ("o".equals(conversion)) {
      return 01;
    }
    if ("x".equals(conversion) || "X".equals(conversion)) {
      return 0x1;
    }
    if ("e".equals(conversion) || "E".equals(conversion)) {
      return 0f;
    }
    if ("g".equals(conversion) || "G".equals(conversion)) {
      return 0f;
    }
    if ("a".equals(conversion) || "A".equals(conversion)) {
      return 0f;
    }
    if ("t".equals(conversion) || "T".equals(conversion)) {
      return 0f;
    }
    return "";
  }
  
  protected String getHeader() {
    return header;
  }
  
  protected String getFormat() {
    return format;
  }
  
  protected int getSize() {
    return size;
  }
  
  public static StringColumn of(final String header, final String format) {
    return new StringColumn(header, format);
  }
}
