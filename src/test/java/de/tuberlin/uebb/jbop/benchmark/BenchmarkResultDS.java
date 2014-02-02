package de.tuberlin.uebb.jbop.benchmark;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import de.tuberlin.uebb.jbop.output.AbstractPlot;
import de.tuberlin.uebb.jbop.output.StringTable;
import de.tuberlin.uebb.jbop.output.TableToAbstractPlot;

public class BenchmarkResultDS {
  
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
  
  public BenchmarkResultDS(final IBenchmarkFactory<?> factory) {
    this.factory = factory;
    tableRun.setDebug(true);
    tableRun.addColumn("Iterationen", "%,15d");
    tableRun.addColumn("Laufzeit", "%,15d");
    tableRun.addColumn("Laufzeit optimiert(I)", "%,15d");
    tableRun.addColumn("Laufzeit optimiert(II)", "%,15d");
    tableRun.setLabel(factory.getLabel() + ".run");
    tableRun.setShortCaption(factory.getCaption());
    tableRun.setCaption(factory.getCaption() + ": Laufzeit von normaler und optimierter Ausführung");
    
    tableCreate.addColumn("Iterationen", "%,15d");
    // tableCreate.addColumn("Erzeugung", "%,15d");
    // tableCreate.addColumn("Optimierung(I)", "%,15d");
    // tableCreate.addColumn("Optimierung(II)", "%,15d");
    tableCreate.setLabel(factory.getLabel() + ".create");
    tableCreate.setShortCaption(factory.getCaption());
    tableCreate.setCaption(factory.getCaption() + ": Instanziierungs- und Optimierungszeit");
    
    tableTotal.addColumn("Iterationen", "%,15d");
    tableTotal.addColumn("Ausführungszeit", "%,15d");
    tableTotal.addColumn("Ausführungszeit optimiert(I)", "%,15d");
    tableTotal.addColumn("Ausführungszeit optimiert(II)", "%,15d");
    tableTotal.setLabel(factory.getLabel() + ".total");
    tableTotal.setShortCaption(factory.getCaption());
    tableTotal.setCaption(factory.getCaption() + ": totale Laufzeit (erzeugung und Ausführung)");
    
    tableRatio.addColumn("Iterationen", "%,15d");
    // tableRatio.addColumn("Laufzeit(I)", "%,15.9f");
    // tableRatio.addColumn("Laufzeit(II)", "%,15.9f");
    // tableRatio.addColumn("Erzeugung / Optimierung(I)", "%,15.9f");
    // tableRatio.addColumn("Erzeugung / Optimierung(II)", "%,15.9f");
    tableRatio.addColumn("Ausführung(I)", "%,15.9f");
    tableRatio.addColumn("Ausführung(II)", "%,15.9f");
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
  
  public void addResults(final int durchlauf, final long timeCreate, final long timeOptimize, final long timeOptOnly,
      final long timeRunNormal, final long timeRunOptimized, final long timeRunOnly) {
    final long total = timeRunNormal + timeCreate;
    final long totalOpt = timeRunOptimized + timeOptimize;
    final long totalOnly = timeRunOnly + timeOptOnly;
    
    // final double rateCreateI = (double) timeOptimize / (double) timeCreate;
    // final double rateRunI = (double) timeRunOptimized / (double) timeRunNormal;
    final double rateTotalI = (double) totalOpt / (double) total;
    // final double rateCreateII = (double) timeOptOnly / (double) timeCreate;
    // final double rateRunII = (double) timeRunOnly / (double) timeRunNormal;
    final double rateTotalII = (double) totalOnly / (double) total;
    final int iterationen = (int) Math.pow(10, durchlauf);
    tableTotal.addRow(iterationen, total, totalOpt, totalOnly);
    tableRun.addRow(iterationen, timeRunNormal, timeRunOptimized, timeRunOnly);
    tableCreate.addRow(iterationen/* , timeCreate, timeOptimize, timeOptOnly */);
    tableRatio.addRow(iterationen, /* rateRunI, rateRunII, rateCreateI, rateCreateII, */rateTotalI, rateTotalII);
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
