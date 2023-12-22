from copy import deepcopy

with open("input") as input:
    a = [[x for x in line.strip()] for line in input.readlines()]
    b = deepcopy(a)
    for i in range(len(a)):
        for j in range(len(a[0])):
            if a[i][j] == 'S':
                a[i][j] = 'O'
                b[i][j] = '.'

    k = 5
    c = []
    d = []
    for i in range(len(a)):
        line = []
        line2 = []
        for j in range(k):
            line.extend(deepcopy(a[i]) if j == k//2 else deepcopy(b[i]))
            line2.extend(deepcopy(b[i]))
        c.append(line)
        d.append(line2)
    
    field = []
    field2 = []
    for i in range(k):
        field.extend(deepcopy(c) if i == k//2 else deepcopy(d))
        field2.extend(deepcopy(d))


    dirs = [(0,1), (0,-1), (1,0), (-1,0)]
    prev = 0
    k = 1
    for iter in range(131 * k+ 65):
        s = field if iter % 2 == 0 else field2
        t = field2 if iter % 2 == 0 else field
        for i in range(len(s)):
            for j in range(len(s[0])):
                if s[i][j] == 'O':
                    for dir in dirs:
                        if i+dir[0] < 0 or j+dir[1] < 0 or i+dir[0] >= len(field) or j+dir[1] >= len(field[0]):
                            continue
                        if t[i+dir[0]][j+dir[1]] != '#':
                            t[i+dir[0]][j+dir[1]] = 'O'
                    s[i][j] = '.'
        sum = 0
        for line in field:
            for c in line:
                if c == 'O':
                    sum += 1
        for line in field2:
            for c in line:
                if c == 'O':
                    sum += 1

        print(iter, sum, sum - prev)
        prev = sum

    #quit()
    sum = 0
    for line in field:
        for c in line:
            if c == 'O':
                sum += 1
        print("".join(line))
    print("-----------------------")
    
    for line in field2:
        for c in line:
            if c == 'O':
                sum += 1
        print("".join(line))
    print(sum)
    
# 65 = 3703 
# 196 = 32712
# 327 = 90559
# y = 14419x2 + 14590x + 3703
# 26501365 // 131 = 202300