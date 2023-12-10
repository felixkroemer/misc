import collections
from functools import cmp_to_key

def value(c):
    if c.isnumeric():
        return int(c)
    if c == 'A':
        return 14
    elif c == 'K':
        return 13
    elif c == 'Q':
        return 12
    elif c == 'J':
        return 1
    elif c == 'T':
        return 10

def kind(hand):
    hand = [x for x in hand.split()[0]]
    f = collections.Counter(hand)
    jokers = 0
    if 'J' in f:
        jokers = f.pop("J")
    l = len(f)
    if l == 1 or l == 0:
        return 7
    elif l == 2 and max(f.values()) == 4 - jokers:
        return 6
    elif l == 2:
        return 5
    elif l == 3 and max(f.values()) == 3 - jokers: # fh
        return 4
    elif l == 3: # two pair
        return 3
    elif l == 4:
        return 2
    else:
        return 1

def compare(a, b):
    #print(a,b)
    kindA = kind(a)
    kindB = kind(b)
    if kindA > kindB:
        #print("A")
        return 1
    elif kindB > kindA:
        #print("B")
        return -1
    else:
        #print("CMP")
        for x,y in zip(a, b):
            if x != y:
                if value(x) > value(y):
                    #print(x)
                    return 1
                else:
                    #print(y)
                    return -1
        return 0
    

with open("input") as input:
    lines = [line.strip() for line in input.readlines()]
    lines = sorted(lines, key=cmp_to_key(compare))
    for line in lines:
        print(line)
    sum = 0
    for i, line in enumerate(lines):
        sum += (i+1) * int(line.split()[1])
    print(sum)
