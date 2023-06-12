package com.rytuo.calculator.visitor;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.rytuo.calculator.token.NumberToken;
import com.rytuo.calculator.token.Token;
import com.rytuo.calculator.token.brace.LeftBraceToken;
import com.rytuo.calculator.token.brace.RightBraceToken;
import com.rytuo.calculator.token.operation.AddToken;
import com.rytuo.calculator.token.operation.DivToken;
import com.rytuo.calculator.token.operation.MulToken;
import com.rytuo.calculator.token.operation.SubToken;

public class PrintVisitorTest {

    private PrintVisitor visitor;

    @BeforeEach
    void beforeEach() {
        visitor = new PrintVisitor();
    }

    @Test
    void testVisitLeftBrace() {
        assertVisited(List.of(new LeftBraceToken()), "(");
    }

    @Test
    void testVisitRightBrace() {
        assertVisited(List.of(new RightBraceToken()), ")");
    }

    @Test
    void testVisitAdd() {
        assertVisited(List.of(new AddToken()), "+");
    }

    @Test
    void testVisitSub() {
        assertVisited(List.of(new SubToken()), "-");
    }

    @Test
    void testVisitMul() {
        assertVisited(List.of(new MulToken()), "*");
    }

    @Test
    void testVisitDiv() {
        assertVisited(List.of(new DivToken()), "/");
    }

    @Test
    void testVisitNumber() {
        assertVisited(List.of(new NumberToken(73711)), "73711");
    }

    @Test
    void testVisitExpression() {
        assertVisited(
                List.of(new NumberToken(3), new AddToken(), new NumberToken(4), new MulToken(),
                        new NumberToken(2), new DivToken(), new LeftBraceToken(), new NumberToken(1),
                        new SubToken(), new NumberToken(5), new RightBraceToken()
                ),
                "3 + 4 * 2 / ( 1 - 5 )"
        );
    }

    private void assertVisited(List<Token> tokens, String expected) {
        tokens.forEach(token -> token.accept(visitor));
        assertThat(visitor.getResult()).isEqualTo(expected);
    }
}
