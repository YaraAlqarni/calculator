/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Noor Safia..
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
        
        System.out.println("[LOG] Executing operation: " + operationName);
        System.out.println("[LOG] Operands: a=" + a + ", b=" + b);
        
        double result = super.execute(a, b);
        
        System.out.println("[LOG] Result: " + result);
        
        String logEntry = String.format("%s: %.2f %s %.2f = %.2f", 
                                        operationName, a, operationSymbol, b, result);
        operationHistory.add(logEntry);
        
        return result;
    }

    public static List<String> getOperationHistory() {
        return new ArrayList<>(operationHistory);
    }

    public static void clearHistory() {
        operationHistory.clear();
    }

    private String getOperationName() {
        Operation actualOperation = decoratedOperation;
        while (actualOperation instanceof OperationDecorator) {
            actualOperation = ((OperationDecorator) actualOperation).decoratedOperation;
        }
        return actualOperation.getClass().getSimpleName();
    }

    private String getOperationSymbol() {
        Operation actualOperation = decoratedOperation;
        while (actualOperation instanceof OperationDecorator) {
            actualOperation = ((OperationDecorator) actualOperation).decoratedOperation;
        }
        
        if (actualOperation instanceof AddOperation) return "+";
        if (actualOperation instanceof SubtractOperation) return "-";
        if (actualOperation instanceof MultiplyOperation) return "×";
        if (actualOperation instanceof DivideOperation) return "÷";
        return "?";
    }
}