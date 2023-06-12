package com.rytuo.calculator;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.rytuo.calculator.token.Token;
import com.rytuo.calculator.tokenizer.Tokenizer;
import com.rytuo.calculator.visitor.CalcVisitor;
import com.rytuo.calculator.visitor.ParserVisitor;
import com.rytuo.calculator.visitor.PrintVisitor;
import com.rytuo.calculator.visitor.TokenVisitor;

public class Calculator {

    public String process(String input) {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
        );
        tokens = processTokens(new ParserVisitor(), tokens);
        String expression = processTokens(new PrintVisitor(), tokens);
        Double result = processTokens(new CalcVisitor(), tokens);
        return expression + " = " + result;
    }

    private <T> T processTokens(TokenVisitor<T> visitor, List<Token> tokens) {
        for (var token : tokens) {
            token.accept(visitor);
        }
        return visitor.getResult();
    }
}
