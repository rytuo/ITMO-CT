package com.rytuo.calculator.token.operation;

import com.rytuo.calculator.token.Token;
import com.rytuo.calculator.visitor.TokenVisitor;

public abstract class OperationToken implements Token {

    public abstract int getPriority();

    @Override
    public <T> void accept(TokenVisitor<T> visitor) {
        visitor.visit(this);
    }
}
