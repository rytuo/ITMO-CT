package com.rytuo.calculator.visitor;

import java.util.ArrayDeque;
import java.util.Deque;

import com.rytuo.calculator.token.brace.BraceToken;
import com.rytuo.calculator.token.NumberToken;
import com.rytuo.calculator.token.operation.AddToken;
import com.rytuo.calculator.token.operation.DivToken;
import com.rytuo.calculator.token.operation.MulToken;
import com.rytuo.calculator.token.operation.OperationToken;
import com.rytuo.calculator.token.operation.SubToken;

public class CalcVisitor implements TokenVisitor<Double> {

    private final Deque<Double> deque = new ArrayDeque<>();

    @Override
    public void visit(NumberToken token) {
        deque.push((double) token.value());
    }

    @Override
    public void visit(BraceToken token) {
        throw new RuntimeException("Invalid brace");
    }

    @Override
    public void visit(OperationToken token) {
        if (deque.size() < 2) {
            throw new RuntimeException("Not enough operands");
        }
        Double a1 = deque.pop(),
                a0 = deque.pop();
        if (token instanceof AddToken) {
            deque.push(a0 + a1);
        } else if (token instanceof SubToken) {
            deque.push(a0 - a1);
        } else if (token instanceof MulToken) {
            deque.push(a0 * a1);
        } else if (token instanceof DivToken) {
            deque.push(a0 / a1);
        } else {
            throw new RuntimeException("Invalid operation");
        }
    }

    @Override
    public Double getResult() {
        return deque.pop();
    }
}
