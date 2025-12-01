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
 * Concrete Strategy - Dark Theme  
 * PATTERN ROLE: Concrete Strategy implementation
 * DESIGN: Sophisticated dark theme with purple accents
 * USABILITY: Reduces eye strain, ideal for low-light environments
 */

public class DarkTheme implements ThemeStrategy {
    @Override
    public Color getBackgroundColor() {
        return new Color(33, 37, 41); }
    
    @Override
    public Color getDisplayColor() {
        return new Color(52, 58, 64); }
    
    @Override
    public Color getButtonColor() {
        return new Color(73, 80, 87); }
    
    @Override
    public Color getTextColor() {
        return new Color(248, 249, 250); }
    
    @Override
    public Color getSpecialButtonColor() {
        return new Color(134, 142, 150); }
    
    @Override
    public Color getOperationButtonColor() {
        return new Color(147, 51, 234); }
    
    @Override
    public String getThemeName() {
        return "Dark"; }
    
    @Override
    public Color getThemeAccentColor() {
        return new Color(147, 51, 234); }
}