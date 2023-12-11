with open("input") as input:
    lines = [line.strip() for line in input.readlines()]
    galaxies = []
    x = set()
    y = set()
    for i, line in enumerate(lines):
        if all([x == "." for x in line]):
            x.add(i)
        for j, c in enumerate(line):
            if lines[i][j] == "#":
                galaxies.append((i, j))
    for j in range(len(lines[0])):
        if all(lines[i][j] == '.' for i in range(len(lines[0]))):
            y.add(j)

    mult = 1000000

    sum = 0
    for i, g in enumerate(galaxies):
        for other in galaxies[i+1:]:
            dist = abs(g[0] - other[0]) + abs(g[1] - other[1])
            smallerX = g if g[0] < other[0] else other
            for j in range(smallerX[0], (other[0] + 1) if smallerX == g else (g[0] + 1)):
                if j in x:
                    dist += mult - 1
            smallerY = g if g[1] < other[1] else other
            for j in range(smallerY[1], (other[1] + 1) if smallerY == g else (g[1] + 1)):
                if j in y:
                    dist += mult - 1
            sum += dist
    print(sum)