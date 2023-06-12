import math


# input
k = int(input())
a = list(map(int, input().split()))
alpha = int(input())
n = int(input())
c_train, w_train = [], []
for _ in range(n):
    c, l, *w = input().split()
    c_train.append(int(c))
    w_train.append(set(w))

m = int(input())
w_test = []
for _ in range(m):
    l, *w = input().split()
    w_test.append(set(w))


# calc all words
all_words = set()
for w in w_train:
    all_words.update(w)


# init
p_c = dict()
p_wc = dict()
for c in range(1, k + 1):
    p_c[c] = 0
    p_wc[c] = dict()
    for word in all_words:
        p_wc[c][word] = 0

# calc occurrences
for c, words in zip(c_train, w_train):
    p_c[c] += 1
    for w in words:
        p_wc[c][w] += 1

# calc probabilities
for c in p_wc.keys():
    for w in p_wc[c].keys():
        p_wc[c][w] = (p_wc[c][w] + alpha) / (p_c[c] + 2 * alpha)
    p_c[c] /= n


# predict
for words in w_test:
    p_pred = []

    for c in range(1, k + 1):
        if p_c[c] == 0:
            p = -math.inf
        else:
            p = math.log(a[c - 1] * p_c[c])

        for w in p_wc[c]:
            if w in words:
                p += math.log(p_wc[c][w])
            else:
                p += math.log(1 - p_wc[c][w])
        p_pred.append(p)

    p_pred_max = max(p_pred)
    p_pred = list(map(lambda p: math.exp(p - p_pred_max), p_pred))
    sum_p_pred = sum(p_pred)
    p_pred = list(map(lambda p: p / sum_p_pred, p_pred))

    print(*p_pred)
