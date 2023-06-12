class Node:
    def __init__(self, val, children):
        self.val = val
        self.children = children

    def __str__(self):
        return self.val


class Tree:
    def __init__(self, s):
        self.nodes: list[Node] = []
        self.__build_tree(s + '$')

    def __build_tree(self, s):
        from os.path import commonprefix
        root = Node('', [])
        self.nodes.append(root)

        for i in range(0, len(s) - 1):
            cur_s = s[i:]
            cur_node = 0

            while True:
                prefix = commonprefix((cur_s, self.nodes[cur_node].val))
                cur_s = cur_s[len(prefix):]

                if prefix == self.nodes[cur_node].val:
                    next_node = None
                    for child_ind in self.nodes[cur_node].children:
                        if self.nodes[child_ind].val[0] == cur_s[0]:
                            next_node = child_ind
                            break

                    if next_node is None:
                        self.nodes[cur_node].children.append(len(self.nodes))
                        self.nodes.append(Node(cur_s, []))
                    else:
                        cur_node = next_node
                        continue
                else:
                    old_node = self.nodes[cur_node]
                    old_node.val = old_node.val[len(prefix):]
                    self.nodes[cur_node] = Node(prefix, [len(self.nodes), len(self.nodes) + 1])
                    self.nodes.append(old_node)
                    self.nodes.append(Node(cur_s, []))
                break

    def find_longest_repeat(self, cur=0, cur_s=''):
        if not len(self.nodes[cur].children):
            return None

        mx = None
        for i in self.nodes[cur].children:
            res_i = self.find_longest_repeat(i, cur_s + self.nodes[i].val)
            if res_i is not None and (mx is None or len(res_i) > len(mx)):
                mx = res_i

        if mx is None and len(self.nodes[cur].children) > 1:
            mx = cur_s

        return mx


s = input()
tree = Tree(s)
res = tree.find_longest_repeat()
print(res if res else 'nan')
