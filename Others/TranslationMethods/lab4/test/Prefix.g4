grammar Prefix;

start: ns EOF { res="begin\n${r0}end." };

ns
    : stmt ns
        { res="${r0};\n${r1}" }
    |
        { res=" " }
    ;

stmt
    : COND expr stmt else
        { res="if (${r1}) then ${r2}${r3}" }
    | OB ns CB
        { res="begin\n${r1}end" }
    | PRINT expr
        { res="writeln(${r1})" }
    | EQ VAR expr
        { res="${r1} = ${r2}" }
    ;

else
    : stmt
        { res="\nelse ${r0}" }
    |
        { res=" " }
    ;

expr
    : OP expr expr
        { res="($r1 ${r0} ${r2})" }
    | NOT expr
        { res="(not ${r1})" }
    | val
        { res="$r0" }
    ;

val : N
    | L
    | VAR
    ;

OP  : 'or|and|<|>|==|\+|-|\*|/';

COND: 'if';
EQ  : '=';
PRINT: 'print';
OB  : '\(';
CB  : '\)';

NOT: 'not';

N   : '0|[1-9][0-9]*';

WS  : '[ \t\n]+';

L   : 'True|False';

KEY : 'await|else|import|pass|None|break|except|in|raise|class|finally|is|return|continue|for|lambda|try|as|def|from|nonlocal|while|assert|del|global|not|with|async|elif|yield';

VAR : '[a-zA-Z][a-zA-Z0-9_]*';
