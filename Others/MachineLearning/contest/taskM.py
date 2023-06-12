def intra_dist(lst: list) -> int:
    dist = 0
    for i, xi in enumerate(sorted(lst)):
        dist += xi * (2 * i + 1 - len(lst))
    return 2 * dist


k = int(input())
n = int(input())
y2x = dict()
x_all = []

for _ in range(n):
    x, y = map(int, input().split())

    if y not in y2x:
        y2x[y] = []
    y2x[y].append(x)
    x_all.append(x)

dist_all = intra_dist(x_all)
dist_in = sum(map(intra_dist, y2x.values()))
dist_out = dist_all - dist_in

print(dist_in)
print(dist_out)
