package com.rytuo.calculator.tokenizer.state;

import java.util.List;

import com.rytuo.calculator.token.NumberToken;
import com.rytuo.calculator.token.Token;

public class NumberState implements TokenizerState {

    private final StringBuilder sb = new StringBuilder();

    @Override
    public TokenizerState accept(int c, List<Token> tokens) {
        if (Character.isDigit(c)) {
            sb.appendCodePoint(c);
            return this;
        }
        tokens.add(new NumberToken(Integer.parseInt(sb.toString())));
        return new StartState().accept(c, tokens);
    }
}
