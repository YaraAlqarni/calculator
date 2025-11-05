/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        // Create main panel with padding
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 245, 245)); // light background

        // Larger display
        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("SansSerif", Font.BOLD, 36));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        mainPanel.add(display, BorderLayout.NORTH);

        // Larger grid (buttons)
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        buttonPanel.setBackground(new Color(230, 230, 230));
        String[] buttons = {
            "7", "8", "9", "÷",
            "4", "5", "6", "×",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("SansSerif", Font.BOLD, 28));
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
            btn.setPreferredSize(new Dimension(90, 90)); // bigger button

            // Button actions
            if (text.matches("[0-9]")) {
                btn.addActionListener(e -> calculator.appendNumber(text));
            } else if (text.equals("C")) {
                btn.setBackground(new Color(255, 180, 180));
                btn.addActionListener(e -> calculator.clear());
            } else if (text.equals("=")) {
                btn.setBackground(new Color(180, 255, 180));
                btn.addActionListener(e -> calculator.compute());
            } else {
                btn.setBackground(new Color(220, 220, 255));
                btn.addActionListener(e -> calculator.chooseOperation(text));
            }

            buttonPanel.add(btn);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add to calculator window
        calculator.setDisplay(display);
        calculator.add(mainPanel);
        calculator.pack();
        calculator.setSize(500, 600); 
        calculator.setLocationRelativeTo(null);
        calculator.setResizable(false);
    }

    @Override
    public Calculator getCalculator() {
        return calculator;
    }
}
