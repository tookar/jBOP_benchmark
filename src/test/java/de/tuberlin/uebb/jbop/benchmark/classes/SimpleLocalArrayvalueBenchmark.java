package de.tuberlin.uebb.jbop.benchmark.classes;

import java.util.Arrays;

import de.tuberlin.uebb.jbop.benchmark.AbstractBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmarkFactory;
import de.tuberlin.uebb.jbop.benchmark.SimpleOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizerSuite;
import de.tuberlin.uebb.jbop.optimizer.annotations.ImmutableArray;
import de.tuberlin.uebb.jbop.optimizer.array.FieldArrayValueInliner;

public class SimpleLocalArrayvalueBenchmark extends AbstractBenchmark {
  
  @ImmutableArray
  private final double[][] doubleField;
  
  SimpleLocalArrayvalueBenchmark(final double[][] doubleField) {
    super();
    this.doubleField = doubleField;
  }
  
  @Override
  public double run() {
    final double[] doubleLocal = doubleField[0];
    return doubleLocal[0] + doubleLocal[1] + doubleLocal[2] + doubleLocal[3] + doubleLocal[4] + doubleLocal[5];
  }
  
  public static final class Factory implements IBenchmarkFactory {
    
    @Override
    public IBenchmark create() {
      return new SimpleLocalArrayvalueBenchmark(new double[][] {
        {
            123.4, 234.5, 345.6, 456.7, 567.8, 678.9
        }
      });
    }
    
    @Override
    public IOptimizerSuite getOptimizer() {
      return new SimpleOptimizer(Arrays.<IOptimizer> asList(new FieldArrayValueInliner()));
    }
    
    @Override
    public String getCaption() {
      return "Mikro Benchmark des LocalArrayValueInliners";
    }
    
    @Override
    public String getLabel() {
      return "localarrayvaluebenchmark";
    }
  }
}
