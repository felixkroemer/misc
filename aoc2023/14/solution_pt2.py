with open("input") as input:
    lines = [[x for x in line.strip()] for line in input.readlines()]

    m = {}
    iter = 0
    base = 0
    found = False
    remaining = -1
    while(not found or remaining > 0):
        for dir in range(4):
            if dir == 0 or dir == 2:
                for i in range(len(lines[0])):
                    support = 0 if dir == 0 else len(lines) - 1
                    for j in range(len(lines)) if dir == 0 else reversed(range(len(lines))):
                        if lines[j][i] == '.':
                            continue
                        if lines[j][i] == '#':
                            support = j + (1 if dir == 0 else -1) 
                        if lines[j][i] == 'O':
                            lines[j][i] = '.'
                            lines[support][i] = 'O'
                            support += 1 if dir == 0 else -1
            if dir == 1 or dir == 3:
                for i in range(len(lines)):
                    support = 0 if dir == 1 else len(lines) - 1
                    for j in range(len(lines[0])) if dir == 1 else reversed(range(len(lines))):
                        if lines[i][j] == '.':
                            continue
                        if lines[i][j] == '#':
                            support = j + (1 if dir == 1 else -1) 
                        if lines[i][j] == 'O':
                            lines[i][j] = '.'
                            lines[i][support] = 'O'
                            support += 1 if dir == 1 else -1

        s = "".join(["".join(x) for x in lines])

        if remaining:
            remaining -= 1

        if not found:
            if s in m:
                diff = iter - m[s]
                base = m[s]
                found = True
                remaining = ((1000000000 - base) % diff) - 1
            else:
                m[s] = iter

        iter += 1

    sum = 0
    for i in range(len(lines[0])):
        support = 0
        for j in range(len(lines)):
            if lines[j][i] == 'O':
                sum += len(lines) - j
    print(sum)
