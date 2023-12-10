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
    f = [x for x in network.keys() if x[len(x)-1] == 'A']
    i = 0
    j = 0
    iterations = 20
    for node in f:
        iter = 0
        last = 0
        while(True):
            if(i >= len(instruction)):
                j += 1
                i = i % len(instruction)
            dir = 0 if instruction[i] == 'L' else 1
            node = network[node][dir]
            if node[len(node) - 1] == 'Z':
                if(iter == iterations):
                    break
                n = j * len(instruction) + i
                print(node, n, n - last)
                last = n
                iter += 1
            i += 1

# lcm(12361, 19199, 17621, 13939, 18673, 20777)