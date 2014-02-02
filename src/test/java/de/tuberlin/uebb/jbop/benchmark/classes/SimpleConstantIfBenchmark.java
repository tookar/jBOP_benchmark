package de.tuberlin.uebb.jbop.benchmark.classes;

import static org.objectweb.asm.Opcodes.ACC_BRIDGE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SYNTHETIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.BIPUSH;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.ICONST_2;
import static org.objectweb.asm.Opcodes.ICONST_5;
import static org.objectweb.asm.Opcodes.IF_ICMPGE;
import static org.objectweb.asm.Opcodes.IF_ICMPGT;
import static org.objectweb.asm.Opcodes.IF_ICMPLE;
import static org.objectweb.asm.Opcodes.IF_ICMPLT;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.LDC;

import java.util.Arrays;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.LabelNode;

import de.tuberlin.uebb.jbop.benchmark.AbstractBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmark;
import de.tuberlin.uebb.jbop.benchmark.IBenchmarkFactory;
import de.tuberlin.uebb.jbop.benchmark.SimpleOptimizer;
import de.tuberlin.uebb.jbop.optimizer.ClassNodeBuilder;
import de.tuberlin.uebb.jbop.optimizer.IOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizerSuite;
import de.tuberlin.uebb.jbop.optimizer.controlflow.ConstantIfInliner;

public class SimpleConstantIfBenchmark extends AbstractBenchmark<Double> {
  
  @SuppressWarnings("unused")
  @Override
  public Double run() {
    if (1 < 2) {
      if (2 > 1) {
        if (1 <= 5) {
          if (9 >= 1) {
            return 10.0;
          }
          return 9.0;
        }
        return 8.0;
      }
      return 7.0;
    }
    return 6.0;
  }
  
  public static final class Factory implements IBenchmarkFactory<Double> {
    
    /**
     * get the code above without dead-code check
     */
    @Override
    public IBenchmark<Double> create() {
      try {
        final LabelNode l1 = new LabelNode();
        final LabelNode l2 = new LabelNode();
        final LabelNode l3 = new LabelNode();
        final LabelNode l4 = new LabelNode();
        final ClassNodeBuilder builder = ClassNodeBuilder
            .createClass("de.tuberlin.uebb.jbop.benchmark.classes.SimpleConstantIfBenchmarkImpl").//
            implementInterface(Type.getInternalName(IBenchmark.class)).//
            withSignature("Ljava/lang/Object;Lde/tuberlin/uebb/jbop/benchmark/IBenchmark<Ljava/lang/Double;>;");
        return (IBenchmark<Double>) builder.//
            addMethod("run", "()Ljava/lang/Double;").//
            add(ICONST_1).//
            add(ICONST_2).//
            add(IF_ICMPGE, l1).//
            add(ICONST_2).//
            add(ICONST_1).//
            add(IF_ICMPLE, l2).//
            add(ICONST_1).//
            add(ICONST_5).//
            add(IF_ICMPGT, l3).//
            add(BIPUSH, 9).//
            add(ICONST_1).//
            add(IF_ICMPLT, l4).//
            add(LDC, 10.0).//
            invoke(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;").//
            addReturn().//
            addInsn(l4).//
            add(LDC, 9.0).//
            invoke(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;").//
            addReturn().//
            addInsn(l3).//
            add(LDC, 8.0).//
            invoke(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;").//
            addReturn().//
            addInsn(l2).//
            add(LDC, 7.0).//
            invoke(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;").//
            addReturn().// /
            addInsn(l1).//
            add(LDC, 6.0).//
            invoke(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;").//
            addReturn().//
            addMethod("run", "()Ljava/lang/Object;", ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC).//
            add(ALOAD, 0).//
            invoke(INVOKEVIRTUAL, builder, "run").//
            addReturn().//
            instance();
      } catch (final Exception e) {
        throw new RuntimeException(e);
      }
    }
    
    @Override
    public IOptimizerSuite getOptimizer() {
      return new SimpleOptimizer(Arrays.<IOptimizer> asList(new ConstantIfInliner(null)));
    }
    
    @Override
    public String getCaption() {
      return "Mikro Benchmark des ConstantIfInliners";
    }
    
    @Override
    public String getLabel() {
      return "constantifbenchmark";
    }
  }
}
