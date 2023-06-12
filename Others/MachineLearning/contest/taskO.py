k1, k2 = map(int, input().split())
n = int(input())
p1, p2, sparse_frequency_table = dict(), dict(), dict()

for _ in range(n):
    x1, x2 = map(int, input().split())

    if x1 not in p1:
        p1[x1] = 0
    p1[x1] += 1 / n

    if x2 not in p2:
        p2[x2] = 0
    p2[x2] += 1 / n

    if x1 not in sparse_frequency_table:
        sparse_frequency_table[x1] = dict()
    if x2 not in sparse_frequency_table[x1]:
        sparse_frequency_table[x1][x2] = 0

    sparse_frequency_table[x1][x2] += 1

criteria = n
for x1 in sparse_frequency_table.keys():
    for x2 in sparse_frequency_table[x1].keys():
        practical_freq = sparse_frequency_table[x1][x2]
        expected_freq = p1[x1] * p2[x2] * n
        diff = practical_freq - expected_freq
        criteria += diff**2 / expected_freq - expected_freq

print(criteria)
