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

// Strategy Interface for Themes
 
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