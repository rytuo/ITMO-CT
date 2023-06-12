k = int(input())
n = int(input())

x2y = dict()
sum_y2 = 0

for _ in range(n):
    x, y = map(int, input().split())
    if x not in x2y:
        x2y[x] = []
    x2y[x].append(y)
    sum_y2 += y**2

sc = 0
for x in x2y.keys():
    y = x2y[x]
    sc += (sum(y))**2 / (len(y) * n)

cond_var = sum_y2 / n - sc
print(cond_var)
