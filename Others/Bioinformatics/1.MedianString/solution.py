k = int(input())
data = []
while True:
    line = input()
    if not line:
        break
    data.append(line)

available = {'A', 'T', 'C', 'G'}


def get_hamming(s1: str, s2: str) -> int:
    if len(s1) != len(s2):
        raise Exception

    res = 0
    for c1, c2 in zip(s1, s2):
        if c1 != c2:
            res += 1
    return res


def get_min_hamming(s1: str, s2: str) -> int:
    if len(s1) < len(s2):
        raise Exception

    res = None
    for i in range(len(s1) - len(s2) + 1):
        score = get_hamming(s1[i : i + len(s2)], s2)
        if res is None or score < res:
            res = score

    return res


def get_median_string(data: list[str], k: int, available: set) -> str:

    def generate(depth: int, cur_s: str) -> dict:
        if depth == 0:
            score = sum(map(lambda s: get_min_hamming(s, cur_s), data))
            return {'score': score, 's': cur_s}

        best_score = None
        for c in available:
            cur_score = generate(depth - 1, cur_s + c)
            if best_score is None or cur_score['score'] < best_score['score']:
                best_score = cur_score

        return best_score

    return generate(k, '')['s']


print(get_median_string(data, k, available))
