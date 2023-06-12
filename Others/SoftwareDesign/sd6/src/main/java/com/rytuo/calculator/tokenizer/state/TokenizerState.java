package com.rytuo.calculator.tokenizer.state;

import java.util.List;

import com.rytuo.calculator.token.Token;

public interface TokenizerState {
    TokenizerState accept(int c, List<Token> tokens);
}
