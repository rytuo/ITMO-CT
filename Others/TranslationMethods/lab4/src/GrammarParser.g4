parser grammar GrammarParser;

options {
    tokenVocab = GrammarLexer;
}

start
    : GR T LINE_SEP rulesN EOF
    ;

rulesN
    : rules rulesN
    |
    ;

rules
    : T COLON reg LINE_SEP
    | R COLON singleRule nextRule LINE_SEP
    ;

reg
    : Q q_text Q;

q_text
    : Q_TEXT+;

singleRule
    : tks code
    ;

code
    : FB fb_text FB
    |
    ;

fb_text
    : (FB_TEXT | FB FB_TEXT FB)+;

nextRule
    : RULE_SEP singleRule nextRule
    |
    ;

tks
    : T tks
    | R params tks
    |
    ;

params
    : SB sb_text SB
    |
    ;

sb_text
    : (SB_TEXT | SB SB_TEXT SB)+;
