def baum_welch(n, x, alphabet, states, transition, emission):
    T = len(x)
    for i in range(n):
        # forward
        alpha = [{s: emission[s][x[0]] / len(states) for s in states}]
        for t in range(T - 1):
            alpha.append({s_j: emission[s_j][x[t + 1]] * sum([alpha[t][s_i] * transition[s_i][s_j] for s_i in states]) for s_j in states})

        # backward
        beta: list[dict[str, float]] = [{s: 1 for s in states} for _ in range(T)]
        for t in range(T - 2, -1, -1):
            beta[t] = {s_i: sum([beta[t + 1][s_j] * transition[s_i][s_j] * emission[s_j][x[t + 1]] for s_j in states]) for s_i in states}

        gamma = []
        for t in range(T):
            gamma_t = {s_i: alpha[t][s_i] * beta[t][s_i] for s_i in states}
            sum_gamma_t = sum(gamma_t.values())
            for k in gamma_t.keys():
                gamma_t[k] /= sum_gamma_t
            gamma.append(gamma_t)


        eps = []
        for t in range(T - 1):
            eps.append({s1: {s2: 0 for s2 in states} for s1 in states})
            sum_eps_t = 0
            for s_i in states:
                for s_j in states:
                    eps[t][s_i][s_j] = alpha[t][s_i] * transition[s_i][s_j] * beta[t + 1][s_j] * emission[s_j][x[t + 1]]
                    sum_eps_t += eps[t][s_i][s_j]

            for i in states:
                for j in states:
                    eps[t][i][j] /= sum_eps_t

        new_t = {s: {} for s in states}
        for s_i in states:
            denominator = 0
            for t in range(T - 1):
                denominator += gamma[t][s_i]

            for s_j in states:
                numerator = 0
                for t in range(T - 1):
                    numerator += eps[t][s_i][s_j]
                new_t[s_i][s_j] = numerator / denominator

        new_e = {s: {} for s in states}
        for s_i in states:
            denominator = 0
            for t in range(T):
                denominator += gamma[t][s_i]

            for o_k in alphabet:
                numerator = 0
                for t in range(T):
                    if x[t] == o_k:
                        numerator += gamma[t][s_i]
                new_e[s_i][o_k] = numerator / denominator

        transition = new_t
        emission = new_e

    return transition, emission


n = int(input())
input()
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

transition, emission = baum_welch(n, x, alphabet, states, transition, emission)

print(*states)
for i in states:
    print(i, ' '.join(map(lambda k: "{:.3f}".format(transition[i][k]), states)))

print('--------')

print(*alphabet)
for i in states:
    print(i, ' '.join(map(lambda k: "{:.3f}".format(emission[i][k]), alphabet)))
