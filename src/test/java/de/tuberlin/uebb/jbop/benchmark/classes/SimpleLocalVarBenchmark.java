package de.tuberlin.uebb.jbop.benchmark.classes;

import java.util.Arrays;

import de.tuberlin.uebb.jbop.benchmark.AbstractBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmarkFactory;
import de.tuberlin.uebb.jbop.benchmark.SimpleOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizerSuite;
import de.tuberlin.uebb.jbop.optimizer.var.LocalVarInliner;

public class SimpleLocalVarBenchmark extends AbstractBenchmark<Double> {
  
  @Override
  public Double run() {
    double local = 1.0;
    local = 2.0;
    local = 3.0;
    return local;
  }
  
  public static final class Factory implements IBenchmarkFactory<Double> {
    
    @Override
    public IBenchmark<Double> create() {
      return new SimpleLocalVarBenchmark();
    }
    
    @Override
    public IOptimizerSuite getOptimizer() {
      return new SimpleOptimizer(Arrays.<IOptimizer> asList(new LocalVarInliner()));
    }
    
    @Override
    public String getCaption() {
      return "Mikro Benchmark des LocalVarInliners";
    }
    
    @Override
    public String getLabel() {
      return "localvarbenchmark";
    }
  }
}
