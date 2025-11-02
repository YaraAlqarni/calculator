/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;

public class CalculatorFacade {

    public double calculate(String operationSymbol, double a, double b) {
        
        Operation operation = OperationFactory.createOperation(operationSymbol);

        if (operation == null)
            throw new IllegalArgumentException("Unknown operation: " + operationSymbol);

        
        operation = new ValidationDecorator(operation);
        operation = new LoggingDecorator(operation);

       
        return operation.execute(a, b);
    }
}