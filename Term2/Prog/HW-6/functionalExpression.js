"use strict";

const BinaryExpression = f => (a, b) => x => f(a(x), b(x));

const UnaryExpression = f => a => x => f(a, x);

const add = BinaryExpression((a, b) => a + b);

const subtract = BinaryExpression((a, b) => a - b);

const multiply = BinaryExpression((a, b) => a * b);

const divide = BinaryExpression((a, b) => a / b);

const variable = UnaryExpression((a, x) => x);

const cnst = UnaryExpression((a, x) => a);

const neg = UnaryExpression((a, x) => -a);

const between09 = c => {
    return "0" <= c && c <= "9";
};

const parseExpression = stek => input => {
    input = input.trim();
    if (input.length === 0) {
        return stek.pop();
    }
    let i = 0;
    if ((between09(input[0])) || (input[0] === "-" && between09(input[1]))) {
        if (input[0] === "-") {
            i++;
        }
        for (; between09(input[i]); i++);
        stek.push(cnst(Number(input.substr(0, i))));
    } else if (input[0] === 'x') {
        stek.push(variable('x'));
        i++;
    } else {
        const right = stek.pop();
        const left = stek.pop();
        switch (input[0]) {
            case "+":
                stek.push(add(left, right));
                break;
            case "-":
                stek.push(subtract(left, right));
                break;
            case "*":
                stek.push(multiply(left, right));
                break;
            case "/":
                stek.push(divide(left, right));
                break;
        }
        i++;
    }
    return parseExpression(stek) (input.substr(i));
};

const parse = parseExpression([]);

//C:\Users\Asus\files\actual\javascript\src>java -ea --module-path=graal jstest.functional.FunctionalExpressionTest hard