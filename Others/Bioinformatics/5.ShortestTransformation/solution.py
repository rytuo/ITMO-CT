from typing import Optional


def make_graph(g0: list[int], g1: list[int]) -> list[list[int]]:
    n = len(g1)
    graph = [[0, 0] for _ in range(2 * n)]
    g0 = [(-2 * g - 1, -2 * g - 2) if g < 0 else (2 * g - 2, 2 * g - 1) for g in g0]
    g1 = [(-2 * g - 1, -2 * g - 2) if g < 0 else (2 * g - 2, 2 * g - 1) for g in g1]

    for i in range(n - 1):
        _, i1 = g0[i]
        i2, _ = g0[i + 1]
        graph[i1][0] = i2
        graph[i2][0] = i1

        _, i1 = g1[i]
        i2, _ = g1[i + 1]
        graph[i1][1] = i2
        graph[i2][1] = i1

    graph[g0[-1][1]][0] = g0[0][0]
    graph[g0[0][0]][0] = g0[-1][1]

    graph[g1[-1][1]][1] = g1[0][0]
    graph[g1[0][0]][1] = g1[-1][1]

    return graph


def get_nontrivial_cycle(graph: list[list[int]]) -> Optional[tuple[int, int, int, int]]:
    for v0 in range(len(graph)):
        v1 = graph[v0][0]
        v2 = graph[v1][1]
        if v0 != v2:
            return v0, v1, v2, graph[v2][0]

    return None


def get_pretty_cycles(graph: list[list[int]]) -> list[list[int]]:
    n = len(graph) // 2
    visited = [False] * n
    cycles = []

    for i in range(n):
        if visited[i]:
            continue

        ind = 2 * i + 1
        v = i
        cycle = []
        while True:
            visited[v] = True
            cycle.append(v + 1 if ind % 2 != 0 else - v - 1)

            ind = graph[ind][0]
            v = ind // 2
            if v == i:
                break
            ind ^= 1

        cycles.append(cycle)

    return cycles


g0 = list(map(int, input().strip('()').split()))
g1 = list(map(int, input().strip('()').split()))

if len(g0) != len(g1):
    raise Exception

graph = make_graph(g0, g1)
history = [[g0]]
while True:
    cycle = get_nontrivial_cycle(graph)
    if cycle is None:
        break

    v0, v1, v2, v3 = cycle
    graph[v0][0] = v3
    graph[v3][0] = v0
    graph[v1][0] = v2
    graph[v2][0] = v1
    history.append(get_pretty_cycles(graph))

for genome in history:
    for s in genome:
        prettify = ' '.join(map(lambda c: f'+{c}' if c > 0 else f'{c}', s))
        print(f'({prettify})', end='')
    print()

