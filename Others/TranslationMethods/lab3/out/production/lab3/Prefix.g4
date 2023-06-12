grammar Prefix;

start: prog;

prog: ns EOF { System.out.println("begin\n" + $ns.res + "end."); };

ns returns[String res]
    : stmt ns
        { $res = $stmt.res + ";\n" + $ns.res; }
    |
        { $res = ""; }
    ;

stmt returns[String res]
    : 'if' expr s1=stmt (s2=stmt)?
        { $res = "if (" + $expr.res + ") then " + $s1.res +
        ($s2.text != null ? ("\nelse " + $s2.res) : ""); }
    | '(' ns ')'
        { $res = "begin\n" + $ns.res + "end"; }
    | 'print' expr
        { $res = "writeln(" + $expr.res + ")"; }
    | '=' VAR expr
        { $res = $VAR.text + " = " + $expr.res; }
    | 'while' expr stmt
        { $res = "while " + $expr.res + " do\n" + $stmt.res; }
    ;

expr returns[String res]
    : op=('or'|'and'|'<'|'>'|'=='|'+'|'-'|'*'|'/') e1=expr e2=expr
        { $res = "(" + $e1.res + " " + $op.text + " " + $e2.res + ")"; }
    | 'not' expr
        { $res = "(not " + $expr.res + ")"; }
    | val
        { $res = $val.text; }
    ;

val : N
    | L
    | VAR
    ;

N   : [0-9]+;

WS  : [ \t\n]+ -> skip;

L   : 'True' | 'False';

KEY : 'await' | 'else' | 'import'
    | 'pass' | 'None' | 'break' | 'except'
    | 'in' | 'raise' | 'class'
    | 'finally' | 'is' | 'return' | 'and'
    | 'continue' | 'for' | 'lambda' | 'try'
    | 'as' | 'def' | 'from' | 'nonlocal'
    | 'while' | 'assert' | 'del' | 'global'
    | 'not' | 'with' | 'async' | 'elif'
    | 'if' | 'or' | 'yield';

VAR : [a-zA-Z][a-zA-Z0-9_]*;