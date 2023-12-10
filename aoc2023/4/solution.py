with open("input") as input:
    sum = 0
    for line in input.readlines():
        ind = line.find("|")
        a = line[:ind].split(" ")[2:]
        b = line[ind + 1:-1].split(" ")
        a = set([a for a in a if a])
        b = set([b for b in b if b])
        intersect = len(a.intersection(b))
        sum += intersect and 2 ** (intersect - 1)
        print(intersect, line)
print(sum)
