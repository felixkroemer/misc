with open("input") as input:
    lines = input.readlines()
    sum = 0
    for line in lines:
        line = [int(x) for x in line.strip().split(" ")]
        s = []
        s.append(line)
        l = line
        while (True):
            n = []
            for i in range(1, len(l)):
                n.append(l[i] - l[i-1])
            s.append(n)
            l = n
            if(all([x == 0 for x in l])):
                break
        s[len(s)-1].insert(0, 0)
        for i in reversed(range(0, len(s)-1)):
            s[i].insert(0, s[i][0]-s[i+1][0])
        print(s)
        sum += s[0][0]
    print(sum)