with open("input") as input:
    lines = input.readlines()
    next = None
    gears = {}
    for i, line in enumerate(lines):
        line = line[:-1]
        current = lines[i]
        prev = lines[i - 1] if i > 0 else None
        next = lines[i + 1] if i < len(lines) - 1 else None
        start = -1
        end = -1
        for j, c in enumerate(line):
            if start != -1:
                if not c.isnumeric():
                    end = j
                elif j == len(line) - 1:
                    end = j + 1
            else:
                if c.isnumeric():
                    start = j
            if start != -1 and end != -1:
                num = str(line[start:end])
                starLoc = -1
                for k in range(max(start - 1, 0), min(end + 1, len(line))):
                    if(prev and prev[k] == '*'):
                        starLoc = (i-1, k)
                        break
                    if(next and next[k] == '*'):
                        starLoc = (i + 1, k)
                        break
                    if current[k] == '*':
                        starLoc = (i, k)
                        break
                if starLoc != -1:
                    print(starLoc)
                    key = starLoc
                    if key in gears:
                        gears[key].append(int(num))
                    else:
                        gears[key] = [int(num)]
                start = -1
                end = -1
s = 0
for x in gears.values():
    if len(x) == 2:
        s += x[0] * x[1]
print(s)