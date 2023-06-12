def find_eulerian_cycle(graph):
    edges = set()
    for a in graph.keys():
        for b in graph[a]:
            edges.add((a, b))

    def find_next(v):
        for out in graph[v]:
            if (v, out) in edges:
                return out
        return None

    def find_branch(cycle):
        pos = 0
        for node in cycle:
            succ = find_next(node)
            if succ is not None:
                return pos, node
            pos += 1

    def find_cycle(cycle, v):
        succ = find_next(v)
        while succ is not None:
            cycle.append(succ)
            edges.remove((v, succ))
            v = succ
            succ = find_next(v)
        return cycle

    v = edges.pop()
    edges.add(v)
    v = v[0]
    cycle = find_cycle([v], v)
    while len(edges) > 0:
        (pos, branch) = find_branch(cycle)
        cycle = cycle[pos:] + cycle[1:pos] + cycle[pos:pos + 1]
        cycle = find_cycle(cycle, cycle[len(cycle) - 1])
    return cycle


def kdmers2str(k, d, patterns):

    in_out = {}
    graph = {}
    for h, t in patterns:
        head, tail = (h[:-1], t[:-1]), (h[1:], t[1:])
        graph.setdefault(head, [])
        graph[head].append(tail)

        in_out.setdefault(head, [0, 0])
        in_out.setdefault(tail, [0, 0])
        in_out[head][1] += 1
        in_out[tail][0] += 1
    for kd in graph.keys():
        graph[kd].sort()

    start = []
    for candidate in graph.keys():
        inn, out = in_out[candidate]
        if inn < out:
            start.append(candidate)

    finish = []
    for candidate in in_out.keys():
        inn, out = in_out[candidate]
        if inn > out:
            finish.append(candidate)

    if len(start) != 1 or len(finish) != 1:
        raise Exception('There is no Eulerian path in this graph')

    graph[finish[0]] = [start[0]]
    path = find_eulerian_cycle(graph)[:-1]

    for i in range(len(path)):
        path = path[1:] + [path[0]]
        if path[0] == start[0] and path[-1] == finish[0]:
            break

    head_res = [path[i][0] for i in range(0, len(path), k - 1)]
    overlap = len(path) + k - 2 - len(head_res) * (k - 2)
    if overlap > 0:
        head_res.append(path[-1][k - overlap - 1:k - 1])
    head_res = ''.join(head_res[:-1])

    tail_res = path[-1][1]
    i = -2
    while len(head_res) + len(tail_res) < len(patterns) + 2 * k + d - 1:
        _, tail = path[i]
        tail_res = tail[0] + tail_res
        i -= 1

    return head_res + tail_res


def get_input(input_f):
    k, d = map(int, input_f().strip().split())
    patterns = []
    while True:
        s = input_f().strip()
        if not s:
            break
        patterns.append(s.split('|'))
    return k, d, patterns


with open('input.txt') as f:
    k, d, patterns = get_input(f.readline)
# k, d, patterns = get_input(input)
actual = kdmers2str(k, d, patterns)
print(actual)
# with open('output.txt') as f:
#     expected = f.readline().strip()

# print(actual == expected)
