with open("input") as input:
    steps = ("".join([line.strip() for line in input.readlines()])).split(",")
    sum = 0
    for step in steps:
        cur = 0
        for c in step:
            cur += ord(c)
            cur *= 17
            cur %= 256
        sum += cur
    print(sum)