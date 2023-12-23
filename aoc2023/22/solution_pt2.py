import queue

with open("input") as input:
    lines = [line.strip() for line in input.readlines()]
    i = 1
    objects = {}
    x,y,z = 12, 12, 350
    dirs = {'x': (1,0,0), 'y': (0,1,0), 'z': (0,0,1), 'n':(0,0,0)}
    base = [[0 for a in range(x)] for b in range(y)] 
    field = [[[0 for _ in range(z)] for _ in range(x)] for _ in range(y)]    
    for line in lines:
        start, end = line.split("~")
        x,y,z = start.split(",")
        xe, ye, ze = end.split(",")
        x = int(x)
        y = int(y)
        z = int(z)
        xe = int(xe)
        ye = int(ye)
        ze = int(ze)
        dir = 'n'
        length = 1
        field[x][y][z] = i
        if x != xe:
            dir = 'x'
            length = abs(x - xe) + 1
            for loc in range(x, x + length):
                field[loc][y][z] = i
        if y != ye:
            dir = 'y'
            length = abs(y - ye) + 1
            for loc in range(y, y + length):
                field[x][loc][z] = i
        if z != ze:
            dir = 'z'
            length = abs(z - ze) + 1
            for loc in range(z, z + length):
                field[x][y][loc] = i
        objects[i] = [[x,y,z], dir, length]
        i += 1

    for z in reversed(range(1, len(field[0][0]))):
        for x in range(len(field)):
            print("%4d" % field[x][0][z], end="|")
        print(end = "   ")
        for x in range(len(field)):
            print("%4d" % field[x][1][z], end="|")
        print(end = "   ")
        for x in range(len(field)):
            print("%4d" % field[x][2][z], end="|")
        print()
    print()
    print()
    print()

    found = set()
    for z in range(len(field[0][0])):
        for y in range(len(field[0])):
            for x in range(len(field)):
                i = field[x][y][z]
                if(i == 0):
                    continue
                if(i in found):
                    continue
                else:
                    found.add(i)
                object = objects[field[x][y][z]]
                pos = object[0]
                maxBase = base[pos[0]][pos[1]]
                for _ in range(object[2] - 1):
                    a = pos[0] + dirs[object[1]][0]
                    b = pos[1] + dirs[object[1]][1]
                    c = pos[2] + dirs[object[1]][2]
                    pos = (a,b,c)
                    maxBase = max(base[pos[0]][pos[1]], maxBase)
                oldPos = [x,y,z]
                pos = [x,y,maxBase + 1]
                for _ in range(object[2]):
                    field[oldPos[0]][oldPos[1]][oldPos[2]] = 0
                    field[pos[0]][pos[1]][pos[2]] = i
                    base[pos[0]][pos[1]] = pos[2]
                    a = pos[0] + dirs[object[1]][0]
                    b = pos[1] + dirs[object[1]][1]
                    c = pos[2] + dirs[object[1]][2]
                    pos = (a,b,c)
                    a = oldPos[0] + dirs[object[1]][0]
                    b = oldPos[1] + dirs[object[1]][1]
                    c = oldPos[2] + dirs[object[1]][2]
                    oldPos = (a,b,c)
    holds = {}
    isHeldBy = {}
    for x in range(len(field)):
        for y in range(len(field[0])):
            for z in range(1, len(field[0][0]) - 1):
                if field[x][y][z] != field[x][y][z+1] and field[x][y][z] != 0 and field[x][y][z+1] != 0:
                    if not field[x][y][z] in holds:
                        holds[field[x][y][z]] = [field[x][y][z+1]]
                    else:
                        if not field[x][y][z+1] in holds[field[x][y][z]]:
                            holds[field[x][y][z]].append(field[x][y][z+1])
                    if not field[x][y][z + 1] in isHeldBy:
                        isHeldBy[field[x][y][z + 1]] = [field[x][y][z]]
                    else:
                        if not field[x][y][z] in isHeldBy[field[x][y][z + 1]]:
                            isHeldBy[field[x][y][z + 1]].append(field[x][y][z])
    
    sum = 0
    found = {}
    for i in objects.keys():
        s = set()
        q = queue.SimpleQueue()
        s.add(i)
        q.put(i)
        while not q.empty():
            item = q.get()
            #print(holds.get(item, None))
            for j in holds.get(item, set()):
                if all(x in s for x in isHeldBy[j]):
                    s.add(j)
                    q.put(j)
        sum += len(s) - 1
    print(sum)

