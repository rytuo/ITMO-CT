package com.rytuo.calculator.visitor;

import com.rytuo.calculator.token.brace.BraceToken;
import com.rytuo.calculator.token.operation.OperationToken;
import com.rytuo.calculator.token.NumberToken;

public interface TokenVisitor<T> {
    void visit(NumberToken token);
    void visit(BraceToken token);
    void visit(OperationToken token);
    T getResult();
}
