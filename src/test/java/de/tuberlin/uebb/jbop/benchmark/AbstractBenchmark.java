package de.tuberlin.uebb.jbop.benchmark;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class AbstractBenchmark<t> implements IBenchmark<t>, Comparable<IBenchmark<t>> {
  
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }
  
  @Override
  public boolean equals(final Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }
  
  @Override
  public int compareTo(final IBenchmark<t> o) {
    return CompareToBuilder.reflectionCompare(this, o);
  }
}
