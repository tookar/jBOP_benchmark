package de.tuberlin.uebb.jbop.benchmark.classes;

import java.util.logging.Logger;

import de.tuberlin.uebb.jbop.benchmark.AbstractBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmarkFactory;
import de.tuberlin.uebb.jbop.optimizer.annotations.ImmutableArray;
import de.tuberlin.uebb.jbop.optimizer.annotations.Optimizable;
import de.tuberlin.uebb.jbop.optimizer.annotations.StrictLoops;

public class ComplexBenchmark extends AbstractBenchmark {
  
  private final Chain chain;
  @ImmutableArray
  private final double[][] doubleArray;
  
  public ComplexBenchmark(final Chain chain, final double[][] doubleArray) {
    this.chain = chain;
    this.doubleArray = doubleArray;
  }
  
  @Override
  @Optimizable
  @StrictLoops
  public double run() {
    if (chain == null) {
      return -1;
    }
    for (int i = 0; i < doubleArray.length; ++i) {
      if (chain.chainArray.length != doubleArray[i].length) {
        Logger.getLogger("").fine("This will crash...");
      }
    }
    double res = 0;
    for (int i = 0; i < doubleArray.length; ++i) {
      final double[] localDoubleArray = doubleArray[0];
      for (int j = 0; j < localDoubleArray.length; ++j) {
        for (int k = 0; k < 8; ++k) {
          res += chain.chainArray[j] + localDoubleArray[j];
        }
      }
    }
    return res;
  }
  
  public static final class Factory implements IBenchmarkFactory {
    
    final Chain chain = new Chain(new double[] {
        1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0,//
    });
    final double[][] doubleArray = new double[][] {
        {
            11.0, 12.0, 13.0, 14.0, 10.0, 16.0, 17.0, 18.0, 19.0, 20.0,//
        }, {
            21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0, 28.0, 29.0, 30.0,//
        },
    };
    
    @Override
    public IBenchmark create() {
      return new ComplexBenchmark(chain, doubleArray);
    }
  }
}
