package de.tuberlin.uebb.jbop.benchmark.classes;

import de.tuberlin.uebb.jbop.optimizer.annotations.ImmutableArray;

public class Chain {
  
  @ImmutableArray
  public final double[] chainArray;
  
  public Chain(final double[] chainArray) {
    this.chainArray = chainArray;
  }
  
}
