package de.tuberlin.uebb.jbop.benchmark;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;

import de.tuberlin.uebb.jbop.example.DSExampleOnlyCompose;
import de.tuberlin.uebb.jbop.example.DSExampleOrig;
import de.tuberlin.uebb.jbop.optimizer.IOptimizerSuite;

public class DSOnlyComposeBenchmarkRunner {
  
  private final class DSBenchmark implements IBenchmarkFactory<Object> {
    
    @Override
    public IOptimizerSuite getOptimizer() {
      return null;
    }
    
    @Override
    public String getLabel() {
      return "DerivativeStructure";
    }
    
    @Override
    public String getCaption() {
      return "DerivativeStructureTest";
    }
    
    @Override
    public IBenchmark<Object> create() {
      return null;
    }
  }
  
  public static void main(final String[] args) {
    final BenchmarkResult result = new DSOnlyComposeBenchmarkRunner().run();
    final File parent = new File(".");
    try {
      result.store(parent);
    } catch (final IOException e) {
      System.err.println("Error while storing result: " + e);
    }
  }
  
  public BenchmarkResult run() {
    return benchmark();
  }
  
  public BenchmarkResult benchmark() {
    final IBenchmarkFactory<?> factory = new DSBenchmark();
    final BenchmarkResult result = new BenchmarkResult(factory);
    final ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    
    for (int i = 0; i < 10; ++i) {
      durchlauf(result, bean, i);
    }
    result.setLatex(true);
    return result;
  }
  
  private void durchlauf(final BenchmarkResult result, final ThreadMXBean bean, final int i) {
    final DSExampleOrig exampleOrig = new DSExampleOrig();
    final DSExampleOnlyCompose exampleOpt = new DSExampleOnlyCompose();
    final long startCreate = bean.getCurrentThreadCpuTime();
    exampleOrig.createCompilers();
    final long endCreate = bean.getCurrentThreadCpuTime();
    
    final long startOptimize = bean.getCurrentThreadCpuTime();
    exampleOpt.createCompilers();
    final long endOptimize = bean.getCurrentThreadCpuTime();
    
    final long timeCreate = endCreate - startCreate;
    final long timeOptimize = endOptimize - startOptimize;
    
    final int cycles = (int) Math.pow(10, i);
    final long timeRunNormal = run(exampleOrig, cycles, bean);
    final long timeRunOptimized = run(exampleOpt, cycles, bean);
    
    result.addResults(i, timeCreate, timeOptimize, timeRunNormal, timeRunOptimized);
  }
  
  private long run(final DSExampleOnlyCompose benchmark, final int cycles, final ThreadMXBean bean) {
    System.gc();
    final long startRunNormal = bean.getCurrentThreadCpuTime();
    final int iterations = 5;
    for (int i = 0; i < iterations; ++i) {
      for (int c = 0; c < cycles; ++c) {
        benchmark.run();
      }
    }
    final long endRunNormal = bean.getCurrentThreadCpuTime();
    return BigDecimal.valueOf(endRunNormal).subtract(BigDecimal.valueOf(startRunNormal))
        .divide(BigDecimal.valueOf(iterations)).longValue();
  }
  
  private long run(final DSExampleOrig benchmark, final int cycles, final ThreadMXBean bean) {
    System.gc();
    final long startRunNormal = bean.getCurrentThreadCpuTime();
    final int iterations = 5;
    for (int i = 0; i < iterations; ++i) {
      for (int c = 0; c < cycles; ++c) {
        benchmark.run();
      }
    }
    final long endRunNormal = bean.getCurrentThreadCpuTime();
    return BigDecimal.valueOf(endRunNormal).subtract(BigDecimal.valueOf(startRunNormal))
        .divide(BigDecimal.valueOf(iterations)).longValue();
  }
}
