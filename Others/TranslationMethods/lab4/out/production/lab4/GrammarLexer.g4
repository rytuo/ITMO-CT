lexer grammar GrammarLexer;

GR
    : 'grammar' -> pushMode(DEFAULT_MODE);

mode DEFAULT_MODE;

T
    : [A-Z][a-zA-Z0-9_]* -> pushMode(T_MODE);

R
    : [a-z][a-zA-Z0-9_]* -> pushMode(R_MODE);

LINE_SEP
    : ';';

WS
    : [ \t\n] -> skip;

mode T_MODE;

LINE_SEP_T
    : ';' -> type(LINE_SEP), popMode;

COLON
    : ':';

Q
    : '\'' -> pushMode(Q_MODE);

WS_T
    : [ \t\n] -> skip;

mode Q_MODE;

Q_TEXT
    : ~[\\']+
    | '\\' .;

Q_Q
    : '\'' -> type(Q), popMode;

mode R_MODE;

SB
    : '[' -> pushMode(SB_MODE);

FB
    : '{' -> pushMode(FB_MODE);

LINE_SEP_R
    : ';' -> type(LINE_SEP), popMode;

COLON_R
    : ':' -> type(COLON);

RULE_SEP
    : '|';

T_R
    : [A-Z][a-zA-Z0-9_]* -> type(T)
    ;

R_R
    : [a-z][a-zA-Z0-9_]* -> type(R)
    ;

WS_R
    : [ \t\n] -> skip;

mode SB_MODE;

SB_TEXT
    : ~('\\'|'['|']')+
    | '\\' .;

OSB_SB
    : '[' -> type(SB), pushMode(SB_MODE);

CSB_SB
    : ']' -> type(SB), popMode;

mode FB_MODE;

FB_TEXT
    : ~[\\{}]+
    | '\\' .;

OFB_FB
    : '{' -> type(FB), pushMode(FB_MODE);

CFB_FB
    : '}' -> type(FB), popMode;
