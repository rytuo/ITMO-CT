package generator.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tree {
    public String val;
    public List<Tree> children = new ArrayList<>();

    public Tree(String val) {
        this.val = val;
    }

    public void addChild(Tree child) {
        this.children.add(child);
    }

    public String visualize() {
        if (children.size() == 0) {
            return val;
        }

        return String.format("(%s %s)", val, children.stream().map(Tree::visualize).collect(Collectors.joining(" ")));
    }

    public String getRes() {
        if (children.size() == 0) {
            return val;
        }
        return children.stream().map(Tree::getRes).collect(Collectors.joining());
    }
}
