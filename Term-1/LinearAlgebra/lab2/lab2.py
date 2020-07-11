import math

def vectorMulty(a, b) :
    x = a[1]*b[2] - a[2]*b[1]
    y = a[2]*b[0] - a[0]*b[2]
    z = a[0]*b[1] - a[1]*b[0]
    return [x, y, z]

def vectorMinus(a, b) :
    for i in range(3) :
        a[i] = a[i] - b[i]
    return a

def scalMulti(a, b) :
    s = 0
    for i in range(3) :
        s += a[i] * b[i]
    return s

def vectorLength(a) :
    s = 0
    for i in range(3) :
        s += a[i] * a[i]
    return math.sqrt(s)

def main() :
    fin = open('input.txt', 'r')
    fout = open('output.txt', 'w')


    Vx, Vy, Vz = [float(i) for i in fin.readline().split()]
    Ax, Ay, Az = [float(i) for i in fin.readline().split()]
    Mx, My, Mz = [float(i) for i in fin.readline().split()]
    Wx, Wy, Wz = [float(i) for i in fin.readline().split()]

    v = [Vx, Vy, Vz]
    a = [Ax, Ay, Az]
    m = [Mx, My, Mz]
    w = [Wx, Wy, Wz]
    normal = [0., 0., 1.]

    cannon1 = vectorMulty(a, normal)
    cannon2 = vectorMulty(normal, a)

    aim = vectorMinus(w, v)

    Fi1 = math.acos(scalMulti(aim, cannon1) / (vectorLength(aim) * vectorLength(cannon1)))
    Fi2 = math.acos(scalMulti(aim, cannon2) / (vectorLength(aim) * vectorLength(cannon2)))
    height = math.acos(1 / math.sqrt(Mx ** 2 + My ** 2 + 1))
    heightAngle = scalMulti(m, cannon1) / vectorLength(cannon1)
    if (heightAngle == 0) :
        height = 0
    elif (heightAngle > 0) :
        height = -height
    height = height * 180 / math.pi
    sign = math.acos(scalMulti(a, aim) / (vectorLength(a) * vectorLength(aim)))
    
    if Fi1 < math.pi / 3 :
        fout.write("-1" + "\n")
        Fi1 = Fi1 * 180 / math.pi
        if (sign < math.pi / 2) :
            fout.write(str(Fi1) + "\n")
        else : fout.write(str(-Fi1) + "\n")
        fout.write(str(height) + "\n")
        fout.write("!!!")
    elif Fi2 < math.pi / 3 :
        fout.write("1" + "\n")
        Fi2 = Fi2 * 180 / math.pi
        if (sign < math.pi / 2) :
            fout.write(str(Fi2) + "\n")
        else : fout.write(str(-Fi2) + "\n")
        fout.write(str(height) + "\n")
        fout.write("!!!")
    else :
        fout.write("0\n")
        fout.write("!!!") 
    
    fin.close()
    fout.close()

main()