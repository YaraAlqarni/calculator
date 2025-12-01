/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;

/**
 *
 * @author yaraworldclass
 */
/**
 * RESPONSIBILITY: Encapsulates all information needed to execute an operation
 * RECEIVER: The actual Operation object (AddOperation, SubtractOperation, etc.)
 * INTEGRATION: Works with Decorator pattern (ValidationDecorator, LoggingDecorator)
 */
/**
 * Concrete Command — wraps ANY Operation (receiver)
 * Example: AddOperation, SubtractOperation...
 */
public class ArithmeticCommand implements Command {

    private Operation operation; // reciever
    private double operation1;
    private double operation2;

    private double previousResult;

    public ArithmeticCommand(Operation operation, double operation1, double operation2) {
        this.operation = operation;
        this.operation1 = operation1;
        this.operation2 = operation2;
    }

    @Override
    public double execute() {
        previousResult = operation.execute(operation1, operation2);
        return previousResult;
    }

    @Override
    public void undo() {
        System.out.println("Undoing: " + operation.getClass().getSimpleName());
        // You may restore display value here.
    }
}
