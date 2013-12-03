package de.tuberlin.uebb.jbop.benchmark;

import de.tuberlin.uebb.jbop.output.StringTable;
import de.tuberlin.uebb.jbop.output.TableToAbstractPlot;

public class BenchmarkResult {
  
  private final StringTable tableRun = new StringTable();
  private final StringTable tableCreate = new StringTable();
  private final StringTable tableTotal = new StringTable();
  private final IBenchmarkFactory factory;
  
  public BenchmarkResult(final IBenchmarkFactory factory) {
    this.factory = factory;
    tableRun.setDebug(true);
    tableRun.addColumn("cycles ($10^{x}$)", "%,15d");
    tableRun.addColumn("run", "%,15d");
    tableRun.addColumn("$run_{opt}$", "%,15d");
    tableRun.addColumn("$r_{run}$", "%,15.9f");
    tableRun.setLabel(factory.getLabel() + ".run");
    tableRun.setCaption(factory.getCaption()
        + ". Hier ist die reine Ausführungszeit (run, $run_{opt}$) sowie das Verhältnis von optimierter zu normaler " + //
        "Ausführungszeit ($r_{run}$) daregsetellt.");
    
    tableCreate.addColumn("durchlauf", "%,15d");
    tableCreate.addColumn("create", "%,15d");
    tableCreate.addColumn("optimize", "%,15d");
    tableCreate.addColumn("$r_{create}$", "%,15.9f");
    tableCreate.setLabel(factory.getLabel() + ".create");
    tableCreate.setCaption(factory.getCaption()
        + ". Hier ist die reine Instanziierungs- (create) / Optimierungszeit (optimize) sowie das Verhältnis von " + //
        "Optimierung zu normaler Instanziierung ($r_{create}$) daregsetellt.");
    
    tableTotal.addColumn("durchlauf", "%,15d");
    tableTotal.addColumn("total", "%,15d");
    tableTotal.addColumn("totalOpt", "%,15d");
    tableTotal.addColumn("$r_{total}$", "%,15.9f");
    tableTotal.setLabel(factory.getLabel() + ".total");
    tableTotal.setCaption(factory.getCaption()
        + ". Hier ist jeweils die komplette Zeit von Instanziierung + normale Ausführung (total), Optimierung + " + //
        "optimiertzte Ausführung (totalOpt) sowie das resultierende Verhältnis ($r_{total}$) daregsetellt.");
  }
  
  @Override
  public String toString() {
    final TableToAbstractPlot transformer = new TableToAbstractPlot();
    final StringTable merged = StringTable.merge(tableCreate, tableTotal);
    merged.setLabel(factory.getLabel() + ".merged");
    merged.setCaption(factory.getCaption()
        + ". Hier ist die reine Instanziierungs- (create) / Optimierungszeit (optimize) sowie das Verhältnis von " + //
        "Optimierung zu normaler Instanziierung ($r_{create}$) und " +            //
        "die komplette Zeit von Instanziierung + normale Ausführung (total), Optimierung + " + //
        "optimiertzte Ausführung (totalOpt) sowie das resultierende Verhältnis ($r_{total}$) daregsetellt.");
    return tableRun.toString() + "\n\n" + transformer.transform(tableRun).getTikzPicture("time", "loops") + "\n\n" + //
        merged.toString() + "\n\n" + transformer.transform(tableTotal).getTikzPicture("time", "loops");
  }
  
  public void addResults(final int durchlauf, final long timeCreate, final long timeOptimize, final long timeRunNormal,
      final long timeRunOptimized) {
    final long total = timeRunNormal + timeCreate;
    final long totalOpt = timeRunOptimized + timeOptimize;
    
    final double rateCreate = (double) timeOptimize / (double) timeCreate;
    final double rateRun = (double) timeRunOptimized / (double) timeRunNormal;
    final double rateTotal = (double) totalOpt / (double) total;
    
    tableTotal.addRow(durchlauf, total, totalOpt, rateTotal);
    tableRun.addRow(durchlauf, timeRunNormal, timeRunOptimized, rateRun);
    tableCreate.addRow(durchlauf, timeCreate, timeOptimize, rateCreate);
    
  }
  
  public void setLatex(final boolean isLatex) {
    tableRun.setLatex(isLatex);
    tableCreate.setLatex(isLatex);
    tableTotal.setLatex(isLatex);
  }
}
