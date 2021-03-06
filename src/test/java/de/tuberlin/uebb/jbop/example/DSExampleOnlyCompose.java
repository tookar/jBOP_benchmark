/*
 * Copyright (C) 2013 uebb.tu-berlin.de.
 * 
 * This file is part of JBOP (Java Bytecode OPtimizer).
 * 
 * JBOP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JBOP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with JBOP. If not, see <http://www.gnu.org/licenses/>.
 */
package de.tuberlin.uebb.jbop.example;

// import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.junit.Test;

/**
 * The Class DSExample.
 * 
 * @author Christopher Ewest
 */
public class DSExampleOnlyCompose {
  
  /**
   * The main method.
   * Example for Usage of jBOP.
   * 
   * @param args
   *          the arguments
   */
  public static void main(final String[] args) {
    new DSExampleOnlyCompose().run();
  }
  
  private final double x0 = 15.0;
  private final double y0 = 15.0;
  private final double z0 = 15.0;
  private final double a = 2.0;
  private final double b = 3.5;
  private final double c = 4.0;
  
  @SuppressWarnings("unused")
  public void createCompilers() {
    new DerivativeStructureOnlyCompose(3, 3, 0, 0);
  }
  
  private DerivativeStructureOnlyCompose x;
  private DerivativeStructureOnlyCompose y;
  private DerivativeStructureOnlyCompose z;
  
  @Test
  public DerivativeStructureOnlyCompose run() {
    x = new DerivativeStructureOnlyCompose(3, 3, 0, x0);
    y = new DerivativeStructureOnlyCompose(3, 3, 1, y0);
    z = new DerivativeStructureOnlyCompose(3, 3, 2, z0);
    // ax²
    final DerivativeStructureOnlyCompose ax2 = x.pow(2).multiply(a);//
    // ay²
    final DerivativeStructureOnlyCompose ay2 = y.pow(2).multiply(a);//
    // az²
    final DerivativeStructureOnlyCompose az2 = z.pow(2).multiply(a);//
    // + bx
    final DerivativeStructureOnlyCompose bx = x.multiply(b);
    // + by
    final DerivativeStructureOnlyCompose by = y.multiply(b);
    // + bz
    final DerivativeStructureOnlyCompose bz = z.multiply(b);
    // +c
    
    final DerivativeStructureOnlyCompose multiply = ax2.multiply(ay2).multiply(az2);
    final DerivativeStructureOnlyCompose multiply2 = bx.multiply(by).multiply(bz);
    
    return multiply.add(multiply2).add(c);
  }
}
