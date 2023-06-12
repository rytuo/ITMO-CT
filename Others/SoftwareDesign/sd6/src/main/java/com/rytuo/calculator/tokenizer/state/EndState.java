package com.rytuo.calculator.tokenizer.state;

import java.util.List;

import com.rytuo.calculator.token.Token;

public class EndState implements TokenizerState {
    @Override
    public TokenizerState accept(int c, List<Token> tokens) {
        return this;
    }
}
