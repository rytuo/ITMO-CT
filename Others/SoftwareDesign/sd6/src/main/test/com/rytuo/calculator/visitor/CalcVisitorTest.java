package com.rytuo.calculator.visitor;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rytuo.calculator.token.NumberToken;
import com.rytuo.calculator.token.Token;
import com.rytuo.calculator.token.operation.AddToken;
import com.rytuo.calculator.token.operation.DivToken;
import com.rytuo.calculator.token.operation.MulToken;
import com.rytuo.calculator.token.operation.SubToken;

import static org.assertj.core.api.Assertions.assertThat;

public class CalcVisitorTest {

    private CalcVisitor visitor;

    @BeforeEach
    void beforeEach() {
        visitor = new CalcVisitor();
    }

    @Test
    void testVisitNumber() {
        visitTokens(List.of(new NumberToken(81491200)), 81491200);
    }

    @Test
    void testBraces() {
        visitTokens(List.of(
                new NumberToken(2), new NumberToken(2), new AddToken(), new NumberToken(2), new MulToken()
        ), 8);
    }

    @Test
    void testAdd() {
        visitTokens(List.of(
                new NumberToken(61), new NumberToken(39), new AddToken()
        ), 100);
    }

    @Test
    void testSub() {
        visitTokens(List.of(
                new NumberToken(61), new NumberToken(39), new SubToken()
        ), 22);
    }

    @Test
    void testMul() {
        visitTokens(List.of(
                new NumberToken(61), new NumberToken(39), new MulToken()
        ), 2379);
    }

    @Test
    void testDiv() {
        visitTokens(List.of(
                new NumberToken(62), new NumberToken(8), new DivToken()
        ), 7.75);
    }

    private void visitTokens(List<Token> tokens, Number result) {
        tokens.forEach(token -> token.accept(visitor));
        assertThat(visitor.getResult()).isEqualTo(result.doubleValue());
    }
}
