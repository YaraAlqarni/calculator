/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Noor Safia
 */

/**
 * LoggingDecorator - Adds operation logging functionality
 * SOLVED PROBLEMS: Provides operation history, debugging, audit trail
 * ENHANCES: All operations get automatic logging without changing their code
 */

public class LoggingDecorator extends OperationDecorator {
    private static List<String> operationHistory = new ArrayList<>();
    
    public LoggingDecorator(Operation operation) {
        super(operation);
    }
    
    @Override
    public double execute(double a, double b) {
        String operationName = getOperationName();
        String operationSymbol = getOperationSymbol();
        String logEntry = String.format("%s: %.2f %s %.2f", operationName, a, operationSymbol, b);
        
        double result = super.execute(a, b);
        
        logEntry += String.format(" = %.2f", result);
        operationHistory.add(logEntry);
        
        System.out.println("OPERATION LOG: " + logEntry);
        
        return result;
    }
    
    public static List<String> getOperationHistory() {
        return new ArrayList<>(operationHistory);
    }
    
    public static void clearHistory() {
        operationHistory.clear();
    }
    
    private String getOperationName() {
        return decoratedOperation.getClass().getSimpleName();
    }
    
    private String getOperationSymbol() {
        if (decoratedOperation instanceof AddOperation) return "+";
        if (decoratedOperation instanceof SubtractOperation) return "-";
        if (decoratedOperation instanceof MultiplyOperation) return "×";
        if (decoratedOperation instanceof DivideOperation) return "÷";
        return "?";
    }
}
