/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;

import java.util.ArrayList;
import java.util.List;

public class OperationEntry implements Operation {
    private final Operation operation; 
    private final String operatorSymbol;
    
    private Operation leftOperand;
    private Operation rightOperand;

    public OperationEntry(String symbol) {
       
        this.operation = OperationFactory.createOperation(symbol);
        this.operatorSymbol = symbol;
        if (this.operation == null) {
            throw new IllegalArgumentException("Unknown operation: " + symbol);
        }
    }

 
    public void setLeftOperand(Operation leftOperand) {
        this.leftOperand = leftOperand;
    }

    public void setRightOperand(Operation rightOperand) {
        this.rightOperand = rightOperand;
    }


    public int getPrecedence() {
        return (operatorSymbol.equals("×") || operatorSymbol.equals("÷")) ? 2 : 1;
    }

   
    @Override
    public double execute(double a, double b) {
        if (leftOperand == null || rightOperand == null) {
            throw new ArithmeticException("Incomplete operation expression.");
        }
        
       
        double valA = leftOperand.execute(0, 0); 
        
     
        double valB = rightOperand.execute(0, 0); 

   
        return operation.execute(valA, valB);
    }
    
   
    public Operation getLeftOperand() { return leftOperand; }
    public Operation getRightOperand() { return rightOperand; }
    public String getOperatorSymbol() { return operatorSymbol; }
}