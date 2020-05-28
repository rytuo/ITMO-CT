n, m = map(int, input().split())
a = [[0 for i in range(n + 1)] for j in range(m + 1)]
d = [[0 for i in range(n + 1)] for j in range(m + 1)]
ans = ""
for i in range(1, m + 1):
    tmp = list(map(int, input().split()))
    for j in range(1, n + 1):
        a[i][j] = tmp[j - 1]
for i in range(1, m + 1):
    for j in range(1, n + 1):
        if i > 1 and j > 1:
            if d[i][j - 1] >= d[i - 1][j]:
                d[i][j] = d[i][j - 1] + a[i][j]
            else:
                d[i][j] = d[i - 1][j] + a[i][j]
        else:
            if i > 1:
                d[i][j] = d[i - 1][j] + a[i][j]
            if j > 1:
                d[i][j] = d[i][j - 1] + a[i][j]
i = m
j = n
print(d[m][n])
while i > 1 or j > 1:
    if i > 1 and d[i][j] == d[i - 1][j] + a[i][j]:
        i -= 1
        ans += 'D'
    else:
        j -= 1
        ans += 'R'
print(ans[::-1])