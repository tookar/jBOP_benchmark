package de.tuberlin.uebb.jbop.benchmark;

import de.tuberlin.uebb.jbop.output.StringTable;
import de.tuberlin.uebb.jbop.output.TableToAbstractPlot;

public class BenchmarkResult {
  
  private final StringTable tableRun = new StringTable();
  private final StringTable tableCreate = new StringTable();
  private final StringTable tableTotal = new StringTable();
  
  public BenchmarkResult() {
    tableRun.setDebug(true);
    tableRun.addColumn("cycles ($10^{x}$)", "%,15d");
    tableRun.addColumn("run", "%,15d");
    tableRun.addColumn("run-opt", "%,15d");
    tableRun.addColumn("$r_{run}$", "%,15.9f");
    
    tableCreate.addColumn("durchlauf", "%,15d");
    tableCreate.addColumn("create", "%,15d");
    tableCreate.addColumn("optimize", "%,15d");
    tableCreate.addColumn("$r_{create}$", "%,15.9f");
    
    tableTotal.addColumn("durchlauf", "%,15d");
    tableTotal.addColumn("total", "%,15d");
    tableTotal.addColumn("totalOptimiert", "%,15d");
    tableTotal.addColumn("$r_{total}$", "%,15.9f");
  }
  
  @Override
  public String toString() {
    final TableToAbstractPlot transformer = new TableToAbstractPlot();
    return tableRun.toString() + "\n\n" + transformer.transform(tableRun).getTikzPicture("time", "loops") + "\n\n"
        + //
        StringTable.merge(tableCreate, tableTotal).toString() + "\n\n"
        + transformer.transform(tableTotal).getTikzPicture("time", "loops");
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
