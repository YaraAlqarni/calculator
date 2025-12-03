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
import java.util.Stack;

/**
 * 
 * @author Yara, Nour, Ayah
 */
public class Calculator extends JFrame {
    private JTextField display;
    private Operation currentExpressionRoot = null;
    private OperationEntry lastOperationEntry = null;
    private boolean operationSelected = false;
    
    // COMMAND PATTERN: Command history management
    private Stack<Command> commandHistory = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();
    
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

        initializeThemes();
        this.currentTheme = availableThemes.get(currentThemeIndex);

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
  
    public double executeCommand(Command command) {
        double result = command.execute();
        commandHistory.push(command);
        redoStack.clear(); 
        return result;
    }
    
   
    public void undo() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
            redoStack.push(lastCommand);
            
            
            if (!commandHistory.isEmpty()) {
                display.setText("Undo performed");
            } else {
                display.setText("0");
            }
            
            
            currentExpressionRoot = null;
            lastOperationEntry = null;
            operationSelected = false;
        } else {
            display.setText("Nothing to undo");
        }
    }
    
   
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            double result = command.execute();
            commandHistory.push(command);
            display.setText(format(result));
        } else {
            display.setText("Nothing to redo");
        }
    }
    
  
    public Stack<Command> getCommandHistory() {
        return commandHistory;
    }
    
    private void initializeThemes() {
        availableThemes = new ArrayList<>();
        availableThemes.add(new LightTheme());
        availableThemes.add(new DarkTheme());
        availableThemes.add(new ProfessionalBlueTheme());
    }

    public void setDisplay(JTextField display) {
        this.display = display;
    }

    public void setMainPanel(JPanel panel) {
        this.mainPanel = panel;
    }

    public void setTheme(ThemeStrategy theme) {
        this.currentTheme = theme;
        applyTheme();
    }
    
    public void switchTheme() {
        currentThemeIndex = (currentThemeIndex + 1) % availableThemes.size();
        ThemeStrategy newTheme = availableThemes.get(currentThemeIndex);
        setTheme(newTheme);
    }
    
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

    private void updateButtonTheme(JButton button) {
        String text = button.getText();
        Color buttonColor = currentTheme.getButtonColor();
        Color textColor = currentTheme.getTextColor();
        
        if (text.equals("C")) {
            buttonColor = currentTheme.getSpecialButtonColor();
        } else if (text.equals("=")) {
            buttonColor = new Color(40, 167, 69);
            textColor = Color.WHITE;
        } else if (text.matches("[+\\-×÷]")) {
            buttonColor = currentTheme.getOperationButtonColor();
            textColor = Color.WHITE;
        } else if (text.equals("🎨") || text.equals("↶") || text.equals("↷")) {
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

            // COMMAND PATTERN: Create CompositeCommand for the entire expression
            CompositeCommand compositeCmd = new CompositeCommand(currentExpressionRoot, display.getText());
            
            // Execute through command pattern
            double result = executeCommand(compositeCmd);
            
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