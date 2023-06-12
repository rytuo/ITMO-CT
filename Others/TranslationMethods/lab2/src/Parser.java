import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Objects;
import java.util.Scanner;

public class Parser {
    private LexicalAnalyzer lex = null;
    private int counter = 0;

    public static void main(String... args) throws ParseException, IOException {
        String input;
        if (Objects.nonNull(args) && args.length > 0 && Objects.nonNull(args[0])) {
            input = args[0];
        } else {
            System.out.println("Enter python lambda-expression:");
            // lambda: (lambda: (lambda: 1))
            input = new Scanner(System.in).nextLine();
        }

        Tree res = new Parser().parse(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        System.out.println(res.toString());

        String filename = "util/graph.dot";

        Writer writer = new FileWriter(filename);
        writer.write(res.getDot());
        writer.close();
    }

    public Tree parse(InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();

        Tree lambda = Lambda();

        // END
        if (this.lex.curToken() != Token.END) {
            throw exception();
        }

        return lambda;
    }

    private ParseException exception() {
        return new ParseException("Invalid token: " + this.lex.curToken() + " at pos " + this.lex.curPos(), this.lex.curPos());
    }

    private Tree Lambda() throws ParseException {
        counter++;

        // lambda
        if (this.lex.curToken() == Token.KEY && this.lex.curVal().equals("lambda")) {
            this.lex.nextToken();

            // Args
            Tree vars = Args();

            // colon
            if (this.lex.curToken() != Token.COLON) {
                throw exception();
            }
            this.lex.nextToken();

            // Add
            return new Tree(
                    "Lambda_" + counter,
                    new Tree("lambda"),
                    vars,
                    new Tree(":"),
                    Add()
            );
        }

        throw exception();
    }

    private Tree Args() throws ParseException {
        counter++;

        // var ArgsNext
        if (this.lex.curToken() == Token.VAR) {
            String var = this.lex.curVal();
            this.lex.nextToken();

            // ArgsNext
            return new Tree("Args_" + counter, new Tree(var), ArgsNext());
        }

        // Eps
        return null;
    }

    private Tree ArgsNext() throws ParseException {
        counter++;

        // quote
        if (this.lex.curToken() == Token.QUOTE) {
            this.lex.nextToken();

            // var
            if (this.lex.curToken() != Token.VAR) {
                throw exception();
            }
            String var = this.lex.curVal();
            this.lex.nextToken();

            // ArgsNext
            return new Tree("ArgsNext_" + counter, new Tree(","), new Tree(var), ArgsNext());
        }

        // Eps
        return null;
    }

    private Tree Add() throws ParseException {
        counter++;

        // Mul AddNext
        return new Tree("Add_" + counter, Mul(), AddNext());
    }

    private Tree AddNext() throws ParseException {
        counter++;

        // Eps
        if (this.lex.curToken() == Token.CLOSE
                || this.lex.curToken() == Token.END) {
            return null;
        }

        // [+-]
        String sign;
        if (this.lex.curToken() == Token.ADD) {
            sign = "+";
        } else if (this.lex.curToken() == Token.SUB) {
            sign = "-";
        } else {
            throw exception();
        }
        this.lex.nextToken();

        // Mul AddNext
        return new Tree("AddNext_" + counter, new Tree(sign), Mul(), AddNext());
    }

    private Tree Mul() throws ParseException {
        counter++;

        // Val MulNext
        return new Tree("Mul_" + counter, Val(), MulNext());
    }

    private Tree MulNext() throws ParseException {
        counter++;

        // Eps
        if (this.lex.curToken() == Token.CLOSE
                || this.lex.curToken() == Token.END
                || this.lex.curToken() == Token.ADD
                || this.lex.curToken() == Token.SUB) {
            return null;
        }

        // [*/%//]
        String sign;
        if (this.lex.curToken() == Token.MUL) {
            sign = "*";
        } else if (this.lex.curToken() == Token.DIV) {
            sign = "/";
        } else if (this.lex.curToken() == Token.MOD) {
            sign = "%";
        } else if (this.lex.curToken() == Token.FLOOR_DIV) {
            sign = "//";
        } else {
            throw exception();
        }
        this.lex.nextToken();

        // Val MulNext
        return new Tree("MulNext_" + counter, new Tree(sign), Val(), MulNext());
    }

    private Tree Val() throws ParseException {
        counter++;

        // Num | Var
        if (this.lex.curToken() == Token.NUM || this.lex.curToken() == Token.VAR) {
            String val = this.lex.curVal();
            this.lex.nextToken();
            return new Tree("Val_" + counter, new Tree(val));
        }

        // -
        if (this.lex.curToken() == Token.SUB) {
            this.lex.nextToken();

            // Val
            return new Tree("Val_" + counter, new Tree("-"), Val());
        }

        // ( Par
        if (this.lex.curToken() == Token.OPEN) {
            this.lex.nextToken();

            return new Tree("Val_" + counter, new Tree("("), Par());
        }

        throw exception();
    }

    private Tree Par() throws ParseException {
        counter++;

        Tree res;
        // lambda
        if (this.lex.curToken() == Token.KEY && this.lex.curVal().equals("lambda")) {
            res = Lambda();
        } else {
            res = Add();
        }

        if (this.lex.curToken() != Token.CLOSE) {
            throw exception();
        }
        this.lex.nextToken();

        return new Tree("Par_" + counter, res, new Tree(")"));
    }
}
