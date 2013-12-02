package de.tuberlin.uebb.jbop.benchmark.classes;

import de.tuberlin.uebb.jbop.benchmark.AbstractBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmarkFactory;
import de.tuberlin.uebb.jbop.optimizer.annotations.ImmutableArray;

public class SimpleArrayLengthBenchmark extends AbstractBenchmark {
  
  @ImmutableArray
  private final double[] doubleField;
  
  SimpleArrayLengthBenchmark(final double[] doubleField) {
    super();
    this.doubleField = doubleField;
  }
  
  @Override
  public double run() {
    return doubleField.length;
  }
  
  public static final class Factory implements IBenchmarkFactory {
    
    @Override
    public IBenchmark create() {
      return new SimpleArrayLengthBenchmark(new double[] {
        123.4
      });
    }
  }
}
