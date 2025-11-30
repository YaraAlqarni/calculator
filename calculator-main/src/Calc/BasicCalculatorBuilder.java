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
        display.setFont(new Font("Segoe UI", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        display.setForeground(new Color(33, 37, 41));
        display.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 2),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        
        // Elegant theme control panel
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
     * Creates an elegant theme control panel with modern design
     * UPDATED: No tooltip updates to avoid theme name display
     */
    private JPanel createThemeControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        controlPanel.setBackground(mainPanel.getBackground());
        
        // Modern theme switcher button with icon
        JButton themeBtn = new JButton("🎨");
        themeBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        themeBtn.setToolTipText("Switch Theme"); // Static tooltip only
        themeBtn.setPreferredSize(new Dimension(44, 44));
        themeBtn.setFocusPainted(false);
        themeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Modern button styling
        themeBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        // STRATEGY PATTERN: Button action switches between theme strategies
        // UPDATED: No theme name display in tooltip or display
        themeBtn.addActionListener(e -> {
            calculator.switchTheme();
            // REMOVED: Tooltip update to avoid theme name display
        });
        
        controlPanel.add(themeBtn);
        return controlPanel;
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
        
        // Modern button styling
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