package com.rytuo.calculator;

import java.util.Random;
import java.util.function.BiFunction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {

    private Calculator calculator;
    private final Random random = new Random();

    @BeforeEach
    void beforeEach() {
        calculator = new Calculator();
    }

    @Test
    void testNumber() {
        Integer testNumber = 412748;
        String expression = calculator.process(testNumber.toString());
        assertThat(expression).isEqualTo(testNumber + " = " + testNumber.doubleValue());
    }

    @Test
    void testAdd() {
        testOperation("+", Double::sum);
    }

    @Test
    void testSub() {
        testOperation("-", (i1, i2) -> i1 - i2);
    }

    @Test
    void testMul() {
        testOperation("*", (i1, i2) -> i1 * i2);
    }

    @Test
    void testDiv() {
        testOperation("/", (i1, i2) -> i1 / i2);

    }

    private void testOperation(String sign, BiFunction<Double, Double, Double> op) {
        int testNumber1 = random.nextInt(10000);
        int testNumber2 = random.nextInt(10000);
        String expression = calculator.process(testNumber1 + " " + sign + " " + testNumber2);
        assertThat(expression).isEqualTo(
                String.format("%s %s %s = %s", testNumber1, testNumber2, sign, op.apply((double)testNumber1, (double)testNumber2))
        );
    }

    @Test
    void testSimple() {
        String expression = calculator.process("3 + 4 * 2 / (1 - 5)");
        assertThat(expression).isEqualTo("3 4 2 * 1 5 - / + = 1.0");
    }

    @Test
    void testAdvanced() {
        String expression = calculator.process("(3 + 4 * 2 / (1 - 5)) / (1) * 0 + (4178 - 51 / 51) * 345 - 13");
        assertThat(expression).isEqualTo("3 4 2 * 1 5 - / + 1 / 0 * 4178 51 51 / - 345 * + 13 - = 1441052.0");
    }
}
