/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;

/**
 *
 * @author Noor Safia
 */

/**
 * Base decorator class for Operation interface
 * DECORATOR PATTERN: Provides foundation for adding features to operations
 * Enables dynamic enhancement of operations without modifying original classes
 */

public abstract class OperationDecorator implements Operation {
    protected Operation decoratedOperation;
    
    public OperationDecorator(Operation operation) {
        this.decoratedOperation = operation;
    }
    
    @Override
    public double execute(double a, double b) {
        return decoratedOperation.execute(a, b);
    }
}
