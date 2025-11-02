/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;

/**
 *
 * @author Yara , Nour , Ayah
 */

public class CalculatorEngineer {
    private CalculatorBuilder builder;

    public CalculatorEngineer(CalculatorBuilder builder) {
        this.builder = builder;
    }

    public Calculator constructCalculator() {
        builder.buildUI();
        return builder.getCalculator();
    }
}
