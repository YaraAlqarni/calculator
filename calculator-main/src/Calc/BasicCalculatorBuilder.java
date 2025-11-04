/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;

import java.awt.*;
import javax.swing.*;

/**
 * 
 * @author Yara , Nour , Ayah
 */


public class BasicCalculatorBuilder implements CalculatorBuilder {
    private Calculator calculator;
    private JPanel mainPanel;
    private JTextField display;

    public BasicCalculatorBuilder() {
        calculator = new Calculator();
    }

    @Override
    public void buildUI() {
        mainPanel = new JPanel(new BorderLayout());
        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("SansSerif", Font.BOLD, 28));
        mainPanel.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        String[] buttons = {"7", "8", "9", "÷", "4", "5", "6", "×", "1", "2", "3", "-", "0", "C", "=", "+"};

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("SansSerif", Font.BOLD, 20));
            buttonPanel.add(btn);

       if (text.matches("[0-9]")) {
                btn.addActionListener(e -> calculator.appendNumber(text));
            } else if (text.equals("C")) {
                btn.addActionListener(e -> calculator.clear());
            } else if (text.equals("=")) {
                btn.addActionListener(e -> calculator.compute());
            } else {
                btn.addActionListener(e -> calculator.chooseOperation(text));
            }
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        calculator.setDisplay(display);
        calculator.add(mainPanel);
        calculator.pack();
        calculator.setLocationRelativeTo(null);
    }

    @Override
    public Calculator getCalculator() {
        return calculator;
    }
}
