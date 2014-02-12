package de.tuberlin.uebb.jbop.benchmark.classes;

import de.tuberlin.uebb.jbop.optimizer.annotations.ImmutableArray;

public class Chain {
  
  @ImmutableArray
  public final double[] chainArray;
  
  public final Chain chain;
  
  public final double doubleField;
  
  public Chain(final double[] chainArray, final Chain chain) {
    this.chainArray = chainArray;
    if (chainArray == null) {
      doubleField = 1.0;
    } else {
      doubleField = chainArray[0];
    }
    this.chain = chain;
  }
  
}
