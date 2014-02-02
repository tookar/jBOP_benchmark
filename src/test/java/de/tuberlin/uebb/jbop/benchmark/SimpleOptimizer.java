package de.tuberlin.uebb.jbop.benchmark;

import java.util.List;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

import de.tuberlin.uebb.jbop.access.OptimizerUtils;
import de.tuberlin.uebb.jbop.exception.JBOPClassException;
import de.tuberlin.uebb.jbop.optimizer.IClassNodeAware;
import de.tuberlin.uebb.jbop.optimizer.IInputObjectAware;
import de.tuberlin.uebb.jbop.optimizer.IOptimizer;
import de.tuberlin.uebb.jbop.optimizer.IOptimizerSuite;

public class SimpleOptimizer implements IOptimizerSuite {
  
  private final List<IOptimizer> optimizers;
  
  public SimpleOptimizer(final List<IOptimizer> optimizers) {
    super();
    this.optimizers = optimizers;
  }
  
  @Override
  public <T> T optimize(final T input, final String suffix) throws JBOPClassException {
    if (OptimizerUtils.existsInstance(input)) {
      return OptimizerUtils.getInstanceFor(input);
    }
    
    final ClassNode classNode = OptimizerUtils.readClass(input);
    for (final IOptimizer optimizer : optimizers) {
      if (optimizer instanceof IInputObjectAware) {
        ((IInputObjectAware) optimizer).setInputObject(input);
      }
      if (optimizer instanceof IClassNodeAware) {
        ((IClassNodeAware) optimizer).setClassNode(classNode);
      }
      if (optimizer instanceof IClassNodeAware) {
        ((IClassNodeAware) optimizer).setClassNode(classNode);
      }
    }
    for (final MethodNode methodNode : classNode.methods) {
      if (!"<init>".equals(methodNode.name)) {
        // for (final IOptimizer optimizer : optimizers) {
        // if (optimizer instanceof I) {
        // ((IInputObjectAware) optimizer).setInputObject(input);
        // }
        // }
        runOptimization(methodNode);
      }
      
    }
    
    return OptimizerUtils.createInstance(classNode, input, suffix);
  }
  
  private void runOptimization(final MethodNode methodNode) throws JBOPClassException {
    boolean canOptimize = true;
    InsnList list = methodNode.instructions;
    while (canOptimize) {
      boolean optimized = false;
      for (final IOptimizer optimizer : optimizers) {
        list = optimizer.optimize(list, methodNode);
        optimized |= optimizer.isOptimized();
        methodNode.instructions = list;
      }
      canOptimize = optimized;
    }
  }
  
  // @Override
  // public OptimizerStatistic getStats() {
  // return null;
  // }
}
