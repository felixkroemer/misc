import queue
import sys

sys.setrecursionlimit(10000000)

dirs = [(1,0), (-1,0), (0,1), (0,-1)]

def getDirections(node, field):
    n = []
    for dir in dirs:
        newPos = node[0] + dir[0], node[1] + dir[1]
        if newPos[0] < 0 or newPos[1] < 0 or newPos[0] >= len(lines[0]) or newPos[1] >= len(lines):
            continue
        if field[newPos[0]][newPos[1]] == '.':
            n.append(newPos)
        elif dir == dirs[0] and field[newPos[0]][newPos[1]] == 'v':
            n.append(newPos)
        elif dir == dirs[1] and field[newPos[0]][newPos[1]] == '^':
            n.append(newPos)
        elif dir == dirs[2] and field[newPos[0]][newPos[1]] == '>':
            n.append(newPos)
        elif dir == dirs[3] and field[newPos[0]][newPos[1]] == '<':
            n.append(newPos)
    return n

def visit(node, visited, field, l):
    if node in visited:
        return
    else:
        visited.add(node)
    neighbors = getDirections(node, field)
    for neighbor in neighbors:
        visit(neighbor, visited, field, l)
    l.insert(0, node)
            

with open("input") as input:
    lines = [[x for x in line.strip()] for line in input.readlines()]
    
    visited = set()
    l = []
    visit((0,1), visited, lines, l)
    print(l)

    for n in l:
        numericNeighbors = []
        for dir in dirs:
            newPos = (n[0] + dir[0], n[1] + dir[1])
            if newPos[0] < 0 or newPos[1] < 0 or newPos[0] >= len(lines[0]) or newPos[1] >= len(lines):
                continue
            if type(lines[newPos[0]][newPos[1]]) == int:
                numericNeighbors.append(lines[newPos[0]][newPos[1]])
        numericNeighbors.append(0)
        maxNeighbor = max(numericNeighbors)
        lines[n[0]][n[1]] = maxNeighbor + 1

    for line in lines:
        for x in line:
            print("%2s" % str(x), end="")
        print()

    print(lines[len(lines) - 1][-2] - 1)