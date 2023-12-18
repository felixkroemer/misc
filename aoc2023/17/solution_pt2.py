import math
import queue

""" def steps(s, d, pred):
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
            i += 1 """

directions = {0: (0,1), 1: (0,-1), 2: (-1,0), 3: (1,0)}
turns = {0: [0,2,3], 1: [1,2,3], 2:[2,0,1], 3:[3,0,1]}

with open("input") as input:
    lines = [[int(x) for x in line.strip()] for line in input.readlines()]
    q = queue.PriorityQueue()
    dist = {}
    startRight = ((0,0), 0, 0)
    startDown = ((0,0), 3, 0)
    dist[startRight] = 0
    dist[startDown] = 0
    visited = set()
    q.put((0, startRight))
    q.put((0, startDown))
    while not q.empty():
        _, state = q.get()
        if state in visited:
            continue
        else:
            visited.add(state)
        (i,j), dir, step = state
        if i == len(lines) and j == len(lines[0]):
            print(dist[state])
            break
        #print(state, dist[state])

        for x in [0] if state == startRight else [3] if state == startDown else turns[dir]:
            a = i + directions[x][0]
            b = j + directions[x][1]
            if a < 0 or b < 0 or a >= len(lines) or b >= len(lines[0]):
                continue
            newState = ((a,b), x, step + 1 if x == dir else 0)
            if dist[state] + lines[a][b] < dist.get(newState, math.inf) and newState[2] <= 9:
                if (x == dir or step >= 3) and not (a == len(lines) - 1 and b == len(lines[0]) - 1):
                    dist[newState] = dist[state] + lines[a][b]
                    q.put((dist[newState], newState))
                if a == len(lines) - 1 and b == len(lines[0]) - 1 and x == dir and step >= 2: # actually 3
                    dist[newState] = dist[state] + lines[a][b]
                    q.put((dist[newState], newState))
        """   
        for h in range(len(lines)):
            for k in range(len(lines[0])):
                m = math.inf
                for t in range(4):
                    for w in range(20):
                        if dist.get(((h,k), t, w), math.inf) < m:
                            m = dist.get(((h,k), t, w), math.inf)
                print(m, " ", end="")
            print() """

    for h in range(len(lines)):
        for k in range(len(lines[0])):
            m = math.inf
            for t in range(4):
                for w in range(20):
                    if dist.get(((h,k), t, w), math.inf) < m:
                        m = dist.get(((h,k), t, w), math.inf)
            print(m, " ", end="")
        print()
    