package com.rytuo.calculator.tokenizer.state;

import java.util.List;

import com.rytuo.calculator.token.Token;
import com.rytuo.calculator.token.brace.LeftBraceToken;
import com.rytuo.calculator.token.brace.RightBraceToken;
import com.rytuo.calculator.token.operation.AddToken;
import com.rytuo.calculator.token.operation.DivToken;
import com.rytuo.calculator.token.operation.MulToken;
import com.rytuo.calculator.token.operation.SubToken;

public class StartState implements TokenizerState {
    @Override
    public TokenizerState accept(int c, List<Token> tokens) {
        if (c == -1) {
            return new EndState();
        }

        if (Character.isDigit(c)) {
            return new NumberState().accept(c, tokens);
        }

        switch (c) {
            case '(' -> {
                tokens.add(new LeftBraceToken());
                return this;
            }
            case ')' -> {
                tokens.add(new RightBraceToken());
                return this;
            }
            case '+' -> {
                tokens.add(new AddToken());
                return this;
            }
            case '-' -> {
                tokens.add(new SubToken());
                return this;
            }
            case '*' -> {
                tokens.add(new MulToken());
                return this;
            }
            case '/' -> {
                tokens.add(new DivToken());
                return this;
            }
            default -> {
                if (Character.isWhitespace(c)) {
                    return this;
                }
                return new ErrorState();
            }
        }
    }
}
