with open("input") as input:
    lines = [[x for x in line.strip()] for line in input.readlines()]
    sum = 0
    for i in range(len(lines[0])):
        support = 0
        for j in range(len(lines)):
            if lines[j][i] == '.':
                continue
            if lines[j][i] == '#':
                support = j + 1
            if lines[j][i] == 'O':
                sum += len(lines) - support
                support += 1
    print(sum)