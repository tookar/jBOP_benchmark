package de.tuberlin.uebb.jbop.benchmark;

import de.tuberlin.uebb.jbop.optimizer.IOptimizerSuite;

public interface IBenchmarkFactory {
  
  IBenchmark create();
  
  IOptimizerSuite getOptimizer();
}
