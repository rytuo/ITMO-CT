grammar CountingCalculator;

start
    : add EOF
        {res=$r0}
    ;

add
    : mul addN[arg=$r0]
        {res=$r1}
    ;

addN
    : PLUS mul addN[arg=$((inh + r1))]
        {res=$r2}
    | MINUS mul addN[arg=$((inh - r1))]
        {res=$r2}
    |
        {res=$inh}
    ;

mul
    : pow mulN[arg=$r0]
        {res=$r1}
    ;

mulN
    : MUL pow mulN[arg=$((inh * r1))]
        {res=$r2}
    | DIV pow mulN[arg=$((inh / r1))]
        {res=$r2}
    |
        {res=$inh}
    ;

pow
    : val powN
        {res=`echo "$r0^$r1" | bc`}
    ;

powN
    : POW pow
        {res=$r1}
    |
        {res="1"}
    ;

POW: '\*\*';

val
    : MINUS val
        {res=$((r1 * -1))}
    | N
        {res=$r0}
    | OB add CB
        {res=$r1}
    ;

PLUS    : '\+';
MINUS   : '-';
MUL     : '\*';
DIV     : '/';

OB  : '\(';
CB  : '\)';

N   : '0|[1-9][0-9]*';

WS  : '[ \t\n]+';
