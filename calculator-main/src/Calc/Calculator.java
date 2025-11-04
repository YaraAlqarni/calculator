/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Yara , Nour , Ayah javax.swing.JFrame
 */

/**
 * Calculator - Enhanced with error handling for decorated operations
 * CHANGES: Added exception handling for ValidationDecorator exceptions
 */

public class Calculator extends JFrame {
   private JTextField display;
    private Operation currentExpressionRoot = null;
    private OperationEntry lastOperationEntry = null;
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
        NumericOperand newOperand = new NumericOperand(currentValue);

        if (currentExpressionRoot == null) {
            OperationEntry newComposite = new OperationEntry(symbol);
            newComposite.setLeftOperand(newOperand);
            currentExpressionRoot = newComposite;
            lastOperationEntry = newComposite;
        } else {
            if (lastOperationEntry.getRightOperand() == null)
                lastOperationEntry.setRightOperand(newOperand);

            OperationEntry newComposite = new OperationEntry(symbol);
            int newPrecedence = newComposite.getPrecedence();
            int currentPrecedence = lastOperationEntry.getPrecedence();

            if (newPrecedence > currentPrecedence) {
                Operation lastRightOperand = lastOperationEntry.getRightOperand();
                lastOperationEntry.setRightOperand(newComposite);
                newComposite.setLeftOperand(lastRightOperand);
                lastOperationEntry = newComposite;
            } else {
                double result = currentExpressionRoot.execute(0, 0);
                NumericOperand resultLeaf = new NumericOperand(result);
                newComposite.setLeftOperand(resultLeaf);
                currentExpressionRoot = newComposite;
                lastOperationEntry = newComposite;
            }
        }

        display.setText(display.getText() + " " + symbol + " ");
        operationSelected = true;
    }

    public void compute() {
        if (currentExpressionRoot == null || display.getText().isEmpty()) return;

        try {
            double currentValue = Double.parseDouble(display.getText().trim().split(" ")[display.getText().trim().split(" ").length - 1]);
            NumericOperand finalOperand = new NumericOperand(currentValue);

            if (lastOperationEntry != null && lastOperationEntry.getRightOperand() == null)
                lastOperationEntry.setRightOperand(finalOperand);

            double result = currentExpressionRoot.execute(0, 0);
            display.setText(format(result));
            currentExpressionRoot = new NumericOperand(result);
            lastOperationEntry = null;

        } catch (ArithmeticException e) {
            display.setText("Error: " + e.getMessage());
            currentExpressionRoot = null;
            lastOperationEntry = null;
        } catch (Exception e) {
            display.setText("Calculation Error");
            currentExpressionRoot = null;
            lastOperationEntry = null;
        }
    }

    public void clear() {
        currentExpressionRoot = null;
        lastOperationEntry = null;
        operationSelected = false;
        display.setText("0");
    }

    private String format(double val) {
        return (val == (int) val) ? String.valueOf((int) val) : String.valueOf(val);}}

