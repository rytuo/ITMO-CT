package com.rytuo.calculator.visitor;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rytuo.calculator.token.NumberToken;
import com.rytuo.calculator.token.Token;
import com.rytuo.calculator.token.brace.LeftBraceToken;
import com.rytuo.calculator.token.brace.RightBraceToken;
import com.rytuo.calculator.token.operation.AddToken;
import com.rytuo.calculator.token.operation.DivToken;
import com.rytuo.calculator.token.operation.MulToken;
import com.rytuo.calculator.token.operation.SubToken;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserVisitorTest {

    private ParserVisitor visitor;

    @BeforeEach
    void beforeEach() {
        visitor = new ParserVisitor();
    }

    @Test
    void testVisitNumber() {
        visitTokens(List.of(new NumberToken(81491200)), List.of(new NumberToken(81491200)));
    }

    @Test
    void testBraces() {
        visitTokens(List.of(
                new LeftBraceToken(), new NumberToken(2), new AddToken(), new NumberToken(2), new RightBraceToken(),
                new MulToken(), new NumberToken(2)
        ), List.of(
                new NumberToken(2), new NumberToken(2), new AddToken(), new NumberToken(2), new MulToken()
        ));
    }

    @Test
    void testAdd() {
        visitTokens(List.of(
                new NumberToken(61), new AddToken(), new NumberToken(39)
        ), List.of(
                new NumberToken(61), new NumberToken(39), new AddToken()
        ));
    }

    @Test
    void testSub() {
        visitTokens(List.of(
                new NumberToken(61), new SubToken(), new NumberToken(39)
        ), List.of(
                new NumberToken(61), new NumberToken(39), new SubToken()
        ));
    }

    @Test
    void testMul() {
        visitTokens(List.of(
                new NumberToken(61), new MulToken(), new NumberToken(39)
        ), List.of(
                new NumberToken(61), new NumberToken(39), new MulToken()
        ));
    }

    @Test
    void testDiv() {
        visitTokens(List.of(
                new NumberToken(61), new DivToken(), new NumberToken(39)
        ), List.of(
                new NumberToken(61), new NumberToken(39), new DivToken()
        ));
    }

    private void visitTokens(List<Token> tokens, List<Token> expected) {
        tokens.forEach(token -> token.accept(visitor));
        List<Token> actual = visitor.getResult();
        assertThat(actual).hasSameSizeAs(expected);
        for (int i = 0; i < actual.size(); i++) {
            Token token1 = actual.get(i);
            Token token2 = expected.get(i);
            assertThat(token1).isInstanceOf(token2.getClass());
            if (token1.getClass().equals(NumberToken.class)) {
                assertThat(((NumberToken) token1).value())
                        .isEqualTo(((NumberToken)token2).value());
            }
        }
    }
}
