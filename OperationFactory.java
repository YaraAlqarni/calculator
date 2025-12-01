package Calc;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author yaraworldclass
 */
import java.util.HashMap;
import java.util.Map;

/**
 * OperationFactory - Enhanced to support decorated operations
 * INTEGRATION: Factory Method + Decorator Pattern work together seamlessly
 */
public class OperationFactory {
    private static Map<String, Operation> decoratedOperations = new HashMap<>();
    
    public static Operation createOperation(String opSymbol) {
        if (decoratedOperations.containsKey(opSymbol)) {
            return decoratedOperations.get(opSymbol);
        }
        
        return switch (opSymbol) {
            case "+" -> new AddOperation();
            case "-" -> new SubtractOperation();
            case "×" -> new MultiplyOperation();
            case "÷" -> new DivideOperation();
            default -> null;
        };
    }
    
    public static void setDecoratedOperation(String opSymbol, Operation decoratedOp) {
        decoratedOperations.put(opSymbol, decoratedOp);
    }
    
    public static void clearDecoratedOperations() {
        decoratedOperations.clear();
    }
}
