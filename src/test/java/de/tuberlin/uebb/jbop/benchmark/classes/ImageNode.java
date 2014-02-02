package de.tuberlin.uebb.jbop.benchmark.classes;

import de.tuberlin.uebb.jbop.optimizer.annotations.ImmutableArray;

public class ImageNode {
  
  public final ImageNode next;
  
  @ImmutableArray
  public final int[][] bitmap;
  
  ImageNode(final ImageNode next, final int[][] bitmap) {
    this.next = next;
    this.bitmap = bitmap;
  }
  
}
