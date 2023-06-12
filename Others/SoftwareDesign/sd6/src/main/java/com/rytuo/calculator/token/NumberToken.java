package com.rytuo.calculator.token;

import com.rytuo.calculator.visitor.TokenVisitor;

public record NumberToken(int value) implements Token {
    @Override
    public <T> void accept(TokenVisitor<T> visitor) {
        visitor.visit(this);
    }
}
