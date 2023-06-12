def add_v(i, k, x, D, T, nextV) -> tuple[int, int]:
    """ the (potentially new) node in T at distance x from i on the path between i and k """
    if x == 0:
        return i, nextV

    path = []

    def dfs(cur: int, prev: int) -> bool:
        path.append(cur)
        if cur == k:
            return True

        for i in T[cur].keys():
            if i == prev:
                continue
            if dfs(i, cur):
                return True

        path.pop()
        return False

    dfs(i, -1)

    cur_w = 0
    for ind in range(len(path) - 1):
        l0, l1 = path[ind], path[ind + 1]
        w = T[l0][l1]
        if x == cur_w + w:
            return l1, nextV
        if cur_w < x < cur_w + w:
            v = nextV

            T[l0].pop(l1)
            T[l1].pop(l0)

            T[v] = {}
            T[v][l0] = x - cur_w
            T[v][l1] = cur_w + w - x

            T[l0][v] = x - cur_w
            T[l1][v] = cur_w + w - x
            return v, nextV + 1
        cur_w += w

    raise Exception


def additive_phylogeny(D, n, nextV) -> tuple[dict[int, dict[int, int]], int]:
    if n == 2:
        return {0: {1: D[0][1]}, 1: {0: D[1][0]}}, nextV

    limb_length = None
    for i in range(n - 1):
        for k in range(i):
            dist = D[i][n - 1] + D[n - 1][k] - D[i][k]
            if limb_length is None or limb_length > dist:
                limb_length = dist
    limb_length //= 2

    for j in range(n - 1):
        D[j][n - 1] -= limb_length
        D[n - 1][j] = D[j][n - 1]

    def get_ik():
        for i in range(n - 1):
            for k in range(n - 1):
                if D[i][k] == D[i][n - 1] + D[n - 1][k]:
                    return i, k

    i, k = get_ik()
    x = D[i][n - 1]
    D = [row[:-1] for row in D[:-1]]

    T, nextV = additive_phylogeny(D[:], n - 1, nextV)
    v, nextV = add_v(i, k, x, D, T, nextV)

    T.setdefault(v, {})
    T[v][n - 1] = limb_length
    T[n - 1] = {}
    T[n - 1][v] = limb_length
    return T, nextV


n = int(input().strip())
D = [[0 for _ in range(n)] for _ in range(n)]
for i in range(n):
    row = map(int, input().strip().split())
    for j, v in enumerate(row):
        D[i][j] = v

T = additive_phylogeny(D, n, n)[0]

for i in T.keys():
    for j in T[i].keys():
        print(f'{i}->{j}:{T[i][j]}')
