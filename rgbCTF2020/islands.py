def findGroup(entry):
    for g in groups:
        if entry in g:
            return g
    return None

f = open("islands", "r").read().split("\n")
islands = 0
del f[len(f)-1] 
groups = []
prev = f[0]
for line in enumerate(f):
    if(line[0] % 100 == 0):
        print(line[0])
    counter = 0
    for c in range(len(line[1])+1):
        if c < len(line[1]) and line[1][c] == '1':
            counter = counter + 1
        else:
            if counter == 0:
                continue
            g = set()
            for i in range(counter):
                g.add((line[0], c-i-1))

            if line[0] != 0:
                for i in range(c-counter,c):
                    if(prev[i] == '1'):
                        otherGroup = findGroup((line[0]-1, i))
                        if otherGroup != None:
                            g = g.union(otherGroup)
                            groups.remove(otherGroup)
            groups.append(g)
            counter = 0
    prev = line[1]
    rem = []
    for g in groups:
        max = 0
        for i in g:
            if i[0] > max:
                max = i[0]
        if max < line[0]:
            rem.append(g)
    for g in rem:
        groups.remove(g)
        islands = islands + 1

islands += len(groups)

print(islands, end="")