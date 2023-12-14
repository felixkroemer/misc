with open("input") as input:
    lines = input.read()
    lines = lines.split("\n\n")
    patterns = [line.strip().split("\n") for line in lines]
    sum = 0
    for pattern in patterns:
        out = set()
        width = len(pattern[0])
        height = len(pattern)
        for k in range(height):
            for i in range(1, width):
                if i in out:
                    continue
                l = i if (i < width / 2) else width - i
                for j in range(1, l + 1):
                    if pattern[k][i-j] != pattern[k][i+j-1]:
                        out.add(i)
        diff = set(range(1, width)).difference(out)
        if(diff != set()):
            sum += diff.pop()
        out = set()
        for k in range(width):
            for i in range(1, height):
                if i in out:
                    continue
                l = i if (i < height / 2) else height - i
                for j in range(1, l + 1):
                    if pattern[i-j][k] != pattern[i+j-1][k]:
                        out.add(i)
        diff = set(range(1, height)).difference(out)
        if(diff != set()):
            sum += diff.pop() * 100
    print(sum)