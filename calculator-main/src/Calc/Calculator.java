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
    private double previousOperand = 0;
    private String currentOperation = "";
    private boolean operationSelected = false;
 private CalculatorFacade facade = new CalculatorFacade();
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
        if (!display.getText().isEmpty()) {
            previousOperand = Double.parseDouble(display.getText());
            currentOperation = symbol;
            operationSelected = true;
        }
    }

    public void compute() {
       if (currentOperation.isEmpty() || display.getText().isEmpty()) return;

        try {
            double current = Double.parseDouble(display.getText());
            Operation operation = OperationFactory.createOperation(currentOperation);

            if (operation != null) {
               double result = facade.calculate(currentOperation, previousOperand, current);
                display.setText(format(result));
                currentOperation = "";
            }
        } catch (ArithmeticException e) {
            display.setText("Error: " + e.getMessage());
            currentOperation = "";
        } catch (Exception e) {
            display.setText("Calculation Error");
            currentOperation = "";
    }
    }     

    public void clear() {
        previousOperand = 0;
        currentOperation = "";
        display.setText("0");
    }

    private String format(double val) {
        return (val == (int) val) ? String.valueOf((int) val) : String.valueOf(val);
    }
    
    
    }

