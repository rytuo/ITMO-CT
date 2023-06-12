package com.rytuo.calculator.token.brace;

import com.rytuo.calculator.token.Token;
import com.rytuo.calculator.visitor.TokenVisitor;

public abstract class BraceToken implements Token {
    @Override
    public <T> void accept(TokenVisitor<T> visitor) {
        visitor.visit(this);
    }
}
