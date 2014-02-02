package de.tuberlin.uebb.jbop.benchmark.classes;

import java.util.Arrays;

import de.tuberlin.uebb.jbop.benchmark.AbstractBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmarkFactory;
import de.tuberlin.uebb.jbop.benchmark.SimpleOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizerSuite;
import de.tuberlin.uebb.jbop.optimizer.annotations.ImmutableArray;
import de.tuberlin.uebb.jbop.optimizer.array.FieldArrayLengthInliner;

public class SimpleLocalArrayLengthBenchmark extends AbstractBenchmark<Double> {
  
  @ImmutableArray
  private final double[][] doubleField;
  
  SimpleLocalArrayLengthBenchmark(final double[][] doubleField) {
    super();
    this.doubleField = doubleField;
  }
  
  @Override
  public Double run() {
    final double[] doubleLocal1 = new double[25];
    final double[] doubleLocal2 = doubleField[0];
    return (double) doubleLocal1.length + doubleLocal2.length;
  }
  
  public static final class Factory implements IBenchmarkFactory<Double> {
    
    @Override
    public IBenchmark<Double> create() {
      return new SimpleLocalArrayLengthBenchmark(new double[][] {
        {
            123.4, 234.5, 345.6, 456.7, 567.8, 678.9
        }
      });
    }
    
    @Override
    public IOptimizerSuite getOptimizer() {
      return new SimpleOptimizer(Arrays.<IOptimizer> asList(new FieldArrayLengthInliner()));
    }
    
    @Override
    public String getCaption() {
      return "Mikro Benchmark des LocalArrayLengthInliners";
    }
    
    @Override
    public String getLabel() {
      return "localarraylengthbenchmark";
    }
    
  }
}
