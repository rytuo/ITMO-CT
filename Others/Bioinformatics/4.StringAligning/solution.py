import numpy as np


def get_blosum62() -> dict[str, dict[str, float]]:
    with open('blosum62.txt') as file:
        keys = list(filter(bool, file.readline().split()))
        m = {key: dict() for key in keys}
        for line in file:
            k1, *values = filter(bool, line.split())
            values = map(int, values)
            for k2, v in zip(keys, values):
                m[k1][k2] = v
                m[k2][k1] = v
        return m


def affine_gap(s: str, t: str, sigma=11, eps=1) -> tuple[int, str, str]:
    score_matrix = get_blosum62()

    I = np.zeros((len(s) + 1, len(t) + 1), dtype=int)
    M = np.zeros((len(s) + 1, len(t) + 1), dtype=int)
    D = np.zeros((len(s) + 1, len(t) + 1), dtype=int)

    for i in range(1, len(s) + 1):
        I[i][0] = M[i][0] = D[i][0] = - (sigma + eps * (i - 1))

    for i in range(1, len(t) + 1):
        I[0][i] = M[0][i] = D[0][i] = - (sigma + eps * (i - 1))

    for i in range(1, len(s) + 1):
        for j in range(1, len(t) + 1):
            I[i][j] = max(I[i - 1][j] - eps, M[i - 1][j] - sigma)
            D[i][j] = max(D[i][j - 1] - eps, M[i][j - 1] - sigma)
            M[i][j] = max(I[i][j], M[i - 1][j - 1] + score_matrix[s[i - 1]][t[j - 1]], D[i][j])

    score = M[len(s)][len(t)]

    i, j = len(s), len(t)
    new_s, new_t = '', ''
    while i > 0 and j > 0:
        v0 = I[i][j]
        v1 = M[i - 1][j - 1] + score_matrix[s[i - 1]][t[j - 1]]
        v2 = D[i][j]
        if v0 >= v1 and v0 >= v2:
            new_s += s[i - 1]
            new_t += '-'
            i -= 1
        elif v2 >= v0 and v2 >= v1:
            new_s += '-'
            new_t += t[j - 1]
            j -= 1
        else:
            new_s += s[i - 1]
            new_t += t[j - 1]
            i -= 1
            j -= 1

    new_s = new_s[::-1]
    new_t = new_t[::-1]

    return score, new_s, new_t


data = []
while True:
    s = input().strip()
    if not s:
        break
    data.append(s)

score, s, t = affine_gap(data[0], data[1])
print(score)
print(s)
print(t)
