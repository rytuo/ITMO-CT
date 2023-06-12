import numpy as np

n = int(input())
data = np.array([list(map(int, input().split())) for _ in range(n)])
x, y = data[:, 0], data[:, 1]

x_mean = np.mean(x)
y_mean = np.mean(y)

numerator = np.sum((x - x_mean) * (y - y_mean))
denominator = np.sqrt(np.sum((x - x_mean)**2) * np.sum((y - y_mean)**2))

eps = 10**-6

correlation = numerator / denominator if np.abs(denominator) > eps else 0

print(correlation)
