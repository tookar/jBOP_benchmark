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

public class SimpleArrayvalueBenchmark extends AbstractBenchmark {
  
  @ImmutableArray
  private final double[] doubleField;
  
  SimpleArrayvalueBenchmark(final double[] doubleField) {
    super();
    this.doubleField = doubleField;
  }
  
  @Override
  public double run() {
    return doubleField[0] + doubleField[1] + doubleField[2] + doubleField[3] + doubleField[4] + doubleField[5];
  }
  
  public static final class Factory implements IBenchmarkFactory {
    
    @Override
    public IBenchmark create() {
      return new SimpleArrayvalueBenchmark(new double[] {
          123.4, 234.5, 345.6, 456.7, 567.8, 678.9
      });
    }
    
    @Override
    public IOptimizerSuite getOptimizer() {
      return new SimpleOptimizer(Arrays.<IOptimizer> asList(new FieldArrayValueInliner()));
    }
    
    @Override
    public String getCaption() {
      return "Mikro Benchmark des FieldArrayValueInliners";
    }
    
    @Override
    public String getLabel() {
      return "arrayvaluebenchmark";
    }
  }
}
