with open("input") as input:
    lines = [line.strip() for line in input.readlines()]
    dirs = {'R': (0,1), 'L': (0,-1), 'U':(-1, 0), 'D': (1,0)}
    pos = (0,0)
    field = {}
    prev = (0,0)
    for line in lines:
        dir, steps, color = line.split()
        steps = range(int(steps))
        for i in steps:
            pos = (pos[0] + dirs[dir][0], pos[1] + dirs[dir][1])
            field[pos] = dir
            if dir == 'U' or dir == 'D':
                field[prev] = dir
            prev = pos

    print(field)
    hMin = min([x[0] for x in field])
    hMax = max([x[0] for x in field])
    wMin = min([x[1] for x in field])
    wMax = max([x[1] for x in field])
    w = wMax - wMin + 1
    h = hMax - hMin + 1
    print(w,h)
    s = 0
    for i in range(h):
        inside = False
        for j in range(w):
            if field.get((i,j), None) == 'U' or (i,j) == (0,0):
                s += 1
                inside = True
            if field.get((i,j), None) == 'D':
                s += 1
                inside = False
            if field.get((i,j), None) != 'U' and field.get((i,j), None) != 'D':
                if inside or (i,j) in field:
                    s += 1
                else:
                    print((i,j))
                
    print(s)