class Node:
    def __init__(self, val, children):
        self.val = val
        self.children = children

    def __str__(self):
        return self.val


class Tree:
    def __init__(self, s):
        self.nodes: list[Node] = []
        self.node2ind: list[list[int]] = []
        self.__build_tree(s)

    def __build_tree(self, s):
        from os.path import commonprefix
        self.nodes.append(Node('', []))
        self.node2ind.append([])

        for i in range(0, len(s)):
            cur_s = s[i:]
            cur_node = 0

            while True:
                prefix = commonprefix((cur_s, self.nodes[cur_node].val))
                cur_s = cur_s[len(prefix):]

                if prefix == self.nodes[cur_node].val:
                    self.node2ind[cur_node].append(i)
                    if not cur_s:
                        break

                    next_node = None
                    for child_ind in self.nodes[cur_node].children:
                        if cur_s and self.nodes[child_ind].val[0] == cur_s[0]:
                            next_node = child_ind
                            break

                    if next_node is None:
                        self.nodes[cur_node].children.append(len(self.nodes))
                        self.nodes.append(Node(cur_s, []))
                        self.node2ind.append([i])
                    else:
                        cur_node = next_node
                        continue
                else:
                    old_node = self.nodes[cur_node]
                    old_node.val = old_node.val[len(prefix):]
                    old_ind = self.node2ind[cur_node]

                    self.nodes[cur_node] = Node(prefix, [len(self.nodes)])
                    self.node2ind[cur_node] = [*old_ind, i]

                    self.nodes.append(old_node)
                    self.node2ind.append(old_ind)

                    if cur_s:
                        self.nodes[cur_node].children.append(len(self.nodes))
                        self.nodes.append(Node(cur_s, []))
                        self.node2ind.append([i])
                break

    def approximate_matches(self, remain_s, remain_d, cur=0):
        mismatches = 0
        for c1, c2 in zip(self.nodes[cur].val, remain_s):
            if c1 != c2:
                mismatches += 1

        remain_d -= mismatches
        if remain_d < 0:
            return []

        remain_s = remain_s[len(self.nodes[cur].val):]
        if not remain_s:
            return self.node2ind[cur]

        matches = []
        for i in self.nodes[cur].children:
            matches.extend(self.approximate_matches(remain_s, remain_d, i))
        return matches


s = input()
patterns = input().strip().split()
d = int(input())

tree = Tree(s)
res = []
for pattern in patterns:
    res.extend(tree.approximate_matches(pattern, d))

res = sorted(res)
print(*res)
