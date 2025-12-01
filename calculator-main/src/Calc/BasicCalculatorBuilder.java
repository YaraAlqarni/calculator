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
import javax.swing.border.EmptyBorder;
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
        // Create main panel with modern padding
        mainPanel = new JPanel(new BorderLayout(8, 8));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(248, 249, 250));

        // Modern display with better styling
        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("Segoe UI", Font.BOLD, 15));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        display.setForeground(new Color(33, 37, 41));
        display.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 2),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        
        // UPDATED: Control panel now includes undo/redo buttons
        JPanel controlPanel = createThemeControlPanel();
        
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setBackground(mainPanel.getBackground());
        topPanel.add(display, BorderLayout.CENTER);
        topPanel.add(controlPanel, BorderLayout.EAST);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Modern calculator buttons
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Finalize calculator setup
        calculator.setDisplay(display);
        calculator.setMainPanel(mainPanel);
        calculator.add(mainPanel);
        calculator.pack();
        calculator.setSize(380, 520);
        calculator.setLocationRelativeTo(null);
        calculator.setResizable(false);
        
        // Apply initial theme
        calculator.applyTheme();
    }
    
    /**
     * UPDATED: Added Undo and Redo buttons for Command Pattern
     * Creates an elegant control panel with theme switcher, undo, and redo
     */
    private JPanel createThemeControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        controlPanel.setBackground(mainPanel.getBackground());
        
        // COMMAND PATTERN: Undo button
        JButton undoBtn = createControlButton("↶", "Undo");
        undoBtn.addActionListener(e -> calculator.undo());
        controlPanel.add(undoBtn);
        
        // COMMAND PATTERN: Redo button
        JButton redoBtn = createControlButton("↷", "Redo");
        redoBtn.addActionListener(e -> calculator.redo());
        controlPanel.add(redoBtn);
        
        // Theme switcher button
        JButton themeBtn = createControlButton("🎨", "Switch Theme");
        themeBtn.addActionListener(e -> calculator.switchTheme());
        controlPanel.add(themeBtn);
        
        return controlPanel;
    }
    
    /**
     * Helper method to create consistent control buttons
     */
    private JButton createControlButton(String text, String tooltip) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        btn.setToolTipText(tooltip);
        btn.setPreferredSize(new Dimension(44, 44));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        return btn;
    }
    
    /**
     * Creates modern calculator button panel
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 8, 8));
        buttonPanel.setBackground(mainPanel.getBackground());
        
        String[] buttons = {
            "7", "8", "9", "÷",
            "4", "5", "6", "×", 
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton btn = createModernButton(text);
            buttonPanel.add(btn);
        }

        return buttonPanel;
    }
    
    private JButton createModernButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            BorderFactory.createEmptyBorder(12, 0, 12, 0)
        ));
        
        // Button actions
        if (text.matches("[0-9]")) {
            btn.addActionListener(e -> calculator.appendNumber(text));
        } else if (text.equals("C")) {
            btn.addActionListener(e -> calculator.clear());
        } else if (text.equals("=")) {
            btn.addActionListener(e -> calculator.compute());
        } else {
            btn.addActionListener(e -> calculator.chooseOperation(text));
        }

        return btn;
    }

    @Override
    public Calculator getCalculator() {
        return calculator;
    }
}