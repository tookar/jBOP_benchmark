package de.tuberlin.uebb.jbop.output;

public abstract class AbstractPlot {
  
  private String caption;
  private String label;
  private String shortCaption;
  
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
    builder.append("\\begin{figure}\n");
    builder.append("\\begin{tikzpicture}\n");
    builder.append("\\begin{axis}[xLabel={").append(xLabel).append("},yLabel={").append(yLabel).append("}]\n");
    return builder.toString();
  }
  
  protected String getSuffix() {
    final StringBuilder builder = new StringBuilder();
    builder.append("\\end{axis}\n");
    builder.append("\\end{tikzpicture}\n");
    
    builder.append("\\label{fig:").append(getLabel()).append("}\n");
    builder.append("\\caption[").append(getShortCaption()).append("]{").append(getCaption()).append("}\n");
    builder.append("\\end{figure}");
    return builder.toString();
  }
  
  /**
   * Sets the caption.
   * 
   * @param caption
   *          the new caption
   */
  public void setCaption(final String caption) {
    this.caption = caption;
  }
  
  private String getCaption() {
    if (caption == null) {
      return "";
    }
    return caption;
  }
  
  /**
   * Sets the caption.
   * 
   * @param caption
   *          the new caption
   */
  public void setShortCaption(final String shortCaption) {
    this.shortCaption = shortCaption;
  }
  
  String getShortCaption() {
    if (shortCaption == null) {
      return getCaption();
    }
    return shortCaption;
  }
  
  /**
   * Sets the label.
   * 
   * @param label
   *          the new label
   */
  public void setLabel(final String label) {
    this.label = label;
  }
  
  private String getLabel() {
    if (label == null) {
      return getCaption();
    }
    return label;
  }
}
