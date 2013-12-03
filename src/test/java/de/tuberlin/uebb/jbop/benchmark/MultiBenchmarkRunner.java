package de.tuberlin.uebb.jbop.benchmark;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import de.tuberlin.uebb.jbop.benchmark.classes.SimpleFieldInlinerBenchmark;
import de.tuberlin.uebb.jbop.exception.JBOPClassException;

public class MultiBenchmarkRunner {
  
  private final List<IBenchmarkFactory> benchmarks = new ArrayList<>();
  private File parent;
  
  public MultiBenchmarkResult run() {
    final MultiBenchmarkResult multiResult = new MultiBenchmarkResult();
    for (final IBenchmarkFactory factory : benchmarks) {
      final BenchmarkRunner benchmarkRunner = new BenchmarkRunner(factory);
      try {
        final BenchmarkResult result = benchmarkRunner.run();
        multiResult.addResult(result);
      } catch (final JBOPClassException e) {
        multiResult.addError(factory, e);
      }
    }
    return multiResult;
  }
  
  public void addBenchmark(final IBenchmarkFactory factory) {
    benchmarks.add(factory);
  }
  
  public static void main(final String[] args) {
    final MultiBenchmarkRunner multiBenchmarkRunner = new MultiBenchmarkRunner();
    multiBenchmarkRunner.addBenchmark(new SimpleFieldInlinerBenchmark.Factory());
    final MultiBenchmarkResult result = multiBenchmarkRunner.run();
    if (result.hasErrors()) {
      System.err.println("There were errors: ");
      for (final Entry<IBenchmarkFactory, Throwable> entry : result.getErrors().entrySet()) {
        System.err.println("\t" + entry.getKey().getCaption() + ": " + entry.getValue().getMessage());
      }
    }
    final File parent = new File(".");
    try {
      result.store(parent);
    } catch (final IOException e) {
      System.err.println("Error while storing result: " + e);
    }
    System.out.println(result.getIncludes());
  }
}
