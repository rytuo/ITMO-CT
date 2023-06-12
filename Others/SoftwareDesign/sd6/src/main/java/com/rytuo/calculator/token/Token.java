package com.rytuo.calculator.token;

import com.rytuo.calculator.visitor.TokenVisitor;

public interface Token {
    <T> void accept(TokenVisitor<T> visitor);
}
