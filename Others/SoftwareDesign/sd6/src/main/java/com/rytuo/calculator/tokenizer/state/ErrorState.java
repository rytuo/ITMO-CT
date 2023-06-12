package com.rytuo.calculator.tokenizer.state;

import java.util.List;

import com.rytuo.calculator.token.Token;

public class ErrorState implements TokenizerState {
    @Override
    public TokenizerState accept(int c, List<Token> tokens) {
        return this;
    }
}
