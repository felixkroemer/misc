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
        s[len(s)-1].append(0)
        for i in reversed(range(0, len(s)-1)):
            s[i].append(s[i+1][len(s[i+1])-1] + s[i][len(s[i])-1])
        print(s)
        sum += s[0][len(s[0]) - 1]
    print(sum)