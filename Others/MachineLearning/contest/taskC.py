import math
import numpy as np

N, M = map(int, input().split())

data_train = np.array([list(map(int, input().split())) for i in range(N)])
x_train = data_train[:, :-1]
y_train = data_train[:, -1]

x_test = list(map(int, input().split()))

dist_f_str = input()
kernel_str = input()
window_type = input()
window_param = int(input())


# distances
def euclidean(p1: np.ndarray, p2: np.ndarray) -> float:
    return np.sqrt(np.sum((p1 - p2) ** 2))


def manhattan(p1: np.ndarray, p2: np.ndarray) -> float:
    return sum(np.abs(p1 - p2))


def chebyshev(p1: np.ndarray, p2: np.ndarray) -> float:
    return max(np.abs(p1 - p2))


dist_f_map = {
    'euclidean': euclidean,
    'manhattan': manhattan,
    'chebyshev': chebyshev,
}


# kernel functions
def uniform(u: float) -> float:
    return 1 / 2 if abs(u) < 1 else 0


def triangular(u: float) -> float:
    return 1 - abs(u) if abs(u) < 1 else 0


def epanechnikov(u: float) -> float:
    return 3 / 4 * (1 - u**2) if abs(u) < 1 else 0


def quartic(u: float) -> float:
    return 15 / 16 * (1 - u**2)**2 if abs(u) < 1 else 0


def triweight(u: float) -> float:
    return 35 / 32 * (1 - u**2)**3 if abs(u) < 1 else 0


def tricube(u: float) -> float:
    return 70 / 81 * (1 - abs(u)**3)**3 if abs(u) < 1 else 0


def gaussian(u: float) -> float:
    return math.exp(u**2 / -2) / math.sqrt(2 * math.pi)


def cosine(u: float) -> float:
    return math.pi * math.cos(math.pi * u / 2) / 4 if abs(u) < 1 else 0


def logistic(u: float) -> float:
    return 1 / (math.exp(u) + 2 + math.exp(-u))


def sigmoid(u: float) -> float:
    return 2 / (math.pi * (math.exp(u) + math.exp(-u)))


kernel_map = {
    'uniform': uniform,
    'triangular': triangular,
    'epanechnikov': epanechnikov,
    'quartic': quartic,
    'triweight': triweight,
    'tricube': tricube,
    'gaussian': gaussian,
    'cosine': cosine,
    'logistic': logistic,
    'sigmoid': sigmoid,
}

dist_f = dist_f_map[dist_f_str]
kernel = kernel_map[kernel_str]

dist_x = np.apply_along_axis(lambda x: dist_f(x, x_test), 1, x_train)
ind = np.argsort(dist_x)

if window_type == 'variable':
    window_param = dist_x[ind[window_param]]

if window_param == 0:
    exact_ind = list(filter(lambda i: dist_x[i] == 0, range(N)))
    if len(exact_ind) > 0:
        y_pred = sum(map(lambda i: y_train[i], exact_ind)) / len(exact_ind)
    else:
        y_pred = sum(y_train) / len(y_train)
else:
    numerator, denominator = 0, 0
    for i in ind:
        w = kernel(dist_x[i] / window_param)
        numerator += y_train[i] * w
        denominator += w

    y_pred = numerator / denominator if denominator != 0 else sum(y_train) / len(y_train)

print(y_pred)
