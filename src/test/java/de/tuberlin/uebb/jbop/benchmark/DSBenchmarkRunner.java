package de.tuberlin.uebb.jbop.benchmark;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;

import de.tuberlin.uebb.jbop.example.DSExample;
import de.tuberlin.uebb.jbop.example.DSExampleOnlyCompose;
import de.tuberlin.uebb.jbop.example.DSExampleOrig;
import de.tuberlin.uebb.jbop.optimizer.IOptimizerSuite;

public class DSBenchmarkRunner {
  
  private final class DSBenchmark implements IBenchmarkFactory<Object> {
    
    @Override
    public IOptimizerSuite getOptimizer() {
      return null;
    }
    
    @Override
    public String getLabel() {
      return "ds_fullopt__2";
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
    final BenchmarkResultDS result = new DSBenchmarkRunner().run();
    final File parent = new File("/mnt/D/uni/diplom/tmp/tex");
    try {
      result.store(parent);
    } catch (final IOException e) {
      System.err.println("Error while storing result: " + e);
    }
  }
  
  public BenchmarkResultDS run() {
    return benchmark();
  }
  
  public BenchmarkResultDS benchmark() {
    final IBenchmarkFactory<?> factory = new DSBenchmark();
    final BenchmarkResultDS result = new BenchmarkResultDS(factory);
    final ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    
    for (int i = 0; i < 10; ++i) {
      durchlauf(result, bean, i);
    }
    result.setLatex(true);
    return result;
  }
  
  private void durchlauf(final BenchmarkResultDS result, final ThreadMXBean bean, final int i) {
    final DSExampleOrig exampleOrig = new DSExampleOrig();
    final DSExample exampleOpt = new DSExample();
    final DSExampleOnlyCompose exampleOnly = new DSExampleOnlyCompose();
    final long startCreate = bean.getCurrentThreadCpuTime();
    exampleOrig.createCompilers();
    final long endCreate = bean.getCurrentThreadCpuTime();
    
    final long startOptimize = bean.getCurrentThreadCpuTime();
    exampleOpt.createCompilers();
    final long endOptimize = bean.getCurrentThreadCpuTime();
    
    final long startOnly = bean.getCurrentThreadCpuTime();
    exampleOnly.createCompilers();
    final long endOnly = bean.getCurrentThreadCpuTime();
    
    final long timeCreate = endCreate - startCreate;
    final long timeOptimize = endOptimize - startOptimize;
    final long timeOnly = endOnly - startOnly;
    
    final int cycles = (int) Math.pow(10, i);
    final long timeRunNormal1 = run(exampleOrig, cycles, bean);
    final long timeRunOptimized1 = run(exampleOpt, cycles, bean);
    final long timeRunOnly1 = run(exampleOnly, cycles, bean);
    final long timeRunOnly2 = run(exampleOnly, cycles, bean);
    final long timeRunOptimized2 = run(exampleOpt, cycles, bean);
    final long timeRunNormal2 = run(exampleOrig, cycles, bean);
    
    final long timeRunNormal = (timeRunNormal1 + timeRunNormal2) / 2;
    final long timeRunOptimized = (timeRunOptimized1 + timeRunOptimized2) / 2;
    final long timeRunOnly = (timeRunOnly1 + timeRunOnly2) / 2;
    result.addResults(i, timeCreate, timeOptimize, timeOnly, timeRunNormal, timeRunOptimized, timeRunOnly);
  }
  
  private long run(final DSExample benchmark, final int cycles, final ThreadMXBean bean) {
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
}
