package de.tuberlin.uebb.jbop.benchmark.classes;

import java.util.Arrays;

import de.tuberlin.uebb.jbop.benchmark.AbstractBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmarkFactory;
import de.tuberlin.uebb.jbop.benchmark.SimpleOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizerSuite;
import de.tuberlin.uebb.jbop.optimizer.var.RemoveUnusedLocalVars;

public class SimpleUnusedVarBenchmark extends AbstractBenchmark<Double> {
  
  @SuppressWarnings("unused")
  @Override
  public Double run() {
    final double local1 = 1.0;
    final double local2 = 1.0;
    final double local3 = 1.0;
    final double local4 = 1.0;
    final double local5 = 1.0;
    final double local6 = 1.0;
    return 1.0;
  }
  
  public static final class Factory implements IBenchmarkFactory<Double> {
    
    @Override
    public IBenchmark<Double> create() {
      return new SimpleUnusedVarBenchmark();
    }
    
    @Override
    public IOptimizerSuite getOptimizer() {
      return new SimpleOptimizer(Arrays.<IOptimizer> asList(new RemoveUnusedLocalVars()));
    }
    
    @Override
    public String getCaption() {
      return "Mikro Benchmark von RemoveUnusedVars";
    }
    
    @Override
    public String getLabel() {
      return "unusedbenchmark";
    }
  }
}
