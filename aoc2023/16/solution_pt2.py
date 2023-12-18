def next(x, y, dir):
    if dir == 0:
        return (x + 1, y)
    if dir == 1:
        return (x - 1, y)
    if dir == 2:
        return (x, y - 1)
    if dir == 3:
        return (x, y + 1)

def fun(start, lines):
    dirs = [start]
    used = []
    found = []
    for x in range(len(lines)):
        found.append([False] * len(lines[0]))
    while dirs:
        (x,y), dir = dirs.pop()
        if((y,x,dir) in used):
            continue
        if x < 0 or x >= len(lines[0]) or y < 0 or y >= len(lines):
            continue
        found[y][x] = True
        if (lines[y][x] == "." or (lines[y][x] == "-" and (dir == 0 or dir == 1)) or (lines[y][x] == "|" and (dir == 2 or dir == 3))):
            x, y = next(x, y, dir)
            dirs.append(((x,y), dir))
            continue
        if lines[y][x] == "|":
            used.append((y,x,dir))
            dirs.append(((x, y - 1), 2))
            dirs.append(((x, y + 1), 3))
        if lines[y][x] == "-":
            used.append((y,x,dir))
            dirs.append(((x + 1, y), 0))
            dirs.append(((x - 1, y), 1))
        if lines[y][x] == '/':
            used.append((y,x,dir))
            if dir == 0:
                dirs.append(((x, y - 1), 2))
            if dir == 1:
                dirs.append(((x, y + 1), 3))
            if dir == 2:
                dirs.append(((x + 1, y), 0))
            if dir == 3:
                dirs.append(((x - 1, y), 1))
        if lines[y][x] == '\\':
            used.append((y,x,dir))
            if dir == 0:
                dirs.append(((x, y + 1), 3))
            if dir == 1:
                dirs.append(((x, y - 1), 2))
            if dir == 2:
                dirs.append(((x - 1, y), 1))
            if dir == 3:
                dirs.append(((x + 1, y), 0))
    sum = 0
    for line in found:
        for c in line:
            sum += 1 if c else 0
    return sum

with open("input") as input:
    lines = [[x for x in line.strip()] for line in input.readlines()]
    m = 0
    for i in range(len(lines)):
        a = fun(((0,i), 0), lines)
        b = fun(((len(lines[0])-1, i), 1), lines)
        m = max([a,b,m])
    for i in range(len(lines[0])):
        a = fun(((i, 0), 3), lines)
        b = fun(((i, len(lines)-1), 2), lines)
        m = max([a,b,m])
    
    print(m)