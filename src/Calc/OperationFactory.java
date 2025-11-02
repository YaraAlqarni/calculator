package Calc;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author yaraworldclass
 */
public class OperationFactory {
    public static Operation createOperation(String opSymbol) {
        return switch (opSymbol) {
            case "+" -> new AddOperation();
            case "-" -> new SubtractOperation();
            case "×" -> new MultiplyOperation();
            case "÷" -> new DivideOperation();
            default -> null;
        };
    }
}
