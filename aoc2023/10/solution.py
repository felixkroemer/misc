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
    y = index = [i for i,x in enumerate(lines) if 'S' in x][0]
    x = lines[index].find('S')
    visited = []
    prev = None
    for dir in ['l', 'r', 'u', 'd']:
        prev = (y,x)
        next = None
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
            step += 1
            if(next[0] == len(lines) or next[0] < 0 or next[1] < 0 or next[1] == len(lines[0])):
                break
            if(lines[next[0]][next[1]] == 'S'):
                print(step / 2)
                visited.append(prev)
                break
            n = getDirections(lines, next[0], next[1])
            if not n:
                break
            cur = prev
            prev = next
            next = n[0] if n[0] != cur else n[1]


    


