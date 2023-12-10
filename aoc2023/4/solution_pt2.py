with open("input") as input:
    lines = input.readlines()
    x = [ 1 for i in lines]
    for i, line in enumerate(lines):
        ind = line.find("|")
        a = line[:ind].split(" ")[2:]
        b = line[ind + 1:-1].split(" ")
        a = set([a for a in a if a])
        b = set([b for b in b if b])
        intersect = len(a.intersection(b))
        for j in range(i + 1, i + 1 + intersect):
            x[j] += x[i]
print(sum(x))
