K = int(input())
confusion_matrix = [list(map(int, input().split())) for i in range(K)]

critical_values = [[0, 0, 0] for i in range(K)]

for actual_ind, predicted in enumerate(confusion_matrix):
    for predicted_ind, predicted_val in enumerate(predicted):
        if actual_ind == predicted_ind:
            critical_values[actual_ind][0] = predicted_val
            continue

        critical_values[predicted_ind][1] += predicted_val
        critical_values[actual_ind][2] += predicted_val

total_c = list(map(lambda val: val[0] + val[2], critical_values))
total = sum(total_c)

def scalar(arr1, arr2):
    res = []
    for x1, x2 in zip(arr1, arr2):
        res.append(x1 * x2)
    return res


macro_f = 0
tp, fp, fn = 0, 0, 0
pr, re = 0, 0

for i in range(K):  # macro f_score
    true_positive, false_positive, false_negative = critical_values[i]
    tp += true_positive * total_c[i]
    fp += false_positive * total_c[i]
    fn += false_negative * total_c[i]

    if true_positive + false_positive == 0:
        precision = 0
    else:
        precision = true_positive / (true_positive + false_positive)

    if true_positive + false_negative == 0:
        recall = 0
    else:
        recall = true_positive / (true_positive + false_negative)

    pr += precision * total_c[i]
    re += recall * total_c[i]

    if precision + recall != 0:
        macro_f += total_c[i] * 2 * precision * recall / (precision + recall)

tp /= total
fp /= total
fn /= total
if tp + fp == 0:
    precision = 0
else:
    precision = tp / (tp + fp)
if tp + fn == 0:
    recall = 0
else:
    recall = tp / (tp + fn)
if precision + recall == 0:
    micro_f = 0
else:
    micro_f = 2 * precision * recall / (precision + recall)

pr /= total
re /= total
if pr + re == 0:
    weighted_f = 0
else:
    weighted_f = 2 * pr * re / (pr + re)

macro_f /= total

print(micro_f, weighted_f, macro_f)
