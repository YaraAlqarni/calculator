/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author yaraworldclass
 *
 * UPDATED: CompositeCommand now handles entire expression trees
 * RESPONSIBILITY: Encapsulates execution of complex multi-operation expressions
 * INTEGRATION: Works with OperationEntry composite structure
 * 
 * PURPOSE:
 * - Executes complete expression trees (e.g., "3 + 5 × 2")
 * - Stores the expression for undo functionality
 * - Maintains the result for undo operations
 * 
 * 
 */
public class CompositeCommand implements Command {
    
    private Operation expressionRoot;  // The root of the expression tree
    private String expressionString;    // String representation for display
    private double result;              // Stores the computed result
    private double previousDisplayValue; // For undo functionality

    /**
     * Constructor for handling complete expression trees
     * @param expressionRoot The root Operation of the expression tree
     * @param expressionString The string representation of the expression
     */
    public CompositeCommand(Operation expressionRoot, String expressionString) {
        this.expressionRoot = expressionRoot;
        this.expressionString = expressionString;
        this.previousDisplayValue = 0; // Can be set if needed
    }

    /**
     * COMMAND PATTERN: Execute the entire expression tree
     * This traverses and computes the composite structure
     */
    @Override
    public double execute() {
        try {
            // Execute the entire expression tree
            // The expressionRoot could be:
            // - A simple NumericOperand
            // - An OperationEntry with left and right operands
            // - A complex tree of nested OperationEntries
            result = expressionRoot.execute(0, 0);
            
            System.out.println("[CompositeCommand] Executed: " + expressionString + " = " + result);
            
            return result;
            
        } catch (Exception e) {
            System.err.println("[CompositeCommand] Execution failed: " + e.getMessage());
            throw e;
        }
    }

    /**
     * COMMAND PATTERN: Undo the expression execution
     * Restores the calculator to its previous state
     */
    @Override
    public void undo() {
        System.out.println("[CompositeCommand] Undoing: " + expressionString);
        // The Calculator's undo() method handles display restoration
        // This method can be extended to restore specific calculator state
    }
    
    /**
     * Get the result of this command (useful for undo/redo)
     */
    public double getResult() {
        return result;
    }
    
    /**
     * Get the expression string
     */
    public String getExpressionString() {
        return expressionString;
    }
    
    /**
     * Set previous display value for undo
     */
    public void setPreviousDisplayValue(double value) {
        this.previousDisplayValue = value;
    }
    
    /**
     * Get previous display value
     */
    public double getPreviousDisplayValue() {
        return previousDisplayValue;
    }
}