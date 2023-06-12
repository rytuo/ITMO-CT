import numpy as np


def smape_grad(y_true: float, y_pred: float, xi: np.ndarray) -> np.ndarray:
    coeff = (np.sign(y_pred - y_true) * (np.abs(y_true) + np.abs(y_pred)) - np.abs(y_true - y_pred) * np.sign(y_pred)) / ((np.abs(y_true) + np.abs(y_pred))**2)
    return xi * coeff


n, m = map(int, input().split())
data = np.array([list(map(float, input().split())) for _ in range(n)])
x, y = data[:, :-1], data[:, -1]
a = np.array(list(map(float, input().split())))

x = np.apply_along_axis(lambda x1: np.append(x1, 1), 1, x)
y_p = x @ a

for xi, yi, yi_p in zip(x, y, y_p):
    print(*smape_grad(yi, yi_p, xi))
