import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Tree {
    private final String node;
    private final List<Tree> children;

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.stream(children)
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Tree(String node) {
        this.node = node;
        this.children = null;
    }

    public String getDot() {
        return "strict digraph {\n" + this.describe() + "}";
    }

    private static String toDot(String s) {
        switch (s) {
            case ",":
                return "quote";
            case ":":
                return "colon";
            case "+":
                return "plus";
            case "-":
                return "minus";
            case "*":
                return "mul";
            case "/":
                return "div";
            case "%":
                return "mod";
            case "//":
                return "floor_div";
            case "(":
                return "open";
            case ")":
                return "close";
            default:
                return s;
        }
    }

    public String describe() {
        if (children == null) {
            return "";
        }

        return toDot(node) + " -> {" + children.stream().map(c -> toDot(c.node)).collect(Collectors.joining(" ")) + "}\n"
                + children.stream().map(Tree::describe).collect(Collectors.joining());
    }

    public String toString() {
        if (this.children == null) {
            return node;
        }

        return this.children.stream().map(Tree::toString).collect(Collectors.joining(" "));
    }
}
