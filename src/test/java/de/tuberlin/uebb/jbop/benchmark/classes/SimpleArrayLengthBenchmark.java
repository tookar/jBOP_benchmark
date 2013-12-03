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

public class SimpleArrayLengthBenchmark extends AbstractBenchmark {
  
  @ImmutableArray
  private final double[] doubleField;
  
  SimpleArrayLengthBenchmark(final double[] doubleField) {
    super();
    this.doubleField = doubleField;
  }
  
  @Override
  public double run() {
    return doubleField.length + doubleField.length + doubleField.length + doubleField.length + doubleField.length
        + doubleField.length;
  }
  
  public static final class Factory implements IBenchmarkFactory {
    
    @Override
    public IBenchmark create() {
      return new SimpleArrayLengthBenchmark(new double[] {
          123.4, 234.5, 345.6, 456.7, 567.8, 678.9
      });
    }
    
    @Override
    public IOptimizerSuite getOptimizer() {
      return new SimpleOptimizer(Arrays.<IOptimizer> asList(new FieldArrayLengthInliner()));
    }
    
    @Override
    public String getCaption() {
      return "Mikro Benchmark des FieldArrayLengthInliners";
    }
    
    @Override
    public String getLabel() {
      return "arraylengthbenchmark";
    }
    
  }
}
