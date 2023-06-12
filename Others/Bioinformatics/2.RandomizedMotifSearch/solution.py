from random import randint
from collections import Counter
import numpy as np


s2i = {'A': 0, 'C': 1, 'G': 2, 'T': 3}


def get_profile(motifs, k):
    profile = np.zeros((4, k))
    size = len(motifs)
    for i in range(k):
        for j in range(size):
            profile[s2i[motifs[j][i]]][i] += 1
    profile += 1
    profile /= (size + 4)
    return profile


def score(motifs, k):
    cnt = k * len(motifs)
    for i in zip(*motifs):
        cnt -= int(Counter(i).most_common(1)[0][1])
    return cnt


def count_p(pattern, profile):
    cnt = 1
    for i in range(len(pattern)):
        cnt *= float(profile[s2i[pattern[i]]][i])
    return cnt


def find_best(text, profile):
    lt = len(text)
    maxVal = -1
    maxSeq = ''
    for i in range(lt - k + 1):
        pattern = text[i:i + k]
        if maxVal < count_p(pattern, profile):
            maxVal = count_p(pattern, profile)
            maxSeq = pattern

    return maxSeq


def randomized_motif_search(dna, k, t):
    motifs = []
    for s in dna:
        i = randint(0, len(s) - k)
        motifs.append(s[i:i+k])

    best_motifs = motifs
    while True:
        profile = get_profile(motifs, k)
        motifs = [find_best(s, profile) for s in dna]
        if score(motifs, k) < score(best_motifs, k):
            best_motifs = motifs
        else:
            return best_motifs


k, t = map(int, input().split())
dna = []
while True:
    s = input()
    if not s:
        break
    dna.append(s)

best = None
for _ in range(1000):
    cur = randomized_motif_search(dna, k, t)
    if best is None or score(cur, k) < score(best, k):
        best = cur

print(*best, sep='\n')
