/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Calc;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Yara , Nour , Ayah
 */
public class Calculator extends JFrame {
    private JTextField display;
    private Operation currentExpressionRoot = null;
    private OperationEntry lastOperationEntry = null;
    private boolean operationSelected = false;
    
    // STRATEGY PATTERN IMPLEMENTATION: Theme management
    private ThemeStrategy currentTheme;
    private List<ThemeStrategy> availableThemes;
    private int currentThemeIndex = 0;
    
    private Point initialClick;
    private JPanel mainPanel;

    public Calculator() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        // STRATEGY PATTERN: Initialize available theme strategies
        initializeThemes();
        
        // Set default theme strategy
        this.currentTheme = availableThemes.get(currentThemeIndex);

        // Window dragging implementation
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                setLocation(thisX + xMoved, thisY + yMoved);
            }
        });
    }
    
    /**
     * STRATEGY PATTERN: Initialize concrete strategy implementations
     */
    private void initializeThemes() {
        availableThemes = new ArrayList<>();
        availableThemes.add(new LightTheme());           // Concrete Strategy 1
        availableThemes.add(new DarkTheme());            // Concrete Strategy 2  
        availableThemes.add(new ProfessionalBlueTheme());// Concrete Strategy 3
    }

    public void setDisplay(JTextField display) {
        this.display = display;
    }

    public void setMainPanel(JPanel panel) {
        this.mainPanel = panel;
    }

    // THEME STRATEGY PATTERN METHODS
    
    /**
     * STRATEGY PATTERN: Set current theme strategy
     */
    public void setTheme(ThemeStrategy theme) {
        this.currentTheme = theme;
        applyTheme();
    }
    
    /**
     * STRATEGY PATTERN: Cycle through available theme strategies
     * UPDATED: No theme name display in the main display area
     */
    public void switchTheme() {
        currentThemeIndex = (currentThemeIndex + 1) % availableThemes.size();
        ThemeStrategy newTheme = availableThemes.get(currentThemeIndex);
        setTheme(newTheme);
        
        // REMOVED: Theme name display in main display
        // Only apply the theme visually without interrupting user input
    }
    
    /**
     * STRATEGY PATTERN: Apply current theme strategy to all UI components
     */
    public void applyTheme() {
        if (mainPanel != null) {
            mainPanel.setBackground(currentTheme.getBackgroundColor());
            if (display != null) {
                display.setBackground(currentTheme.getDisplayColor());
                display.setForeground(currentTheme.getTextColor());
                display.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(currentTheme.getThemeAccentColor(), 2),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
            updateAllButtonsTheme();
        }
    }

    /**
     * STRATEGY PATTERN: Update all buttons using current theme strategy
     */
    private void updateAllButtonsTheme() {
        if (mainPanel == null) return;
        
        Component[] components = mainPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                Component[] buttons = ((JPanel) comp).getComponents();
                for (Component btn : buttons) {
                    if (btn instanceof JButton) {
                        updateButtonTheme((JButton) btn);
                    }
                }
            }
        }
    }

    /**
     * STRATEGY PATTERN: Apply theme to individual button based on its role
     */
    private void updateButtonTheme(JButton button) {
        String text = button.getText();
        Color buttonColor = currentTheme.getButtonColor();
        Color textColor = currentTheme.getTextColor();
        
        // Apply different colors based on button function using current strategy
        if (text.equals("C")) {
            buttonColor = currentTheme.getSpecialButtonColor();
        } else if (text.equals("=")) {
            buttonColor = new Color(40, 167, 69); // Success green
            textColor = Color.WHITE;
        } else if (text.matches("[+\\-×÷]")) {
            buttonColor = currentTheme.getOperationButtonColor();
            textColor = Color.WHITE;
        } else if (text.equals("🎨")) {
            buttonColor = currentTheme.getThemeAccentColor();
            textColor = Color.WHITE;
        }
        
        button.setBackground(buttonColor);
        button.setForeground(textColor);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 30), 1),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));
    }

    // STANDARD CALCULATOR FUNCTIONS (بدون تغيير)
    public void appendNumber(String num) {
        if (display == null) return;
        
        if (operationSelected) {
            display.setText("");
            operationSelected = false;
        }
        display.setText(display.getText().equals("0") ? num : display.getText() + num);
    }

    public void chooseOperation(String symbol) {
        if (display == null || display.getText().isEmpty() || display.getText().equals("0")) return;
        
        try {
            String displayText = display.getText();
            double currentValue;
            if (displayText.contains(" ")) {
                String[] parts = displayText.split(" ");
                currentValue = Double.parseDouble(parts[parts.length - 1]);
            } else {
                currentValue = Double.parseDouble(displayText);
            }
            
            NumericOperand newOperand = new NumericOperand(currentValue);

            if (currentExpressionRoot == null) {
                OperationEntry newOp = new OperationEntry(symbol);
                newOp.setLeftOperand(newOperand);
                currentExpressionRoot = newOp;
                lastOperationEntry = newOp;
            } else {
                if (lastOperationEntry != null && lastOperationEntry.getRightOperand() == null) {
                    lastOperationEntry.setRightOperand(newOperand);
                }
                
                OperationEntry newOp = new OperationEntry(symbol);
                handleOperationPrecedence(newOp);
            }

            display.setText(display.getText() + " " + symbol + " ");
            operationSelected = true;
            
        } catch (NumberFormatException e) {
            display.setText("Invalid Number");
            clear();
        } catch (Exception e) {
            display.setText("Error: " + e.getMessage());
            clear();
        }
    }

    private void handleOperationPrecedence(OperationEntry newOp) {
        if (lastOperationEntry == null) {
            try {
                String displayText = display.getText();
                double currentValue;
                if (displayText.contains(" ")) {
                    String[] parts = displayText.split(" ");
                    currentValue = Double.parseDouble(parts[parts.length - 1]);
                } else {
                    currentValue = Double.parseDouble(displayText);
                }
                NumericOperand resultOp = new NumericOperand(currentValue);
                newOp.setLeftOperand(resultOp);
                currentExpressionRoot = newOp;
                lastOperationEntry = newOp;
            } catch (NumberFormatException e) {
                display.setText("Invalid Number");
                clear();
            }
            return;
        }
        
        int newPrecedence = newOp.getPrecedence();
        int currentPrecedence = lastOperationEntry.getPrecedence();

        if (newPrecedence > currentPrecedence) {
            Operation lastRight = lastOperationEntry.getRightOperand();
            if (lastRight != null) {
                lastOperationEntry.setRightOperand(newOp);
                newOp.setLeftOperand(lastRight);
                lastOperationEntry = newOp;
            }
        } else {
            try {
                double result = currentExpressionRoot.execute(0, 0);
                NumericOperand resultOp = new NumericOperand(result);
                newOp.setLeftOperand(resultOp);
                currentExpressionRoot = newOp;
                lastOperationEntry = newOp;
            } catch (Exception e) {
                display.setText("Calculation Error");
                clear();
            }
        }
    }

    public void compute() {
        if (currentExpressionRoot == null || display.getText().isEmpty()) {
            display.setText("No operation to compute");
            return;
        }

        try {
            String[] parts = display.getText().trim().split(" ");
            double currentValue = Double.parseDouble(parts[parts.length - 1]);
            NumericOperand finalOperand = new NumericOperand(currentValue);

            if (lastOperationEntry != null && lastOperationEntry.getRightOperand() == null) {
                lastOperationEntry.setRightOperand(finalOperand);
            }

            double result = currentExpressionRoot.execute(0, 0);
            display.setText(format(result));
            currentExpressionRoot = new NumericOperand(result);
            lastOperationEntry = null;

        } catch (ArithmeticException e) {
            display.setText("Math Error: " + e.getMessage());
            currentExpressionRoot = null;
            lastOperationEntry = null;
        } catch (NumberFormatException e) {
            display.setText("Invalid Number Format");
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
        if (display != null) {
            display.setText("0");
        }
    }

    private String format(double val) {
        return (val == (int) val) ? String.valueOf((int) val) : String.valueOf(val);
    }
    
    // STRATEGY PATTERN: Access to current strategy and available strategies
    public ThemeStrategy getCurrentTheme() {
        return currentTheme;
    }
    
    public List<ThemeStrategy> getAvailableThemes() {
        return new ArrayList<>(availableThemes);
    }
    
    public String getCurrentThemeName() {
        return currentTheme.getThemeName();
    }
}