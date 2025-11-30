/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;


import java.awt.*;
/**
 *
 * @author Noor Safia
 */


/**
 * Concrete Strategy - Professional Blue Theme
 * PATTERN ROLE: Concrete Strategy implementation  
 * DESIGN: Corporate-inspired blue theme with teal accents
 * USABILITY: Professional appearance, easy on the eyes for extended use
 */
public class ProfessionalBlueTheme implements ThemeStrategy {
    @Override
    public Color getBackgroundColor() {
        return new Color(240, 247, 255); }
    
    @Override
    public Color getDisplayColor() {
        return new Color(255, 255, 255); }
    
    @Override
    public Color getButtonColor() { 
        return new Color(255, 255, 255); }
    
    @Override
    public Color getTextColor() {
        return new Color(13, 56, 107); }
    
    @Override
    public Color getSpecialButtonColor() {
        return new Color(108, 117, 125); }
    
    @Override
    public Color getOperationButtonColor() {
        return new Color(32, 201, 151); }
    
    @Override
    public String getThemeName() {
        return "Professional"; }
    
    @Override
    public Color getThemeAccentColor() {
        return new Color(32, 201, 151); }
}
