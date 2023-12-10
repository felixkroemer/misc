import copy


def getDirections(lines, y, x):
    x1 = None
    y1 = None
    x2 = None
    y2 = None
    if lines[y][x] == 'F':
        y1 = y + 1
        x1 = x
        y2 = y
        x2 = x + 1
    if lines[y][x] == '.':
        return None
    if lines[y][x] == '7':
        y1 = y
        x1 = x - 1
        y2 = y + 1
        x2 = x
    if lines[y][x] == '|':
        y1 = y + 1
        x1 = x
        y2 = y - 1
        x2 = x
    if lines[y][x] == '-':
        y1 = y
        x1 = x + 1
        y2 = y
        x2 = x - 1
    if lines[y][x] == 'J':
        y1 = y - 1
        x1 = x
        y2 = y
        x2 = x - 1
    if lines[y][x] == 'L':
        y1 = y - 1
        x1 = x
        y2 = y
        x2 = x + 1
    if y1 == len(lines) or y2 == len(lines) or y1 < 0 or y2 < 0:
        return None
    if x1 == len(lines[0]) or x2 == len(lines[0]) or x1 < 0 or x2 < 0:
        return None
    else:
        return ((y1, x1), (y2, x2))

with open("input") as input:
    lines = [line.strip() for line in input.readlines()]
    y = [i for i,x in enumerate(lines) if 'S' in x][0]
    x = lines[y].find('S')
    visited = []
    prev = None
    longestPath = None
    for dir in ['l', 'r', 'u', 'd']:
        prev = (y,x)
        next = None
        path = []
        if dir == 'l':
            next = (y, x-1)
            if not (lines[y][x-1] == '-' or lines[y][x-1] == 'L' or lines[y][x-1] == 'F'):
                continue 
        if dir == 'r':
            next = (y, x+1)
            if not (lines[y][x+1] == '-' or lines[y][x+1] == '7' or lines[y][x+1] == 'J'):
                continue 
        if dir == 'u':
            next = (y-1, x)
            if not (lines[y-1][x] == '|' or lines[y-1][x] == '7' or lines[y-1][x] == 'F'):
                continue 
        if dir == 'd':
            next = (y+1, x)
            if not (lines[y+1][x] == '|' or lines[y+1][x] == 'L' or lines[y+1][x] == 'J'):
                continue  
        if next in visited:
            continue
        else:
            visited.append(next)
        print(dir)
        step = 0
        while(True):
            path.append(next)
            step += 1
            if(next[0] == len(lines) or next[0] < 0 or next[1] < 0 or next[1] == len(lines[0])):
                break
            if(lines[next[0]][next[1]] == 'S'):
                print(step / 2)
                longestPath = path if not longestPath or len(path) > len(longestPath) else longestPath 
                visited.append(prev)
                break
            n = getDirections(lines, next[0], next[1])
            if not n:
                break
            cur = prev
            prev = next
            next = n[0] if n[0] != cur else n[1]


    lines = [[x for x in line] for line in lines]
    longestPath.insert(0, (y,x))

    prev = longestPath[0]
    for e in longestPath[1:]:
        y = e[0]
        x = e[1]

        dir = None
        if prev[0] + 1 == y and prev[1] == x:
            dir = 'd'
        elif prev[0] - 1 == y and prev[1] == x:
            dir = 'u'
        elif prev[0] == y and prev[1] + 1 == x:
            dir = 'r'
        elif prev[0] == y and prev[1] - 1 == x:
            dir = 'l'
        
        print(prev, (y,x), dir)
        i = []
        o = []
        if lines[e[0]][e[1]] == 'F': #
            if dir == 'u': 
                i.append((y, x-1))
                i.append((y - 1, x))
            else:
                o.append((y, x-1))
                o.append((y - 1, x))
        if lines[e[0]][e[1]] == '7': #
            if dir == 'r': 
                i.append((y, x+1))
                i.append((y - 1, x))
            else:
                o.append((y, x+1))
                o.append((y - 1, x))
        if lines[e[0]][e[1]] == 'J': #
            if dir == 'd': 
                i.append((y, x+1))
                i.append((y + 1, x))
            else:
                o.append((y, x+1))
                o.append((y + 1, x))
        if lines[e[0]][e[1]] == 'L': #
            if dir == 'l': 
                i.append((y, x-1))
                i.append((y + 1, x))
            else:
                o.append((y, x-1))
                o.append((y + 1, x))
        if lines[e[0]][e[1]] == '|': #
            if dir == 'u': 
                i.append((y, x-1))
                o.append((y, x+1))
            else:
                o.append((y, x-1))
                i.append((y, x+1))
        if lines[e[0]][e[1]] == '-': #
            if dir == 'l': 
                i.append((y + 1, x))
                o.append((y - 1, x))
            else:
                o.append((y + 1, x))
                i.append((y - 1, x))

        for x in i:
            if x[0] >= 0 and x[0] < len(lines) and x[1] >= 0 and x[1] < len(lines[0]) and x not in longestPath:
                #print("i", x)
                lines[x[0]][x[1]] = 'I'
        for x in o:
            if x[0] >= 0 and x[0] < len(lines) and x[1] >= 0 and x[1] < len(lines[0]) and x not in longestPath:
                #print("o", x)
                lines[x[0]][x[1]] = 'O'

        prev = e

    for i, line in enumerate(lines):
        for j, c in enumerate(line):
            if c == 'I' or c == 'O':
                if i > 0:
                    if (i-1, j) not in longestPath:
                        lines[i - 1][j] = c
                if i < len(lines) - 1:
                    if (i+1, j) not in longestPath:
                        lines[i + 1][j] = c
                if j > 0:
                    if (i, j-1) not in longestPath:
                        lines[i][j-1] = c
                if j < len(lines[0]) - 1:
                    if (i, j+1) not in longestPath:
                        lines[i][j+1] = c

    sum = 0
    for line in lines:
        for c in line:
            if c == 'I':
                sum += 1

    for line in lines:
        print(''.join(line))
    
    print(sum)
