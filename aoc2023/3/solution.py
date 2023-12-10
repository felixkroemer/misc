with open("input") as input:
    lines = input.readlines()
    next = None
    sum = 0
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
                valid = False
                for k in range(max(start - 1, 0), min(end + 1, len(line))):
                    if(prev and prev[k] != '.' and not prev[k].isnumeric()):
                        valid = True
                        break
                    if(next and next[k] != '.' and not next[k].isnumeric()):
                        valid = True
                        break
                    if current[k] != '.' and not current[k].isnumeric():
                        valid = True
                        break
                if valid:
                    print(start, end, num)
                    sum += int(num)
                start = -1
                end = -1
print(sum)