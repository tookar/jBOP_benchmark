package de.tuberlin.uebb.jbop.benchmark.classes;

public class RGBA {
  
  public final double ONE_BYTE;
  public final int R_MASK;
  public final int G_MASK;
  public final int B_MASK;
  public final int A_MASK;
  
  public final int R_SELECTOR;
  public final int G_SELECTOR;
  public final int B_SELECTOR;
  public final int A_SELECTOR;
  
  public RGBA() {
    ONE_BYTE = 255.0;
    
    A_MASK = 0xff;
    A_SELECTOR = 0;
    B_MASK = A_MASK << 8;
    B_SELECTOR = 8;
    G_MASK = B_MASK << 8;
    G_SELECTOR = 16;
    R_MASK = G_MASK << 8;
    R_SELECTOR = 24;
  }
}
