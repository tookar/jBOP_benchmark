package de.tuberlin.uebb.jbop.benchmark.classes;

import de.tuberlin.uebb.jbop.benchmark.AbstractBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmarkFactory;
import de.tuberlin.uebb.jbop.optimizer.IOptimizerSuite;
import de.tuberlin.uebb.jbop.optimizer.Optimizer;
import de.tuberlin.uebb.jbop.optimizer.annotations.ImmutableArray;
import de.tuberlin.uebb.jbop.optimizer.annotations.Optimizable;
import de.tuberlin.uebb.jbop.optimizer.annotations.StrictLoops;

public class AlphaBlending extends AbstractBenchmark<int[][]> {
  
  @ImmutableArray
  private final int[][] bitmap1;
  @ImmutableArray
  private final int[][] bitmap2;
  
  private final RGBA format;
  
  AlphaBlending(final int[][] bitmap1, final int[][] bitmap2, final RGBA format) {
    this.bitmap1 = bitmap1;
    this.bitmap2 = bitmap2;
    this.format = format;
  }
  
  @Override
  @Optimizable
  @StrictLoops
  public int[][] run() {
    // checks
    if (bitmap1 == null) {
      throw new RuntimeException("Input cannot be null.");
    }
    if (bitmap2 == null) {
      throw new RuntimeException("Input cannot be null.");
    }
    if (bitmap1.length != bitmap2.length) {
      throw new RuntimeException("Image sizes differ.");
    }
    final int width = bitmap1[0].length;
    for (int i = 0; i < bitmap1.length; ++i) {
      if (bitmap1[i].length != width) {
        throw new RuntimeException("Image sizes differ.");
      }
      if (bitmap2[i].length != width) {
        throw new RuntimeException("Image sizes differ.");
      }
    }
    
    // do blending
    final int[][] bitmapResult = new int[bitmap1.length][bitmap1[0].length];
    for (int y = 0; y < bitmap1.length; ++y) {
      for (int x = 0; x < bitmap1[0].length; ++x) {
        final int rgb1 = bitmap1[y][x];
        final int rgb2 = bitmap2[y][x];
        final double r1 = ((rgb1 & format.R_MASK) >>> format.R_SELECTOR) / format.ONE_BYTE;
        final double r2 = ((rgb2 & format.R_MASK) >>> format.R_SELECTOR) / format.ONE_BYTE;
        final double g1 = ((rgb1 & format.G_MASK) >>> format.G_SELECTOR) / format.ONE_BYTE;
        final double g2 = ((rgb2 & format.G_MASK) >>> format.G_SELECTOR) / format.ONE_BYTE;
        final double b1 = ((rgb1 & format.B_MASK) >>> format.B_SELECTOR) / format.ONE_BYTE;
        final double b2 = ((rgb2 & format.B_MASK) >>> format.B_SELECTOR) / format.ONE_BYTE;
        final double a1 = ((rgb1 & format.A_MASK) >>> format.A_SELECTOR) / format.ONE_BYTE;
        final double a2 = ((rgb2 & format.A_MASK) >>> format.A_SELECTOR) / format.ONE_BYTE;
        final int rx = (int) ((r1 * a1 + r2 * a2 * (1 - a1)) * format.ONE_BYTE);
        final int gx = (int) ((g1 * a1 + g2 * a2 * (1 - a1)) * format.ONE_BYTE);
        final int bx = (int) ((b1 * a1 + b2 * a2 * (1 - a1)) * format.ONE_BYTE);
        final int ax = (int) ((a1 + a2 * (1 - a1)) * format.ONE_BYTE);
        final int rgbx = rx << format.R_SELECTOR | gx << format.G_SELECTOR | bx << format.B_SELECTOR
            | ax << format.A_SELECTOR;
        bitmapResult[y][x] = rgbx;
      }
    }
    return bitmapResult;
  }
  
  public static class Factory implements IBenchmarkFactory<int[][]> {
    
    private static final Optimizer OPTIMIZER = new Optimizer();
    
    final int width;
    final int height;
    
    public Factory() {
      this(3, 3);
    }
    
    public Factory(final int width, final int height) {
      this.width = width;
      this.height = height;
    }
    
    @Override
    public IBenchmark<int[][]> create() {
      final int[][] bitmap1 = new int[height][width];
      final int[][] bitmap2 = new int[height][width];
      for (int y = 0; y < height; ++y) {
        for (int x = 0; x < width; ++x) {
          if (xeqy(x, y)) {
            bitmap1[y][x] = 0xff00ff7f;
            bitmap2[y][width - 1 - x] = 0x00ff007f;
            
          } else {
            bitmap1[y][x] = 0x00000000;
            bitmap2[y][width - 1 - x] = 0x00000000;
            
          }
        }
        
      }
      return new AlphaBlending(bitmap1, bitmap2, new RGBA());
    }
    
    private boolean xeqy(final int x, final int y) {
      return Math.abs(x - y) < 1;
    }
    
    @Override
    public IOptimizerSuite getOptimizer() {
      return OPTIMIZER;
    }
    
    @Override
    public String getCaption() {
      return "Alphablending";
    }
    
    @Override
    public String getLabel() {
      return "Alphablending";
    }
    
  }
  
}
