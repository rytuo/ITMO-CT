package com.rytuo.calculator.tokenizer;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
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

public class TokenizerTest {

    private Tokenizer tokenizer;

    @BeforeEach
    void beforeEach() {
        tokenizer = new Tokenizer();
    }

    @Test
    void testLeftBrace() {
        assertTokenized("(", new LeftBraceToken());
    }

    @Test
    void testRightBrace() {
        assertTokenized(")", new RightBraceToken());
    }

    @Test
    void testNumber() {
        assertTokenized("74817", new NumberToken(74817));
    }

    @Test
    void testAdd() {
        assertTokenized("+", new AddToken());
    }

    @Test
    void testSub() {
        assertTokenized("-", new SubToken());
    }

    @Test
    void testMul() {
        assertTokenized("*", new MulToken());
    }

    @Test
    void testDiv() {
        assertTokenized("/", new DivToken());
    }

    @Test
    void testTokenize() {
        assertTokenized(
                "(3 + 4 * 2 / (1 - 5)) / (1) * 0 + (4178 - 51 / 51) * 345 - 13",
                new LeftBraceToken(),
                new NumberToken(3),
                new AddToken(),
                new NumberToken(4),
                new MulToken(),
                new NumberToken(2),
                new DivToken(),
                new LeftBraceToken(),
                new NumberToken(1),
                new SubToken(),
                new NumberToken(5),
                new RightBraceToken(),
                new RightBraceToken(),
                new DivToken(),
                new LeftBraceToken(),
                new NumberToken(1),
                new RightBraceToken(),
                new MulToken(),
                new NumberToken(0),
                new AddToken(),
                new LeftBraceToken(),
                new NumberToken(4178),
                new SubToken(),
                new NumberToken(51),
                new DivToken(),
                new NumberToken(51),
                new RightBraceToken(),
                new MulToken(),
                new NumberToken(345),
                new SubToken(),
                new NumberToken(13));
    }

    private void assertTokenized(String input, Token... tokens) {
        List<Token> result = tokenizer.tokenize(
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
        );
        assertThat(result).hasSize(tokens.length);
        for (int i = 0; i < tokens.length; i++) {
            Token token1 = result.get(i);
            Token token2 = tokens[i];
            assertThat(token1).isInstanceOf(token2.getClass());
            if (token1.getClass().equals(NumberToken.class)) {
                assertThat(((NumberToken)token1).value())
                        .isEqualTo(((NumberToken) token2).value());
            }
        }
    }
}
