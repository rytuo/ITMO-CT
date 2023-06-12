file = open('rosalind_ba9h.txt')


s = file.readline().strip() + '$'
n = len(s)

cycles = [s]
for _ in range(n - 1):
    s = s[1:] + s[0]
    cycles.append(s)

cycles = sorted(enumerate(cycles), key=lambda c: c[1])
letters = set(s)

C = {}
for c in letters:
    i = 0
    while i < len(cycles) and cycles[i][1][0] < c:
        i += 1
    C[c] = i

BW = [c[1][-1] for c in cycles]
O = {c: [0 for _ in range(n)] for c in letters}
O[BW[0]][0] = 1
for i in range(1, n):
    for c in letters:
        O[c][i] = O[c][i - 1]
    O[BW[i]][i] += 1


while True:
    t = file.readline().strip()
    if not t:
        break
    if len(set(t).difference(letters)):
        continue

    rl, rh = 0, n - 1
    for c in t[::-1]:
        rl = C[c] + (O[c][rl - 1] if rl > 0 else 0)
        rh = C[c] + O[c][rh] - 1

    for i in range(rl, rh + 1):
        ind = cycles[i][0]
        print(ind)
