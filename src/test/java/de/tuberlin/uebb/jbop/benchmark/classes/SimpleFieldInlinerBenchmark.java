package de.tuberlin.uebb.jbop.benchmark.classes;

import de.tuberlin.uebb.jbop.benchmark.AbstractBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmarkFactory;

public class SimpleFieldInlinerBenchmark extends AbstractBenchmark {
  
  private final double doubleField;
  
  SimpleFieldInlinerBenchmark(final double doubleField) {
    super();
    this.doubleField = doubleField;
  }
  
  @Override
  public double run() {
    return doubleField;
  }
  
  public static final class Factory implements IBenchmarkFactory {
    
    @Override
    public IBenchmark create() {
      return new SimpleFieldInlinerBenchmark(123.4);
    }
  }
}
