grammar Calculator;

start
    : add
    ;

add
    : mul addNext
    ;

addNext
    : PLUS mul addNext
    | MINUS mul addNext
    |
    ;

mul
    : pow mulNext
    ;

mulNext
    : MUL pow mulNext
    | DIV pow mulNext
    |
    ;


pow
    : val powN
    ;

powN
    : POW pow
    |
    ;

POW : '\*\*';

val
    : N
    | OB add CB
    ;

N       : '0|[1-9][0-9]*';

PLUS    : '\+';
MINUS   : '-';
MUL     : '\*';
DIV     : '/';
OB      : '\(';
CB      : '\)';

WS      : '[ \n\t]+';
