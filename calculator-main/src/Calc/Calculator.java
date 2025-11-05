/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;

import javax.swing.*;
import java.awt.*;



public class Calculator extends JFrame {

    private JTextField display;
    
 
    private Operation currentExpressionRoot = null;
    private Operation lastOperation = null;
    private boolean operationSelected = false;

    public Calculator() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
    }

    public void setDisplay(JTextField display) {
        this.display = display;
    }

  
    public void appendNumber(String num) {
        if (operationSelected) {
            display.setText("");
            operationSelected = false;
        }
        display.setText(display.getText().equals("0") ? num : display.getText() + num);
    }

   
    public void chooseOperation(String symbol) {
        if (display.getText().isEmpty()) return;
        double currentValue = Double.parseDouble(display.getText());
        Operation newOperand = new NumericOperand(currentValue); 
        
        if (currentExpressionRoot == null) {  
            Operation newComposite = new OperationEntry(symbol);
            ((OperationEntry)newComposite).setLeftOperand(newOperand);
            currentExpressionRoot = newComposite;
            lastOperation = newComposite;
        } else {
            OperationEntry lastOpEntry = (OperationEntry) lastOperation;
            if (lastOpEntry.getRightOperand() == null)
                lastOpEntry.setRightOperand(newOperand);
            Operation newComposite = new OperationEntry(symbol);
            int newPrecedence = ((OperationEntry)newComposite).getPrecedence();
            int currentPrecedence = lastOpEntry.getPrecedence();
            if (newPrecedence > currentPrecedence) {  
                Operation lastRightOperand = lastOpEntry.getRightOperand();
                lastOpEntry.setRightOperand(newComposite);
                ((OperationEntry)newComposite).setLeftOperand(lastRightOperand);
                lastOperation = newComposite;
            } else {
                double result = currentExpressionRoot.execute(0, 0);
                Operation resultAsOperand = new NumericOperand(result);
                ((OperationEntry)newComposite).setLeftOperand(resultAsOperand);
                currentExpressionRoot = newComposite;
                lastOperation = newComposite;
            }  }
        display.setText(display.getText() + " " + symbol + " ");
        operationSelected = true;}
    public void compute() {
        if (currentExpressionRoot == null || display.getText().isEmpty()) return;
        try {
            String[] tokens = display.getText().trim().split(" ");
            double currentValue = Double.parseDouble(tokens[tokens.length - 1]);
            Operation finalOperand = new NumericOperand(currentValue);

            if (lastOperation != null && ((OperationEntry)lastOperation).getRightOperand() == null)
                ((OperationEntry)lastOperation).setRightOperand(finalOperand);

            double result = currentExpressionRoot.execute(0, 0);
            display.setText(format(result));

            
            currentExpressionRoot = new NumericOperand(result);
            lastOperation = null;

        } catch (ArithmeticException e) {
            display.setText("Error: " + e.getMessage());
            currentExpressionRoot = null;
            lastOperation = null;
        } catch (Exception e) {
            display.setText("Calculation Error");
            currentExpressionRoot = null;
            lastOperation = null;
        }
    }

   
    public void clear() {
        currentExpressionRoot = null;
        lastOperation = null;
        operationSelected = false;
        display.setText("0");
    }

    private String format(double val) {
        return (val == (int) val) ? String.valueOf((int) val) : String.valueOf(val);
    }
}
