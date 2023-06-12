package generator;

import generator.grammar.GrammarLexer;
import generator.grammar.GrammarParser;
import generator.parser.LL1Parser;
import generator.parser.Rule;
import generator.parser.Tree;
import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

public class ParserGenerator {
    public static void main(String... args) throws IOException, ParseException {
        String grammarFile, startRule, inputFile;

        if (Objects.nonNull(args) && args.length > 0 && Objects.nonNull(args[0])) {
            grammarFile = args[0];
        } else {
            System.err.println("No grammar file found");
            return;
        }

        if (args.length > 1 && Objects.nonNull(args[1])) {
            startRule = args[1];
        } else {
            System.err.println("No start rule found");
            return;
        }

        if (args.length > 2 && Objects.nonNull(args[2])) {
            inputFile = args[2];
        } else {
            System.err.println("No start rule found");
            return;
        }

        LL1Parser parser = generate(grammarFile, startRule);

        Tree res = parser.parse(new FileInputStream(inputFile));

        System.out.println(res.visualize());
    }

    public static LL1Parser generate(String grammarFile, String startRule) throws IOException {
        // start node
        ParseTree rulesN = getTree(grammarFile);

        // rules node
        rulesN = rulesN.getChild(3);

        Map<String, List<Rule>> rules = new HashMap<>();
        List<List<String>> terms = new ArrayList<>();
        terms.add(List.of("EOF", "$"));
        fillGrammar(rulesN, rules, terms);

        return new LL1Parser(rules, terms, startRule);
    }

    private static void fillGrammar(ParseTree rulesN, Map<String, List<Rule>> ruleMap, List<List<String>> terms) {
        while (rulesN.getChildCount() != 0) {
            ParseTree rules = rulesN.getChild(0);

            // getRuleName
            String ruleName = rules.getChild(0).getText();

            if (Character.isUpperCase(ruleName.charAt(0))) {
                // parse terminal
                String token = rules.getChild(2).getChild(1).getText();
                terms.add(List.of(ruleName, token));
            } else {
                // parse non-terminal

                List<Rule> rows = new ArrayList<>();
                ruleMap.put(ruleName, rows);

                // get first row
                Rule row = new Rule();
                rows.add(row);

                ParseTree singleRule = rules.getChild(2);
                ParseTree tks = singleRule.getChild(0);
                ParseTree code = singleRule.getChild(1);
                if (code.getChildCount() > 0) {
                    row.code = code.getChild(1).getText();
                }

                while (tks.getChildCount() > 0) {
                    String name = tks.getChild(0).getText();
                    row.elems.add(name);
                    if (Character.isUpperCase(name.charAt(0))) {
                        row.params.add(null);
                    } else {
                        ParseTree params = tks.getChild(1);
                        if (params.getChildCount() > 0) {
                            row.params.add(params.getChild(1).getText());
                        } else {
                            row.params.add(null);
                        }
                    }
                    tks = tks.getChild(tks.getChildCount() - 1);
                }

                // get other rows
                ParseTree nextRule = rules.getChild(3);
                while (nextRule.getChildCount() > 0) {
                    row = new Rule();
                    rows.add(row);

                    singleRule = nextRule.getChild(1);
                    tks = singleRule.getChild(0);
                    code = singleRule.getChild(1);
                    if (code.getChildCount() > 0) {
                        row.code = code.getChild(1).getText();
                    }

                    while (tks.getChildCount() != 0) {
                        String name = tks.getChild(0).getText();
                        row.elems.add(name);
                        if (Character.isUpperCase(name.charAt(0))) {
                            row.params.add(null);
                        } else {
                            ParseTree params = tks.getChild(1);
                            if (params.getChildCount() > 0) {
                                row.params.add(params.getChild(1).getText());
                            } else {
                                row.params.add(null);
                            }
                        }
                        tks = tks.getChild(tks.getChildCount() - 1);
                    }

                    nextRule = nextRule.getChild(2);
                }
            }

            // nextRule
            rulesN = rulesN.getChild(1);
        }
    }

    private static ParserRuleContext getTree(String grammarFile) throws IOException {
        CharStream charStream = CharStreams.fromPath(Paths.get(grammarFile), Charset.defaultCharset());
        Lexer lexer = new GrammarLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();
        GrammarParser parser = new GrammarParser(tokens);
        parser.setBuildParseTree(true);
        ParserRuleContext tree = parser.start();

        Trees.inspect(tree, parser);
        return tree;
    }
}
