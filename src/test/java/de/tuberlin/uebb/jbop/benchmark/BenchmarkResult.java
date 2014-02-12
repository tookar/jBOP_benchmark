package de.tuberlin.uebb.jbop.benchmark;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import de.tuberlin.uebb.jbop.output.AbstractPlot;
import de.tuberlin.uebb.jbop.output.StringTable;
import de.tuberlin.uebb.jbop.output.TableToAbstractPlot;

public class BenchmarkResult {
  
  private static final String LABEL_RATIO = "Verhältnis";
  private static final String LABEL_TIME = "Zeit[ns]";
  private static final String LABEL_LOOPS = "Iterationen";
  private static final TableToAbstractPlot TRANSFORMER = new TableToAbstractPlot();
  private final StringTable tableRun = new StringTable();
  private final StringTable tableCreate = new StringTable();
  private final StringTable tableTotal = new StringTable();
  private final StringTable tableRatio = new StringTable();
  private final IBenchmarkFactory<?> factory;
  private boolean isLatex;
  
  public BenchmarkResult(final IBenchmarkFactory<?> factory) {
    this.factory = factory;
    tableRun.setDebug(true);
    tableRun.addColumn("Iterationen", "%,15d");
    tableRun.addColumn("Laufzeit", "%,15d");
    tableRun.addColumn("Laufzeit optimiert", "%,15d");
    tableRun.setLabel(factory.getLabel() + ".run");
    tableRun.setShortCaption(factory.getCaption());
    tableRun.setCaption(factory.getCaption() + ": Laufzeit von normaler und optimierter Ausführung");
    
    tableCreate.addColumn("Iterationen", "%,15d");
    // tableCreate.addColumn("Erzeugung", "%,15d");
    // tableCreate.addColumn("Optimierung", "%,15d");
    tableCreate.setLabel(factory.getLabel() + ".create");
    tableCreate.setShortCaption(factory.getCaption());
    tableCreate.setCaption(factory.getCaption() + ": Instanziierungs- und Optimierungszeit");
    
    tableTotal.addColumn("Iterationen", "%,15d");
    tableTotal.addColumn("Ausführungszeit", "%,15d");
    tableTotal.addColumn("Ausführungszeit optimiert", "%,15d");
    tableTotal.setLabel(factory.getLabel() + ".total");
    tableTotal.setShortCaption(factory.getCaption());
    tableTotal.setCaption(factory.getCaption() + ": totale Laufzeit (erzeugung und Ausführung)");
    
    tableRatio.addColumn("Iterationen", "%,15d");
    // tableRatio.addColumn("Laufzeit", "%,15.9f");
    // tableRatio.addColumn("Erzeugung / Optimierung", "%,15.9f");
    tableRatio.addColumn("Ausführung", "%,15.9f");
    tableRatio.setLabel(factory.getLabel() + ".ratio");
    tableRatio.setCaption(factory.getCaption() + ": Speedup");
  }
  
  private void add(final StringBuilder builder, final String lx, final String ly, final boolean logy,
      final boolean logx, final StringTable... tables) {
    if (tables == null || tables.length < 1) {
      return;
    }
    
    final AbstractPlot plot = TRANSFORMER.transform(tables[0]);
    plot.setLogy(logy);
    plot.setLogx(logx);
    // if (tables.length == 1) {
    // builder.append(tables[0].toString());
    // } else {
    // builder.append(StringTable.merge(tables[0], tables[1]).toString());
    // }
    builder.append("\n\n");
    builder.append(plot.getTikzPicture(lx, ly));
  }
  
  @Override
  public String toString() {
    final StringBuilder result = new StringBuilder();
    // result.append("\\FloatBarrier\n");
    // result.append("\n\n");
    // result.append("\\cref{tab:").append(factory.getLabel()).append(".run}\n");
    // result.append("\\cref{tab:").append(factory.getLabel()).append(".merged}\n");
    // result.append("\\cref{fig:").append(factory.getLabel()).append(".run}\n");
    // result.append("\\cref{fig:").append(factory.getLabel()).append(".merged}\n");
    // result.append("\\cref{fig:").append(factory.getLabel()).append(".ratio}\n");
    // result.append("\n\n");
    
    add(result, LABEL_LOOPS, LABEL_TIME, true, true, tableRun);
    result.append("\n\n");
    add(result, LABEL_LOOPS, LABEL_TIME, true, true, mergeTables());
    result.append("\n\n");
    add(result, LABEL_LOOPS, LABEL_RATIO, true, true, tableRatio);
    return result.toString();
  }
  
  private StringTable mergeTables() {
    final StringTable merged = StringTable.merge(tableCreate, tableTotal);
    merged.setLabel(factory.getLabel() + ".merged");
    merged.setShortCaption(factory.getCaption());
    merged.setCaption(factory.getCaption() + ": komplette Laufzeit");
    return merged;
  }
  
  public void addResults(final int durchlauf, final long timeCreate, final long timeOptimize, final long timeRunNormal,
      final long timeRunOptimized) {
    final long total = timeRunNormal + timeCreate;
    final long totalOpt = timeRunOptimized + timeOptimize;
    
    // final double rateCreate = (double) timeOptimize / (double) timeCreate;
    // final double rateRun = (double) timeRunOptimized / (double) timeRunNormal;
    final double rateTotal = (double) totalOpt / (double) total;
    final int iterationen = (int) Math.pow(10, durchlauf);
    tableTotal.addRow(iterationen, total, totalOpt);
    tableRun.addRow(iterationen, timeRunNormal, timeRunOptimized);
    tableCreate.addRow(iterationen/* , timeCreate, timeOptimize */);
    tableRatio.addRow(iterationen, /* rateRun, rateCreate, */rateTotal);
  }
  
  public void setLatex(final boolean isLatex) {
    this.isLatex = isLatex;
    tableRun.setLatex(isLatex);
    tableCreate.setLatex(isLatex);
    tableTotal.setLatex(isLatex);
    tableRatio.setLatex(isLatex);
  }
  
  public String getInclude() {
    return "\\input{" + factory.getLabel() + "}";
  }
  
  public void store(final File parent) throws IOException {
    final File file = new File(parent, factory.getLabel() + (isLatex ? ".tex" : ".txt"));
    FileUtils.write(file, toString());
    
  }
}
