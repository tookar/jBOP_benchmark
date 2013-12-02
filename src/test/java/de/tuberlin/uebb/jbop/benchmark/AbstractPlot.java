package de.tuberlin.uebb.jbop.benchmark;

public abstract class AbstractPlot {
  
  public abstract String getTitle();
  
  public abstract String getPlot();
  
  public final String getLegend() {
    return "\\legend{" + getTitle() + "}";
  }
  
  public final String getTikzPicture(final String xLabel, final String yLabel) {
    final StringBuilder builder = new StringBuilder();
    builder.append(getPrefix(xLabel, yLabel));
    builder.append(getPlot());
    builder.append(getLegend());
    builder.append(getSuffix());
    return builder.toString();
  }
  
  protected String getPrefix(final String xLabel, final String yLabel) {
    final StringBuilder builder = new StringBuilder();
    builder.append("\\begin{tikzpicture}\n");
    builder.append("\\begin{axis}[xLabel={").append(xLabel).append("},yLabel={").append(yLabel).append("}]");
    return builder.toString();
  }
  
  protected String getSuffix() {
    final StringBuilder builder = new StringBuilder();
    builder.append("\\end{axis}\n");
    builder.append("\\end{tikzpicture}");
    return builder.toString();
  }
}
