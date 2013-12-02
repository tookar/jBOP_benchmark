package de.tuberlin.uebb.jbop.benchmark;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class AbstractBenchmark implements IBenchmark, Comparable<IBenchmark> {
  
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }
  
  @Override
  public boolean equals(final Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }
  
  @Override
  public int compareTo(final IBenchmark o) {
    return CompareToBuilder.reflectionCompare(this, o);
  }
}
