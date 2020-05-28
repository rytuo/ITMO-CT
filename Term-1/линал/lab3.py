import math

input = open("input.txt", "r").read().split()
fout = open("output.txt", "w")

def convert(p) :
    A = (p[1][1] - p[0][1])*(p[2][2] - p[0][2]) - (p[2][1] - p[0][1])*(p[1][2] - p[0][2])
    B = (p[1][2] - p[0][2])*(p[2][0] - p[0][0]) - (p[1][0] - p[0][0])*(p[2][2] - p[0][2])
    C = (p[1][0] - p[0][0])*(p[2][1] - p[0][1]) - (p[1][1] - p[0][1])*(p[2][0] - p[0][0])
    D = (-1)*p[0][0]*A - p[0][1]*B - p[0][2]*C
    return [A, B, C, D]

def checkOut() :
    tr = 0
    Rm = 0
    for i in range(6) :
        if (face[i][0]*dir[0] + face[i][1]*dir[1] + face[i][2]*dir[2])!= 0 :   
            t = (-1)*(face[i][0] * point[0] + face[i][1] * point[1] + face[i][2] * point[2] + face[i][3]) / (face[i][0]*dir[0] + face[i][1]*dir[1] + face[i][2]*dir[2])
            if t > 0 :
                R = t * (math.sqrt(dir[0]*dir[0] + dir[1]*dir[1] + dir[2]*dir[2]))
                if (tr == 0 or R < Rm) :
                    I = i
                    Rm = R
                    tm = t
                    tr = 1
    if tr == 0 :
        return [-1, -1]
    else :
        return [I, Rm]

def check() :
    f = 1
    s = 1
    th = 1
    if dir[0] == 0 or mirror[Ind][0] == 0 :
        if dir[0] == mirror[Ind][0] :
            f = 0
        else :
            return 0
    else :
        a = mirror[Ind][0] / dir[0]
    if dir[1] == 0 or mirror[Ind][1] == 0 :
        if dir[1] == mirror[Ind][1] :
            s = 0
        else :
            return 0
    else :
        b = mirror[Ind][1] / dir[1]
    if dir[2] == 0 or mirror[Ind][2] == 0 :
        if dir[2] == mirror[Ind][2] :
            th = 0
        else :
            return 0
    else :
        c = mirror[Ind][2] / dir[2]

    if f == 1 :
        if s == 1 :
            if th == 1 :
                if a == b == c :
                    return 1
                else :
                    return 0
            else :
                if a == b :
                    return 1
                else :
                    return 0
        else :
            if th == 1 :
                if a == c :
                    return 1
                else :
                    return 0
            else :
                return 1
    else :
        if s == 1 :
            if th == 1 :
                if b == c :
                    return 1
                else :
                    return 0
            else :
                return 1
        else :
            return 1
                
        

A = [float(input[0]),float(input[1]),float(input[2])]
B = [float(input[3]),float(input[4]),float(input[5])]
C = [float(input[6]),float(input[7]),float(input[8])]
D = [float(input[9]),float(input[10]),float(input[11])]
face = [convert([A, B, C]), convert([B, C, D]), convert([A, B, [A[0] + D[0] - C[0], A[1] + D[1] - C[1], A[2] + D[2] - C[2]]]), convert([C, D, [C[0] + A[0] - B[0], C[1] + A[1] - B[1], C[2] + A[2] - B[2]]]), convert([A, [A[0] + C[0] - B[0], A[1] + C[1] - B[1], A[2] + C[2] - B[2]], [A[0] + D[0] - C[0], A[1] + D[1] - C[1], A[2] + D[2] - C[2]]]), convert([D, [D[0] + B[0] - C[0], D[1] + B[1] - C[1], D[2] + B[2] - C[2]], [D[0] + A[0] - B[0], D[1] + A[1] - B[1], D[2] + A[2] - B[2]]])]

dir = [float(input[12]),float(input[13]),float(input[14])]
point = [float(input[15]),float(input[16]),float(input[17])]

energy = int(input[18])

n = int(input[19])
mirror = [convert([[float(input[20+3*i+9*j]), float(input[21+3*i+9*j]), float(input[22+3*i+9*j])] for i in range(3)]) for j in range(n)]
prev = -1

while (energy > 0) :
    trigger = 0
    for i in range(n) :
        if (i != prev) :
            if (mirror[i][0]*dir[0] + mirror[i][1]*dir[1] + mirror[i][2]*dir[2] != 0) :
                t = (-1)*(mirror[i][0] * point[0] + mirror[i][1] * point[1] + mirror[i][2] * point[2] + mirror[i][3]) / (mirror[i][0]*dir[0] + mirror[i][1]*dir[1] + mirror[i][2]*dir[2])
                if t > 0 :
                    R = t * (math.sqrt(dir[0]*dir[0] + dir[1]*dir[1] + dir[2]*dir[2]))
                    if (trigger == 0 or R < Rmin) :
                        Ind = i
                        Rmin = R
                        tmin = t
                        trigger = 1
    k = checkOut()
    if k[0] == -1:
        fout.write("1\n{0}\n".format(energy))
        fout.write("{0} {1} {2}/n{3} {4} {5}".format(point[0], point[1], point[2], dir[0], dir[1], dir[2]))
        break
    elif trigger == 0 or (k[1] <= Rmin) :
        i = k[0]
        t = (-1) * (face[i][0] * point[0] + face[i][1] * point[1] + face[i][2] * point[2] + face[i][3]) / (face[i][0]*dir[0] + face[i][1]*dir[1] + face[i][2]*dir[2])
        x = point[0] + dir[0]*t
        y = point[1] + dir[1]*t
        z = point[2] + dir[2]*t
        fout.write("1\n{0}\n".format(energy))
        fout.write("{0} {1} {2}\n".format(x, y, z))
        fout.write("{0} {1} {2}".format(dir[0], dir[1], dir[2]))
        break
    else :
        prev = Ind
        point = [point[0] + dir[0]*tmin, point[1] + dir[1]*tmin, point[2] + dir[2]*tmin]
        if check() == 1:
            dir[0] = -dir[0]
            dir[1] = -dir[1]
            dir[2] = -dir[2]
        else :
            A = mirror[Ind][0]
            B = mirror[Ind][1]
            C = mirror[Ind][2]
            alph = (-1) * (dir[0]*A+dir[1]*B+dir[2]*C) / (math.sqrt((dir[0]*dir[0]+dir[1]*dir[1]+dir[2]*dir[2])*(A*A + B*B + C*C)))
            k = (alph * math.sqrt((dir[0]*dir[0]+dir[1]*dir[1]+dir[2]*dir[2]) / (A*A + B*B + C*C)))
            dir[0] = 2*A*k + dir[0]
            dir[1] = 2*B*k + dir[1]
            dir[2] = 2*C*k + dir[2]

    energy -= 1

if energy == 0 :
    fout.write("0\n")
    fout.write("{0} {1} {2}".format(point[0], point[1], point[2]))

fout.close()