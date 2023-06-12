package generator.parser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

public class LL1Parser {
    private final Map<String, List<Rule>> rules;
    private final LL1Lexer lexer;
    private final String startRule;
    private final Map<String, Set<String>> first;
    private final Map<String, Set<String>> follow;

    public LL1Parser(
            Map<String, List<Rule>> rules,
            List<List<String>> terms,
            String startRule
    ) {
        this.rules = rules;
        this.lexer = new LL1Lexer(terms);
        this.startRule = startRule;

        // first
        this.first = new HashMap<>();
        rules.keySet().forEach(rule -> this.first.put(rule, new HashSet<>()));
        boolean changed = true;
        while (changed) {
            changed = rules.entrySet().stream()
                    .map(entry -> {
                        String ruleName = entry.getKey();
                        return entry.getValue().stream()
                                .map(right -> {
                                    if (right.elems.size() == 0) {
                                        return first.get(ruleName).add("");
                                    }

                                    String token = right.elems.get(0);
                                    if (Character.isUpperCase(token.charAt(0))) {
                                        return first.get(ruleName).add(token);
                                    } else {
                                        return first.get(ruleName).addAll(first.get(token));
                                    }
                                }).reduce(false, (acc, t) -> acc || t);
                    }).reduce(false, (acc, t) -> acc || t);
        }

        // follow
        this.follow = new HashMap<>();
        rules.keySet().forEach(rule -> follow.put(rule, new HashSet<>()));
        follow.get(startRule).add("$");
        changed = true;
        while (changed) {
            changed = rules.entrySet().stream().map(entry -> {
                String ruleName = entry.getKey();
                return entry.getValue().stream().map(right -> {
                    boolean changedR = false;
                    for (int i = 0; i < right.elems.size(); i++) {
                        String cur = right.elems.get(i);
                        if (Character.isUpperCase(cur.charAt(0))) {
                            continue;
                        }

                        if (i < right.elems.size() - 1) {
                            String next = right.elems.get(i + 1);
                            if (Character.isUpperCase(next.charAt(0))) {
                                changedR |= follow.get(cur).add(next);
                            } else {
                                Set<String> nextFirst = first.get(next);
                                if (nextFirst.contains("")) {
                                    Set<String> withoutEps = new HashSet<>(nextFirst);
                                    withoutEps.remove("");
                                    changedR |= follow.get(cur).addAll(withoutEps);
                                    changedR |= follow.get(cur).addAll(follow.get(next));
                                } else {
                                    changedR |= follow.get(cur).addAll(nextFirst);
                                }
                            }
                        } else {
                            changedR |= follow.get(cur).addAll(follow.get(ruleName));
                        }
                    }
                    return changedR;
                }).reduce(false, (acc, t) -> acc || t);
            }).reduce(false, (acc, t) -> acc || t);
        }
    }

    public Tree parse(InputStream is) throws ParseException {
        this.lexer.setInputStream(is);
        Tree tree = new Tree(startRule);
        return process(tree, null);
    }

    private Tree process(Tree node, String inh) throws ParseException {
        String name = node.val;

        // parse token
        if (Character.isUpperCase(name.charAt(0))) {
            if (this.lexer.curToken().equals(name)) {
                node.val = this.lexer.curVal();
                this.lexer.nextToken();
                return node;
            } else {
                throw new ParseException("Invalid token: " + this.lexer.curVal(), this.lexer.curPos());
            }
        }

        // parse val

        // init all variables
        Map<String, String> vars = new HashMap<>();
        if (inh != null) {
            vars.put("inh", inh);
        }

        Rule right = chooseRule(name);
        for (int i = 0; i < right.elems.size(); i++) {
            Tree rTree = new Tree(right.elems.get(i));
            node.addChild(rTree);

            String param = right.params.get(i);
            if (param != null) {
                param = runBash(param, "arg", vars);
            }
            String r = process(rTree, param).getRes();
            vars.put("r" + i, r);
        }

        if (right.code == null) {
            return node;
        }
        String ret = runBash(right.code, "res", vars);

        // default is to build a tree
        return ret.isEmpty() ? node : new Tree(ret);
    }

    private String runBash(String code, String returnVar, Map<String, String> vars) {
        String ret = "";

        ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", code +
                "\necho -n -e \"$" + returnVar + "\" > .temp.txt");
        Map<String, String> env = pb.environment();
        env.clear();
        env.putAll(vars);
        pb.inheritIO();
        try {
            pb.start().waitFor();
            ret = Files.readString(Paths.get(".temp.txt"), StandardCharsets.UTF_8);
        } catch (IOException|InterruptedException e) {
            System.err.println(e.getMessage());
        }
        return ret;
    }

    private Rule chooseRule(String name) throws ParseException {
        List<Rule> rights = this.rules.get(name);
        if (rights == null) {
            throw new ParseException("Invalid rule: " + name, this.lexer.curPos());
        }

        Rule right = rights.stream().filter(r -> {
            if (r.elems.size() == 0) {
                return true;
            }

            String token = r.elems.get(0);
            return token.equals(lexer.curToken()) || !Character.isUpperCase(token.charAt(0)) && (
                    first.get(token).contains(lexer.curToken()) ||
                            (first.get(token).contains("") && follow.get(token).contains(lexer.curToken())));
        }).findFirst().orElse(null);

        if (right == null) {
            throw new ParseException("No matching rule for rule <" + name +
                    "> and token <" + this.lexer.curToken() + ">",
                    this.lexer.curPos());
        }

        return right;
    }
}
