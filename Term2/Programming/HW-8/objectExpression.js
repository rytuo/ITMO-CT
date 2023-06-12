"use strict";

function UnaryExpression(value, f, sign) {
    this.value = value;
    this.f = f;
    this.sign = sign;
}
UnaryExpression.prototype.evaluate = function (x, y, z) { return this.f(this.value.evaluate(x, y, z)) };
UnaryExpression.prototype.toString = function () {
    return (this.value.toString() + " " + this.sign);
};
UnaryExpression.prototype.prefix = function () { return '(' + this.sign + ' ' + this.value.prefix() + ')' };

function BinaryExpression(left, right, f, sign) {
    this.left = left;
    this.right = right;
    this.f = f;
    this.sign = sign;
}
BinaryExpression.prototype.evaluate = function (x, y, z) {
    return this.f(this.left.evaluate(x, y, z), this.right.evaluate(x, y, z))
};
BinaryExpression.prototype.toString = function () {
    return (this.left.toString() + " " + this.right.toString() + " " + this.sign);
};
BinaryExpression.prototype.prefix = function () {
    return '(' + this.sign + ' ' + this.left.prefix() + ' ' + this.right.prefix() + ')'
};

function Add(left, right) {
    BinaryExpression.call(this, left, right, (a, b) => a + b, '+');
}
Add.prototype = Object.create(BinaryExpression.prototype);

function Subtract(left, right) {
    BinaryExpression.call(this, left, right, (a, b) => a - b, '-');
}
Subtract.prototype = Object.create(BinaryExpression.prototype);

function Multiply(left, right) {
    BinaryExpression.call(this, left, right, (a, b) => a * b, '*');
}
Multiply.prototype = Object.create(BinaryExpression.prototype);

function Divide(left, right) {
    BinaryExpression.call(this, left, right, (a, b) => a / b, '/');
}
Divide.prototype = Object.create(BinaryExpression.prototype);

function Negate(value) {
    UnaryExpression.call(this, value, a => -a, "negate");
}
Negate.prototype = Object.create(UnaryExpression.prototype);

function Const(value) {
    this.value = value;
    this.evaluate = function(x, y, z) {
        return this.value;
    };
    this.toString = function () { return this.value.toString() };
    this.prefix = function () { return this.toString() }
}

function Variable(value) {
    this.value = value;
    this.evaluate = function(x, y, z) {
        switch(this.value) {
            case 'x':
                return x;
            case 'y':
                return y;
            case 'z':
                return z;
        }
    };
    this.toString = function () { return this.value };
    this.prefix = function () { return this.value };
}

function isDigit(c) { return '0' <= c && c <= '9'}

function ParsingError(message, expr, i) {
    this.message = message + '\n' + expr + '\n';
    for (let j = 0; j < i; j++) { this.message += ' '}
    this.message += '^';
}
ParsingError.prototype = Object.create(Error.prototype);
ParsingError.prototype.name = "ParsingError";
ParsingError.prototype.constructor = ParsingError;

// expr:   1) ws *sign* ws *expr* ws *expr* ws
//         2) ws *sign* ws *expr* ws
//         3) ws ( ws *expr* ws ) ws
//         4) ws +-number ws
//         5) ws x/y/z ws

let Parser = function(expr, i) {
    function skipWhitespace() { while(i < expr.length && expr[i] === ' ') { i++ } }

    function parse() {
        let parsedExpr = parseExpr(false);
        if (i === expr.length) {
            return parsedExpr;
        } else {
            throw new ParsingError('End of expression expected', expr, i);
        }
    }

    function parseExpr(leftPar) {
        let val;
        skipWhitespace();
        if (expr[i] === '(') {
            i++;
            val = parseExpr(true);
        } else if (expr[i] === 'n') {
            for (let q of 'negate') {
                if (expr[i++] !== q) {
                    throw new ParsingError('Unexpected symbol', expr, i)
                }
            }
            val = new Negate(parseExpr(false));
        } else if (isDigit(expr[i]) || (expr[i] === '-' && isDigit(expr[i + 1]))) {
            let num = '';
            while (isDigit(expr[i]) || expr[i] === '-') {
                num += expr[i++];
            }
            val = new Const(Number(num));
        } else if (expr[i] === 'x' || expr[i] === 'y' || expr[i] === 'z') {
            val = new Variable(expr[i++]);
        } else if (expr[i] === '+' || expr[i] === '-' ||
            expr[i] === '*' || expr[i] === '/') {
            const sign = expr[i++];
            const left = parseExpr(false);
            const right = parseExpr(false);
            if (left === null || right === null) {
                throw new ParsingError('Argument expected', expr, i)
            }
            switch (sign) {
                case '+':
                    val = new Add(left, right);
                    break;
                case '-':
                    val = new Subtract(left, right);
                    break;
                case '*':
                    val = new Multiply(left, right);
                    break;
                case '/':
                    val = new Divide(left, right);
            }
        } else {
            throw new ParsingError('Unexpected symbol', expr, i);
        }
        skipWhitespace();
        if (leftPar) {
            if (expr[i++] !== ')') {
                throw new ParsingError('No closing parenthesis', expr, i);
            }
        }
        return val;
    }

    return {skipWhitespace: skipWhitespace, parse: parse, parseExpr: parseExpr};
};

const parsePrefix = expr => Parser(expr, 0).parse();