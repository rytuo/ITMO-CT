package com.rytuo.calculator.token.operation;

public class MulToken extends OperationToken {
    @Override
    public int getPriority() {
        return 1;
    }
}
