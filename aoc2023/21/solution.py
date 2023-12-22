import copy

with open("input") as input:
    field = [[x for x in line.strip()] for line in input.readlines()]
    field2 = copy.deepcopy(field)
    for i in range(len(field)):
        for j in range(len(field[0])):
            if field[i][j] == 'S':
                field[i][j] = 'O'
                field2[i][j] = '.'


    dirs = [(0,1), (0,-1), (1,0), (-1,0)]

    for iter in range(64):
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
        print("".join(line))
    print()
    for line in field2:
        for c in line:
            if c == 'O':
                sum += 1
        print("".join(line))
    print(sum)