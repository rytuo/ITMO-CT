fin = open('input.txt', 'r')
fout = open('output.txt', 'w')

class matrixError(Exception) :
    pass

def parseNext() :
    k = fin.read(1)
    minus = 0
    while k == ' ' or k == '\n' or k == '-':
        if k == '-' :
            minus = 1
        k = fin.read(1)
    q = 0
    fl = 0
    while k != ' ' and k != '\n' and k != '':
        if k == '.' :
            fl = -1
        else :
            if fl == 0:
                q = 10*q + int(k)
            else :
                q += int(k) * (10 ** fl)
                fl -= 1      
        k = fin.read(1)
    if minus == 1 :
        q = -q
    if fl != 0:
        q = round(q, abs(fl + 1))
    return q

def sum(k, l):
    if len(k)==len(l) and len(k[0])==len(l[0]) :
        for i in range(len(k)):
            for j in range(len(k[i])):
                k[i][j] += l[i][j]
        return k
    else :
        raise matrixError

def const_multiply(a, k):
    for i in range(len(k)):
        for j in range(len(k[i])):
            k[i][j] *= a
    return k

def multiply(k, l):
    if len(k[0])==len(l) :
        c = [[0 for x in range(len(l[0]))] for y in range(len(k))]
        for i in range(len(c)):
            for j in range(len(c[i])):
                for t in range(len(l)):
                    c[i][j] += k[i][t] * l[t][j]
        return c
    else :
        raise matrixError

def transpose(k):
    c = [[0 for x in range(len(k))] for y in range(len(k[0]))]
    for i in range(len(c)):
        for j in range(len(c[0])):
             c[i][j] = k[j][i]
    return c

def new_matrix():
    n = parseNext()
    m = parseNext()
    if n > 0 and m > 0 :
        T = [[0 for x in range(m)] for y in range(n)]
        for i in range(n):
            for j in range(m):
                T[i][j] = parseNext()
        return T
    else :
        raise matrixError

a = parseNext()
b = parseNext()
try :
    A = new_matrix()
    B = new_matrix()
    C = new_matrix()
    D = new_matrix()
    F = new_matrix()

    X = sum(multiply(multiply(C, transpose(sum(const_multiply(a, A), const_multiply(b, transpose(B))))), D), const_multiply(-1, F))
    fout.write ('1')
    fout.write ('\n')
    fout.write(str(len(X)))
    fout.write(' ')
    fout.write(str(len(X[0])))
    fout.write ('\n')
    for i in range(len(X)):
        for j in range(len(X[0])):
            if i < len(X) - 1 or j < len(X[0]) - 1 :
                fout.write(str(X[i][j]))
                fout.write(' ')
            else : fout.write(str(X[i][j]))
        if  i < len(X)-1 :
            fout.write('\n')
except matrixError as e:
    fout.write('0')
finally :
    fin.close()
    fout.close()