package de.tuberlin.uebb.jbop.benchmark.classes;

import static org.objectweb.asm.Opcodes.ACC_BRIDGE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SYNTHETIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.DADD;
import static org.objectweb.asm.Opcodes.DCONST_1;
import static org.objectweb.asm.Opcodes.DDIV;
import static org.objectweb.asm.Opcodes.DMUL;
import static org.objectweb.asm.Opcodes.DSUB;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
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

public class SimpleArithmeticBenchmark extends AbstractBenchmark<Double> {
  
  @Override
  public Double run() {
    
    return 1.0 * 2.0 + 3.0 / 4.0 - 5.0;
  }
  
  public static final class Factory implements IBenchmarkFactory<Double> {
    
    /**
     * get the code above without pre evaluation
     */
    @Override
    public IBenchmark<Double> create() {
      try {
        final ClassNodeBuilder builder = ClassNodeBuilder
            .createClass("de.tuberlin.uebb.jbop.benchmark.classes.SimpleArithmeticBenchmarkImpl").//
            implementInterface(Type.getInternalName(IBenchmark.class)).//
            withSignature("Ljava/lang/Object;Lde/tuberlin/uebb/jbop/benchmark/IBenchmark<Ljava/lang/Double;>;");//
        builder.addMethod("run", "()Ljava/lang/Double;", Opcodes.ACC_PUBLIC).//
            add(DCONST_1).//
            add(LDC, 2.0).//
            add(DMUL).//
            add(LDC, 3.0).//
            add(LDC, 4.0).//
            add(DDIV).//
            add(DADD).//
            add(LDC, 5.0).//
            add(DSUB).//
            invoke(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;").//
            addReturn().//
            addMethod("run", "()Ljava/lang/Object;", ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC).//
            add(ALOAD, 0).//
            invoke(INVOKEVIRTUAL, builder, "run").//
            addReturn();
        return (IBenchmark<Double>) builder.//
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
