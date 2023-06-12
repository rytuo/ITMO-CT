package com.rytuo.calculator.visitor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.rytuo.calculator.token.NumberToken;
import com.rytuo.calculator.token.Token;
import com.rytuo.calculator.token.brace.BraceToken;
import com.rytuo.calculator.token.brace.LeftBraceToken;
import com.rytuo.calculator.token.brace.RightBraceToken;
import com.rytuo.calculator.token.operation.OperationToken;

public class ParserVisitor implements TokenVisitor<List<Token>> {

    private final List<Token> result = new ArrayList<>();
    private final Deque<Token> stack = new ArrayDeque<>();

    @Override
    public void visit(NumberToken token) {
        result.add(token);
    }

    @Override
    public void visit(BraceToken token) {
        if (token instanceof LeftBraceToken) {
            stack.push(token);
        } else if (token instanceof RightBraceToken) {
            while (true) {
                if (stack.isEmpty()) {
                    throw new RuntimeException("Unpaired brace");
                }
                Token t = stack.pop();
                if (t instanceof LeftBraceToken) {
                    break;
                }
                result.add(t);
            }
        } else {
            throw new RuntimeException("Invalid brace");
        }
    }

    @Override
    public void visit(OperationToken token) {
        while(!stack.isEmpty()) {
            Token last = stack.peekFirst();
            if (last instanceof OperationToken &&
                    ((OperationToken) last).getPriority() >= token.getPriority()) {
                result.add(stack.pop());
            } else {
                break;
            }
        }
        stack.push(token);
    }

    @Override
    public List<Token> getResult() {
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }
}
