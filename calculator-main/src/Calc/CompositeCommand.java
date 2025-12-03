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
 */
public class CompositeCommand implements Command {
    
    private Operation expressionRoot;  
    private String expressionString;    
    private double result;              
    private double previousDisplayValue; 

    public CompositeCommand(Operation expressionRoot, String expressionString) {
        this.expressionRoot = expressionRoot;
        this.expressionString = expressionString;
        this.previousDisplayValue = 0; 
    }

    @Override
    public double execute() {
        try {
            result = expressionRoot.execute(0, 0);
            
            System.out.println("[CompositeCommand] Executed: " + expressionString + " = " + result);
            
            return result;
            
        } catch (Exception e) {
            System.err.println("[CompositeCommand] Execution failed: " + e.getMessage());
            throw e;
        }
    }
    @Override
    public void undo() {
        System.out.println("[CompositeCommand] Undoing: " + expressionString);
    }
   
    public double getResult() {
        return result;
    }
    
    public String getExpressionString() {
        return expressionString;
    }
    
    public void setPreviousDisplayValue(double value) {
        this.previousDisplayValue = value;
    }
    
    public double getPreviousDisplayValue() {
        return previousDisplayValue;
    }
}