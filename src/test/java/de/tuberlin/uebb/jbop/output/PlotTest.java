package de.tuberlin.uebb.jbop.output;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlotTest {
  
  @Test
  public void test() {
    // INIT
    final Plot plot = new Plot("title");
    plot.addCoordinates("0", "0");
    plot.addCoordinates("1", "1");
    plot.addCoordinates("2", "2");
    
    // RUN
    final String tikzPicture = plot.getTikzPicture("xlabel", "ylabel");
    
    // ASSERT
    
    assertEquals("\\begin{tikzpicture}\n" + "\\begin{axis}[xLabel={xlabel},yLabel={ylabel}]\n"
        + "\\addplot coordinates {(0,0)(1,1)(2,2)};\n" + "\\legend{title}\\end{axis}\n" + "\\end{tikzpicture}",
        tikzPicture);
  }
}
