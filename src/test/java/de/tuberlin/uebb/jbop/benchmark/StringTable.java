package de.tuberlin.uebb.jbop.benchmark;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

public class StringTable {
  
  private final List<Object[]> rows = new LinkedList<>();
  private boolean isLatex = false;
  private final List<StringColumn> columns = new LinkedList<>();
  
  private boolean isDebug = false;
  private boolean headerPrinted = false;
  
  public void setLatex(final boolean isLatex) {
    this.isLatex = isLatex;
  }
  
  public void addColumn(final String header, final String format) {
    addColumn(StringColumn.of(header, format));
  }
  
  public void addColumn(final StringColumn column) {
    columns.add(column);
  }
  
  public void addRow(final Object... rowData) {
    Validate.isTrue(rowData.length == columns.size());
    if (isDebug) {
      final String format = format(isLatex, columns);
      final String line = line(isLatex, columns);
      final StringBuilder buffer = new StringBuilder();
      if (!headerPrinted) {
        headerPrinted = true;
        final String glheader = glheader(isLatex, columns);
        final String title = title(isLatex, columns);
        buffer.append(glheader).append("\n");
        buffer.append(title).append("\n");
        buffer.append(line).append("\n");
      }
      buffer.append(String.format(Locale.GERMAN, format, rowData)).append("\n");
      buffer.append(line);
      System.out.println(buffer.toString());
    }
    rows.add(rowData);
  }
  
  @Override
  public String toString() {
    final StringBuilder buffer = new StringBuilder();
    final String glheader = glheader(isLatex, columns);
    final String line = line(isLatex, columns);
    final String title = title(isLatex, columns);
    final String format = format(isLatex, columns);
    final String glfooter = glfooter(isLatex, columns);
    buffer.append(glheader).append("\n");
    buffer.append(title).append("\n");
    buffer.append(line).append("\n");
    for (int i = 0; i < rows.size(); i++) {
      final Object[] row = rows.get(i);
      buffer.append(String.format(Locale.GERMAN, format, row)).append("\n");
      if (i < (rows.size() - 1)) {
        buffer.append(line).append("\n");
      }
    }
    buffer.append(glfooter).append("\n");
    return buffer.toString();
  }
  
  private static String mc(final String content) {
    return "\\multicolumn{1}{c}{" + content + "}";
  }
  
  private static String title(final boolean latex, final List<StringColumn> cols) {
    final StringBuilder buffer = new StringBuilder();
    if (!latex) {
      buffer.append("|");
    }
    for (int i = 0; i < cols.size(); i++) {
      final StringColumn col = cols.get(i);
      if (latex) {
        buffer.append(mc(col.getHeader()));
        if (i < (cols.size() - 1)) {
          buffer.append("&");
        }
      } else {
        buffer.append(" ").append(StringUtils.center(col.getHeader(), col.getSize())).append(" ");
        buffer.append("|");
      }
    }
    if (latex) {
      buffer.append("\\\\");
    }
    return buffer.toString();
  }
  
  private static String line(final boolean latex, final List<StringColumn> cols) {
    if (latex) {
      return "\\hline";
    }
    final StringBuilder buffer = new StringBuilder();
    buffer.append("+");
    for (final StringColumn col : cols) {
      buffer.append(StringUtils.leftPad("", col.getSize() + 2, "-")).append("+");
    }
    return buffer.toString();
  }
  
  private static String glheader(final boolean latex, final List<StringColumn> cols) {
    final StringBuilder buffer = new StringBuilder();
    if (!latex) {
      return line(latex, cols);
    }
    buffer
        .append(
            "\\begin{table}\n" + "\\centering\n" + "\\scriptsize\n" + "  \\label{tab:t1}\n" + "  \\caption{t1}\n"
                + "\\begin{tabular}{").//
        append(StringUtils.repeat("r", cols.size())).//
        append("}\n\\hline");
    return buffer.toString();
    
  }
  
  private static String format(final boolean latex, final List<StringColumn> formats) {
    final StringBuilder buffer = new StringBuilder();
    if (!latex) {
      buffer.append("|");
    }
    for (int i = 0; i < formats.size(); i++) {
      final String format = formats.get(i).getFormat();
      buffer.append(" ").append(format).append(" ");
      if (latex) {
        if (i < (formats.size() - 1)) {
          buffer.append("&");
        }
      } else {
        buffer.append("|");
      }
    }
    if (latex) {
      buffer.append("\\\\");
    }
    return buffer.toString();
  }
  
  private static String glfooter(final boolean latex, final List<StringColumn> cols) {
    if (latex) {
      return "\\hline\n\\end{tabular}\\end{table}";
    }
    return line(latex, cols);
  }
  
  public void setDebug(final boolean isDebug) {
    this.isDebug = isDebug;
  }
  
  public Object getValue(final int col, final int row) {
    return (rows.get(row)[col]);
  }
  
  public String getTitle(final int col) {
    return (columns.get(col).getHeader());
  }
  
  public Pair<Integer, Integer> getSize() {
    return Pair.of(columns.size(), rows.size());
  }
  
  public static StringTable merge(final StringTable one, final StringTable two) {
    Validate.isTrue(one.getSize().getRight() == two.getSize().getRight());
    Validate.isTrue(StringUtils.equals(one.columns.get(0).getHeader(), two.columns.get(0).getHeader()));
    Validate.isTrue(StringUtils.equals(one.columns.get(0).getFormat(), two.columns.get(0).getFormat()));
    
    final List<StringColumn> columns = new ArrayList<>();
    columns.addAll(one.columns);
    for (int i = 1; i < two.columns.size(); ++i) {
      columns.add(two.columns.get(i));
    }
    
    final StringTable merged = new StringTable();
    for (final StringColumn column : columns) {
      merged.addColumn(column);
    }
    
    for (int i = 0; i < one.rows.size(); ++i) {
      final Object[] rowData = ArrayUtils.addAll(one.rows.get(i),
          ArrayUtils.subarray(two.rows.get(i), 1, two.rows.get(i).length));
      merged.addRow(rowData);
    }
    merged.setLatex(one.isLatex);
    return merged;
  }
}
