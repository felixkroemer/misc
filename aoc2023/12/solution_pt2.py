from functools import cache

@cache
def rec(row, seq, force):
    if(row == tuple() and seq != tuple()):
        return 0
    if seq == tuple():
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
        sum += rec(tuple(row[1:]), tuple(seq), False)
    if seq[0] == 1:
        if len(row) > 1 and row[1] == '#':
            return sum
        seq = seq[1:]
        return sum + rec(tuple(row[2:]), tuple(seq), False)
    else:
        seq = [x for x in seq]
        seq[0] -= 1
        return sum + rec(tuple(row[1:]), tuple(seq), True)


with open("input") as input:
    lines = input.readlines()
    rows = []
    sequence = []
    sum = 0
    for line in lines:
        row = [line.split(" ")[0]] * 5
        row = '?'.join(row)
        row = [x for x in row]
        sequence = [line.split(" ")[1].strip()] * 5
        sequence = ','.join(sequence)
        sequence = [int(x) for x in sequence.split(",")]
        sum += rec(tuple(row), tuple(sequence), False)
    print(sum)
    print(rec.cache_parameters())