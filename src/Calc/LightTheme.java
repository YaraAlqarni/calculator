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
 * Concrete Strategy - Light Theme
 * PATTERN ROLE: Concrete Strategy implementation
 * DESIGN: Clean, modern light theme with blue accents
 * USABILITY: Best for daytime use and well-lit environments
 */
public class LightTheme implements ThemeStrategy {
    @Override
    public Color getBackgroundColor() {
        return new Color(248, 249, 250); }
    
    @Override
    public Color getDisplayColor() {
        return new Color(255, 255, 255); }
    
    @Override
    public Color getButtonColor() {
        return new Color(255, 255, 255); }
    
    @Override
    public Color getTextColor() {
        return new Color(33, 37, 41); }
    
    @Override
    public Color getSpecialButtonColor() {
        return new Color(108, 117, 125); }
    
    @Override
    public Color getOperationButtonColor() {
        return new Color(0, 123, 255); }
    
    @Override
    public String getThemeName() {
        return "Light"; }
    
    @Override
    public Color getThemeAccentColor() {
        return new Color(0, 123, 255); }
}