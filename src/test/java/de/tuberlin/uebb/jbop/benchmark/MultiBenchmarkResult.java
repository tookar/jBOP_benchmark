package de.tuberlin.uebb.jbop.benchmark;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class MultiBenchmarkResult {
  
  private final List<BenchmarkResult> results = new ArrayList<>();
  private final List<String> includes = new ArrayList<>();
  private final Map<IBenchmarkFactory, Throwable> errors = new HashMap<IBenchmarkFactory, Throwable>();
  
  public void addResult(final BenchmarkResult result) {
    results.add(result);
    includes.add(result.getInclude());
  }
  
  public void addError(final IBenchmarkFactory factory, final Throwable error) {
    errors.put(factory, error);
  }
  
  public List<BenchmarkResult> getResults() {
    return Collections.unmodifiableList(results);
  }
  
  public String getIncludes() {
    return StringUtils.join(includes, "\n");
  }
  
  public Map<IBenchmarkFactory, Throwable> getErrors() {
    return Collections.unmodifiableMap(errors);
  }
  
  public void store(final File parent) throws IOException {
    final List<IOException> exceptions = new ArrayList<>();
    for (final BenchmarkResult result : results) {
      try {
        result.store(parent);
      } catch (final IOException e) {
        exceptions.add(e);
      }
    }
    if (exceptions.size() > 0) {
      final IOException ioException = new IOException();
      for (final IOException exception : exceptions) {
        ioException.addSuppressed(exception);
      }
      throw ioException;
    }
  }
  
  public boolean hasErrors() {
    return !errors.isEmpty();
  }
  
}
