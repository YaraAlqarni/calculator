/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;

/**
 *
 * @author Noor Safia
 */
import java.awt.*;

/**
 * Strategy Interface for Themes
 * STRUCTURE: This is the Strategy interface in the Strategy Pattern
 * IMPLEMENTATION: Each concrete theme implements this interface to provide different color schemes
 * BENEFIT: Easy to add new themes without modifying existing code
 * SOLVES: Dynamic theme switching without modifying calculator UI code
 * INTEGRATION: Works seamlessly with existing Calculator GUI components
 */
public interface ThemeStrategy {
    Color getBackgroundColor();
    Color getDisplayColor();
    Color getButtonColor();
    Color getTextColor();
    Color getSpecialButtonColor();
    Color getOperationButtonColor();
    String getThemeName();
    Color getThemeAccentColor();
}