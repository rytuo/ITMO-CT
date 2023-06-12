import numpy as np


def viterbi(
        x: str,
        states: list[str],
        transition: dict[str, dict[str, float]],
        emission: dict[str, dict[str, float]]
) -> str:

    s = [[(1 / len(states)) * emission[k][x[0]] for k in states]]

    for xi in x[1:]:
        weights = []
        for i in states:
            weights.append(max([s[-1][l] * transition[states[l]][i] * emission[i][xi] for l in range(len(states))]))
        s.append(weights)

    state = states[np.argmax(s[len(s) - 1])]
    path = [state]
    for i in range(len(s) - 1)[::-1]:
        ps = [s[i][j] * transition[states[j]][state] for j in range(len(states))]
        state = states[np.argmax(ps)]
        path.append(state)

    return ''.join(path[::-1])


x = input().strip()
input()
alphabet = list(filter(bool, input().strip().split()))
input()
states = list(filter(bool, input().strip().split()))
input()
keys = list(filter(bool, input().strip().split()))
transition = {c: {} for c in states}
for i in range(len(states)):
    key, *vals = filter(bool, input().strip().split())
    vals = list(map(float, vals))
    for k, v in zip(keys, vals):
        transition[key][k] = v
input()
keys = list(filter(bool, input().strip().split()))
emission = {c: {} for c in states}
for i in range(len(states)):
    key, *vals = filter(bool, input().strip().split())
    vals = list(map(float, vals))
    for k, v in zip(keys, vals):
        emission[key][k] = v

print(viterbi(x, states, transition, emission))
