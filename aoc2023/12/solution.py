def rec(row, seq, force):
    if(row == [] and seq != []):
        return 0
    if seq == []:
        if('#' in row):
            return 0
        else:
            return 1
    place = False
    if(row[0] == '.'):
        if force:
            return 0
        else:
            return rec(row[1:], seq, False)
    if(row[0] == '#' or row[0] == '?' and force):
        place = True
    sum = 0
    if not place:
        sum += rec(row[1:], seq, False)
    if seq[0] == 1:
        if len(row) > 1 and row[1] == '#':
            return sum
        seq = seq[1:]
        return sum + rec(row[2:], seq, False)
    else:
        seq = [x for x in seq]
        seq[0] -= 1
        return sum + rec(row[1:], seq, True)


with open("input") as input:
    lines = input.readlines()
    rows = []
    sequence = []
    sum = 0
    for line in lines:
        row = [x for x in line.split(" ")[0]]
        sequence = [int(x) for x in line.split(" ")[1].strip().split(",")]
        sum += rec(row, sequence, False)
    print(sum)