def hash(s):
    cur = 0
    for c in s:
        cur += ord(c)
        cur *= 17
        cur %= 256
    return cur

with open("input") as input:
    steps = ("".join([line.strip() for line in input.readlines()])).split(",")
    m = []
    for i in range(256):
        m.append({})
    for step in steps:
        if "=" in step:
            op = 0
            (label, val) = step.split("=")
            b = hash(label)
            box = m[b]
            if label in box:
                box[label] = val
            box[label] = val
        else:
            op = 1
            label = step.split("-")[0]
            b = hash(label)
            box = m[b]
            if label in box:
                box.pop(label)
    sum = 0
    for i, box in enumerate(m):
        j = 1
        for (key, val) in box.items():
            prod = 1
            prod *= i+1
            prod *= j
            prod *= int(val)
            sum += prod
            j +=1
    print(sum)