package de.tuberlin.uebb.jbop.benchmark.classes;

import static org.objectweb.asm.Opcodes.DADD;
import static org.objectweb.asm.Opcodes.DCONST_1;
import static org.objectweb.asm.Opcodes.DDIV;
import static org.objectweb.asm.Opcodes.DMUL;
import static org.objectweb.asm.Opcodes.DSUB;
import static org.objectweb.asm.Opcodes.LDC;

import java.util.Arrays;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import de.tuberlin.uebb.jbop.benchmark.AbstractBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmarkFactory;
import de.tuberlin.uebb.jbop.benchmark.SimpleOptimizer;
import de.tuberlin.uebb.jbop.optimizer.ClassNodeBuilder;
import de.tuberlin.uebb.jbop.optimizer.IOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizerSuite;
import de.tuberlin.uebb.jbop.optimizer.arithmetic.ArithmeticExpressionInterpreter;

public class SimpleArithmeticBenchmark extends AbstractBenchmark {
  
  @Override
  public double run() {
    
    return ((1.0 * 2.0) + (3.0 / 4.0)) - 5.0;
  }
  
  public static final class Factory implements IBenchmarkFactory {
    
    /**
     * get the code above without pre evaluation
     */
    @Override
    public IBenchmark create() {
      try {
        return (IBenchmark) ClassNodeBuilder
            .createClass("de.tuberlin.uebb.jbop.benchmark.classes.SimpleArithmeticBenchmarkImpl").//
            implementInterface(Type.getInternalName(IBenchmark.class)).//
            addMethod("run", "()D", Opcodes.ACC_PUBLIC).//
            add(DCONST_1).//
            add(LDC, 2.0).//
            add(DMUL).//
            add(LDC, 3.0).//
            add(LDC, 4.0).//
            add(DDIV).//
            add(DADD).//
            add(LDC, 5.0).//
            add(DSUB).//
            addReturn().//
            instance();
      } catch (final Exception e) {
        throw new RuntimeException(e);
      }
    }
    
    @Override
    public IOptimizerSuite getOptimizer() {
      return new SimpleOptimizer(Arrays.<IOptimizer> asList(new ArithmeticExpressionInterpreter()));
    }
    
    @Override
    public String getCaption() {
      return "Mikro Benchmark des ArithmeticExressionInterpreters";
    }
    
    @Override
    public String getLabel() {
      return "arithmeticbenchmark";
    }
    
  }
}
