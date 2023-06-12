package com.rytuo;

import com.rytuo.calculator.Calculator;

public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        String result = calculator.process("3 + 4 * 2 / (1 - 5)");
        System.out.println(result);
    }
}
