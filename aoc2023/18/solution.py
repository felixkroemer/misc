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
    s = 0
    for i in range(hMin, hMax + 1):
        inside = False
        dir = None
        for j in range(wMin, wMax + 1):
            if field.get((i,j), None) == 'U' or (i,j) == (0,0):
                dir = 'U' if dir == None else dir
                inside = True if dir == 'U' else False
            if field.get((i,j), None) == 'D':
                dir = 'D' if dir == None else dir
                inside = True if dir == 'D' else False
            if inside or (i,j) in field:
                s += 1
    print(s)