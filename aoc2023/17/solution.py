import math
import queue

def steps(s, d, pred):
    xSame = True
    ySame = True
    i = 1
    while(True):
        if not s:
            return i
        else:
            if s[0][0] != d[0][0]:
                ySame = False
            if s[0][1] != d[0][1]:
                xSame = False
            if not ySame and not xSame:
                return i
            s = pred.get(s, None)
            i += 1

directions = {0: (0,1), 1: (0,-1), 2: (1,0), 3: (-1,0)}

with open("input") as input:
    lines = [[int(x) for x in line.strip()] for line in input.readlines()]
    q = queue.PriorityQueue()
    dist = {}
    pred = {}
    start = ((0,0), None)
    dist[start] = 0
    q.put((0, start))
    while not q.empty():
        fff, state = q.get()
        print(fff)
        print(q, [str(x) for x in list(q.queue)])
        """
        if state in visited:
            continue
        else:
            visited.append(state) """
        (i,j), dir = state
        distance = dist[state]
        for key, direction in directions.items():
            a = i + direction[0]
            b = j + direction[1]
            newState = ((a,b), key)
            if a < 0 or b < 0 or a >= len(lines) or b >= len(lines[0]):
                continue
            if distance + lines[a][b] < dist.get(newState, math.inf) and steps(state, newState, pred) <= 4:
                dist[newState] = distance + lines[a][b]
                pred[newState] = state
                q.put((dist[newState], newState))

    a = (len(lines[0])-1, len(lines[0])-1)
    print(dist.get((a, 0)))
    print(dist.get((a, 1)))
    print(dist.get((a, 2)))
    print(dist.get((a, 3)))

    p = (a, 2)
    path = []
    while(p):
        print(p)
        path.append(p)
        p = pred.get(p, None)

    path = [x[0] for x in path]
    print(path)

    print(path)
    sum = 0
    for x in path[:-1]:
        sum += lines[x[0]][x[1]]
    print(sum)
    
    for i in range(len(lines)):
        for j in range(len(lines[0])):
            print("." if (i,j) in path else lines[i][j], end="")
        print()
