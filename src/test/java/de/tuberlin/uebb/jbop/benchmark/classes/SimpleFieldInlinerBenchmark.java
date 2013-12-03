package de.tuberlin.uebb.jbop.benchmark.classes;

import java.util.Arrays;

import de.tuberlin.uebb.jbop.benchmark.AbstractBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmarkFactory;
import de.tuberlin.uebb.jbop.benchmark.SimpleOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizerSuite;
import de.tuberlin.uebb.jbop.optimizer.var.FinalFieldInliner;

public class SimpleFieldInlinerBenchmark extends AbstractBenchmark {
  
  private final Chain chain;
  
  SimpleFieldInlinerBenchmark(final Chain chain) {
    super();
    this.chain = chain;
  }
  
  @Override
  public double run() {
    return chain.chain.chain.chain.doubleField;
  }
  
  public static final class Factory implements IBenchmarkFactory {
    
    @Override
    public IBenchmark create() {
      return new SimpleFieldInlinerBenchmark(new Chain(null, new Chain(null, new Chain(null, new Chain(new double[] {
        1.0
      }, null)))));
    }
    
    @Override
    public IOptimizerSuite getOptimizer() {
      return new SimpleOptimizer(Arrays.<IOptimizer> asList(new FinalFieldInliner()));
    }
    
    @Override
    public String getCaption() {
      return "Mikro Benchmark des FinalFieldInliners";
    }
    
    @Override
    public String getLabel() {
      return "finalfieldbenchmark";
    }
  }
}
