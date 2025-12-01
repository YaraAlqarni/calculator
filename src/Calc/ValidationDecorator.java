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
 * ValidationDecorator - Adds input validation to operations
 * SOLVED PROBLEMS: Prevents division by zero, invalid inputs, arithmetic exceptions
 * ENHANCES: All operations get automatic input validation
 */
public class ValidationDecorator extends OperationDecorator {
    
    public ValidationDecorator(Operation operation) {
        super(operation);
    }
    
    @Override
    public double execute(double a, double b) {
        validateInputs(a, b);
        return super.execute(a, b);
    }
    
    private void validateInputs(double a, double b) {
        if (decoratedOperation instanceof DivideOperation && b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        
        if (Double.isInfinite(a) || Double.isInfinite(b)) {
            throw new ArithmeticException("Invalid number (infinite)");
        }
        
        if (Double.isNaN(a) || Double.isNaN(b)) {
            throw new ArithmeticException("Invalid number (NaN)");
        }
    }
}