package de.tuberlin.uebb.jbop.benchmark;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;

import de.tuberlin.uebb.jbop.benchmark.classes.SimpleFieldInlinerBenchmark;
import de.tuberlin.uebb.jbop.exception.JBOPClassException;

public class BenchmarkRunner {
  
  private final IBenchmarkFactory factory;
  
  public static void main(final String[] args) throws JBOPClassException {
    final IBenchmarkFactory factory = new /**/SimpleFieldInlinerBenchmark.Factory() /**//*
                                                                                           * ComplexBenchmark.Factory
                                                                                           * ()
                                                                                           */;
    final BenchmarkResult benchmark = new BenchmarkRunner(factory).benchmark();
    benchmark.setLatex(true);
    System.out.println(benchmark);
  }
  
  public BenchmarkRunner(final IBenchmarkFactory factory) {
    this.factory = factory;
  }
  
  public BenchmarkResult benchmark() throws JBOPClassException {
    final BenchmarkResult result = new BenchmarkResult(factory);
    final ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    
    for (int i = 0; i < 10; ++i) {
      durchlauf(result, bean, i);
    }
    result.setLatex(true);
    return result;
  }
  
  private void durchlauf(final BenchmarkResult result, final ThreadMXBean bean, final int i) throws JBOPClassException {
    final long startCreate = bean.getCurrentThreadCpuTime();
    final IBenchmark example = factory.create();
    final long endCreate = bean.getCurrentThreadCpuTime();
    
    final long startOptimize = bean.getCurrentThreadCpuTime();
    final IBenchmark optimizedExample = factory.getOptimizer().optimize(example, "_Benchmark");
    final long endOptimize = bean.getCurrentThreadCpuTime();
    
    final long timeCreate = endCreate - startCreate;
    final long timeOptimize = endOptimize - startOptimize;
    
    final int cycles = (int) Math.pow(10, i);
    final long timeRunNormal = run(example, cycles, bean);
    final long timeRunOptimized = run(optimizedExample, cycles, bean);
    
    result.addResults(i, timeCreate, timeOptimize, timeRunNormal, timeRunOptimized);
  }
  
  private long run(final IBenchmark benchmark, final int cycles, final ThreadMXBean bean) {
    System.gc();
    final long startRunNormal = bean.getCurrentThreadCpuTime();
    final int iterations = 5;
    for (int i = 0; i < iterations; ++i) {
      for (int c = 0; c < cycles; ++c) {
        benchmark.run();
      }
    }
    final long endRunNormal = bean.getCurrentThreadCpuTime();
    return (BigDecimal.valueOf(endRunNormal).subtract(BigDecimal.valueOf(startRunNormal))).divide(
        BigDecimal.valueOf(iterations)).longValue();
  }
}
