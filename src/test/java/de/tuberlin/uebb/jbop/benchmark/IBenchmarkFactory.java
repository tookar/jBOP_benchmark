package de.tuberlin.uebb.jbop.benchmark;

import de.tuberlin.uebb.jbop.optimizer.IOptimizerSuite;

public interface IBenchmarkFactory<t> {
  
  IBenchmark<t> create();
  
  IOptimizerSuite getOptimizer();
  
  String getCaption();
  
  String getLabel();
}
