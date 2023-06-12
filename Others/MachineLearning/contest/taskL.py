import numpy as np

n = int(input())
data = np.array([list(map(int, input().split())) for _ in range(n)])

x_sorted_args = np.argsort(data[:, 0])
y_sorted_args = np.argsort(data[:, 1])

rank_x = np.zeros(n)
rank_y = np.zeros(n)

for rank, i in enumerate(x_sorted_args):
    rank_x[i] = rank

for rank, i in enumerate(y_sorted_args):
    rank_y[i] = rank

r = 1 - 6 * sum((rank_x - rank_y)**2) / (n * (n**2 - 1))
print(r)
