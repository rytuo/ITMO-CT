grammar Lab2;

start   : lambda;

lambda 	: LAMBDA args COLON add;

args    : VAR argsN
        |
        ;

argsN	: QUOTE VAR argsN
        |
        ;

add : mul addN;
addN: PLUS mul addN
    | MINUS mul addN
    |
    ;

mul : val mulN;
mulN: MUL val mulN
    | DIV val mulN
    | MOD val mulN
    | FLOOR val mulN
    |
    ;

val : MINUS val
    | N
    | VAR
    | OB add  CB
    ;

LAMBDA  : 'lambda';
COLON   : ':';
VAR     : '[a-zA-Z][a-zA-Z0-9_]*';
QUOTE   : ',';

PLUS    : '\+';
MINUS   : '-';
MOD     : '%';
FLOOR   : '//';
MUL     : '\*';
DIV     : '/';

N       : '[1-9][0-9]*';

OB      : '\(';
CB      : '\)';

WS      : '[ \n\t]+';
