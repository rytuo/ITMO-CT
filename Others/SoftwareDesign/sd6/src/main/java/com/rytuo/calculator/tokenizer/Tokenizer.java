package com.rytuo.calculator.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.rytuo.calculator.token.Token;
import com.rytuo.calculator.tokenizer.state.EndState;
import com.rytuo.calculator.tokenizer.state.StartState;
import com.rytuo.calculator.tokenizer.state.TokenizerState;

public class Tokenizer {

    private final List<Token> tokens = new ArrayList<>();
    private TokenizerState state = new StartState();

    public List<Token> tokenize(InputStream inputStream) {
        try (var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            int c;
            do {
                c = reader.read();
                state = state.accept(c, tokens);
            } while(c != -1);
        } catch (IOException e) {
            throw new RuntimeException("Error while parsing input: " + e.getMessage());
        }
        if (state instanceof EndState) {
            return tokens;
        }
        throw new RuntimeException("Invalid expression structure");
    }
}
