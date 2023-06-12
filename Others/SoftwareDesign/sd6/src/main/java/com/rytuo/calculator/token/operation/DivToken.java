package com.rytuo.calculator.token.operation;

public class DivToken extends OperationToken {
    @Override
    public int getPriority() {
        return 1;
    }
}
