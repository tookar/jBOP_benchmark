package de.tuberlin.uebb.jbop.benchmark.classes;

import java.util.Arrays;

import de.tuberlin.uebb.jbop.benchmark.AbstractBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmarkFactory;
import de.tuberlin.uebb.jbop.benchmark.SimpleOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizerSuite;
import de.tuberlin.uebb.jbop.optimizer.loop.ForLoopUnroller;

public class SimpleLoopBenchmark extends AbstractBenchmark {
  
  @Override
  public double run() {
    double d = 1.0;
    for (int i = 0; i < 100; ++i) {
      d += d;
    }
    return d;
  }
  
  public static final class Factory implements IBenchmarkFactory {
    
    @Override
    public IBenchmark create() {
      return new SimpleLoopBenchmark();
    }
    
    @Override
    public IOptimizerSuite getOptimizer() {
      return new SimpleOptimizer(Arrays.<IOptimizer> asList(new ForLoopUnroller()));
    }
    
    @Override
    public String getCaption() {
      return "Mikro Benchmark des ForLoopUnrollers";
    }
    
    @Override
    public String getLabel() {
      return "loopbenchmark";
    }
  }
}
