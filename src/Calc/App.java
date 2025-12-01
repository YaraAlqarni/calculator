/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;

/**
 *
 * @author  @author Yara , Nour , Ayah
 */

public class App {
    public static void main(String[] args) {
        setupDecoratedOperations();
        
        CalculatorBuilder builder = new BasicCalculatorBuilder();
        CalculatorEngineer engineer = new CalculatorEngineer(builder);
        Calculator calculator = engineer.constructCalculator();
        
        calculator.setVisible(true);
        
        System.out.println("Calculator Started with Theme: " + calculator.getCurrentTheme().getThemeName());
    }
    
    public static void setupDecoratedOperations() {
        OperationFactory.setDecoratedOperation("+", 
            new LoggingDecorator(new ValidationDecorator(new AddOperation())));
        
        OperationFactory.setDecoratedOperation("-", 
            new LoggingDecorator(new ValidationDecorator(new SubtractOperation())));
        
        OperationFactory.setDecoratedOperation("×", 
            new LoggingDecorator(new ValidationDecorator(new MultiplyOperation())));
        
        OperationFactory.setDecoratedOperation("÷", 
            new LoggingDecorator(new ValidationDecorator(new DivideOperation())));
    }
}