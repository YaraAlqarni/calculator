/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;

/**
 *
 * @author  @author Yara , Nour , Ayah
 */


public class App {
    public static void main(String[] args) {
        CalculatorBuilder builder = new BasicCalculatorBuilder();
        CalculatorEngineer engineer = new CalculatorEngineer(builder);
        Calculator calculator = engineer.constructCalculator();
        calculator.setVisible(true);
    }
}
