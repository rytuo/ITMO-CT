N, M, K = map(int, input().split())
data = list(map(int, input().split()))

m1_stat = [0 for i in range(M)]
for x in data:
    m1_stat[x - 1] += 1

acc = 0
m1_to_k = [0 for i in range(M)]
for m1, stat in enumerate(m1_stat):
    m1_to_k[m1] = acc
    acc = (acc + stat) % K

res = [[] for i in range(K)]
for i, x in enumerate(data):
    k = m1_to_k[x - 1]
    res[k].append(i + 1)
    m1_to_k[x - 1] = (k + 1) % K

for arr in res:
    print(len(arr), *arr)
