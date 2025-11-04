/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calc;

public class NumericOperand implements Operation {
    private final double value;

    public NumericOperand(double value) {
        this.value = value;
    }

  
    @Override
    public double execute(double a, double b) {
        return this.value;
    }
    

    public double getValue() {
        return this.value;
    }
}