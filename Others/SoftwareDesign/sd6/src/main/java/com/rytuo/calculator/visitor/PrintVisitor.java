package com.rytuo.calculator.visitor;

import java.util.StringJoiner;

import com.rytuo.calculator.token.brace.BraceToken;
import com.rytuo.calculator.token.NumberToken;
import com.rytuo.calculator.token.brace.LeftBraceToken;
import com.rytuo.calculator.token.brace.RightBraceToken;
import com.rytuo.calculator.token.operation.AddToken;
import com.rytuo.calculator.token.operation.DivToken;
import com.rytuo.calculator.token.operation.MulToken;
import com.rytuo.calculator.token.operation.OperationToken;
import com.rytuo.calculator.token.operation.SubToken;

public class PrintVisitor implements TokenVisitor<String> {

    private final StringJoiner sj = new StringJoiner(" ");

    @Override
    public void visit(NumberToken token) {
        sj.add(Integer.toString(token.value()));
    }

    @Override
    public void visit(BraceToken token) {
        if (token instanceof LeftBraceToken) {
            sj.add("(");
        } else if (token instanceof RightBraceToken) {
            sj.add(")");
        } else {
            throw new RuntimeException("Invalid brace");
        }
    }

    @Override
    public void visit(OperationToken token) {
        if (token instanceof AddToken) {
            sj.add("+");
        } else if (token instanceof SubToken) {
            sj.add("-");
        } else if (token instanceof MulToken) {
            sj.add("*");
        } else if (token instanceof DivToken) {
            sj.add("/");
        } else {
            throw new RuntimeException("Invalid operation");
        }
    }

    @Override
    public String getResult() {
        return sj.toString();
    }
}
