with open("input") as input:
    lines = input.readlines()
    instruction = lines[0].strip()
    lines = lines[2:]
    network = {}
    for line in lines:
        line = line.strip().split(" ")
        src = line[0]
        dst1 = line[2][1:-1]
        dst2 = line[3][:-1]
        network[src] = [dst1, dst2]
    stack = set(['AAA'])
    i = 0
    j = 0
    while(True):
        if(i >= len(instruction)):
            j += 1
            i = i % len(instruction)
        print(i)
        dir = 0 if instruction[i] == 'L' else 1
        n = set()
        found = False
        while stack:
            e = stack.pop()
            next = network[e][dir]
            if(next == 'ZZZ'):
                found = True
                break
            n.add(network[e][dir])
        stack = n
        print(stack)
        i += 1
        if(found):
            print(j, i, j * len(instruction) + i)
            quit()
