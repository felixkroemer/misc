import queue
import sys

sys.setrecursionlimit(10000000)

dirs = [(1,0), (-1,0), (0,1), (0,-1)]
f = ['.', '<', '>', 'v', '^']

def getDirections(node, field, visited):
    n = []
    for dir in dirs:
        newPos = node[0] + dir[0], node[1] + dir[1]
        if newPos[0] < 0 or newPos[1] < 0 or newPos[0] >= len(lines[0]) or newPos[1] >= len(lines):
            continue
        if field[newPos[0]][newPos[1]] in f and newPos not in visited:
            n.append(newPos)
    return n

def visit(node, visited, graph, length, lengths):
    if node[1] == (len(lines) - 2, len(lines[0]) - 2):
        if length + node[2] > lengths[0]:
            lengths[0] = length + node[2]
    if node in visited:
        return
    else:
        visited.add(node)
    neighbors = graph[node]
    for neighbor in neighbors:
        visit(neighbor, visited, graph, length + node[2], lengths)
    visited.remove(node)

with open("input") as input:
    lines = [[x for x in line.strip()] for line in input.readlines()]
    
    nodes = set()
    graph = {}
    visited = set()
    x = {}

    for i in range(len(lines)):
        for j in range(len(lines[0])):
            if lines[i][j] in f:
                neighbors = getDirections((i,j), lines, set())
                if len(neighbors) >= 3:
                    node = node = ((i,j), (i,j), 1)
                    nodes.add(node)
                    visited.add((i,j))
                    graph[node] = []
                    x[(i,j)] = node

    for node in list(nodes):
        neighbors = getDirections(node[0], lines, set())
        for neighbor in neighbors:
            if neighbor in visited:
                continue
            length = 0
            pos = neighbor
            prev = node[0]
            while(True):
                length += 1
                visited.add(pos)
                n = getDirections(pos, lines, [prev])
                if len(n) == 1:
                    prev = pos
                    pos = n[0]
                else:
                    newNode = (neighbor, prev, length - 1)
                    nodes.add(newNode)
                    graph[newNode] = []
                    if pos in x:
                        graph[x[pos]].append(newNode)
                        graph[newNode].append(x[pos])
                    x[newNode[0]] = newNode
                    x[newNode[1]] = newNode
                    graph[newNode].append(node)
                    graph[node].append(newNode)
                    break

    for node in nodes:
        print(node)

    print()

    for d in graph:
        if len(graph[d]) == 1:
            print(d, graph[d])


    lengths = [0]
    visited = set()
    visit(x[(1,1)], visited, graph, 0, lengths)
    print(max(lengths) + 1)


