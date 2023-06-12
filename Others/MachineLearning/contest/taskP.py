import math

k1, k2 = map(int, input().split())
n = int(input())

freq_x = dict()
freq_y = dict()
frequency_table = dict()

for _ in range(n):
    x, y = map(int, input().split())

    if x not in freq_x:
        freq_x[x] = 0
    freq_x[x] += 1

    if y not in freq_y:
        freq_y[y] = 0
    freq_y[y] += 1

    if x not in frequency_table:
        frequency_table[x] = dict()
    if y not in frequency_table[x]:
        frequency_table[x][y] = 0

    frequency_table[x][y] += 1


h = 0
for xi in frequency_table.keys():
    for yi in frequency_table[xi].keys():
        p_x = freq_x[xi] / n
        p_yi_xi = frequency_table[xi][yi] / freq_x[xi]
        h -= p_x * p_yi_xi * math.log(p_yi_xi)

print(h)
