maps = {
    "seed-to-soil" : [],
    "soil-to-fertilizer": [],
    "fertilizer-to-water": [],
    "water-to-light": [],
    "light-to-temperature": [],
    "temperature-to-humidity": [],
    "humidity-to-location": []
}

with open("input") as input:
    lines = input.readlines()
    seeds = lines[0][:-1].split(" ")[1:]
    lines = lines[2:]
    i = 0
    while i < len(lines):
        lines[i] = lines[i].strip()
        if lines[i] and not lines[i][0].isnumeric():
            entries = []
            maps[lines[i].split(" ")[0]] = entries
            i += 1
            while i < len(lines) and lines[i][0] != "\n":
                entries.append([int(i) for i in lines[i].strip().split(" ")])
                i += 1
        i += 1
    solutions = []
    print(seeds)
    m = None
    minValue = 1000000000000
    for i, seed in enumerate(seeds):
        seed = int(seed)
        if i % 2 == 1:
            continue
        ranges = [[seed, seed + int(seeds[i+1]) - 1, 0]]
        for map in maps:
            newRanges = []
            while not len(ranges) == 0:
                range = ranges.pop()
                offset = range[2]
                found = False
                for mapRange in maps[map]:
                    to, fr, length = mapRange
                    length -= 1
                    if range[1] + offset < fr or range[0] + offset > fr + length :
                        continue
                    elif range[0] + offset >= fr  and range[1] + offset <= fr + length:
                        found = True
                        newRanges.append([range[0], range[1], offset + to - fr])
                    elif range[0] + offset >= fr and range[1] + offset > fr + length:
                        found = True
                        newRanges.append([range[0], fr + length - offset, offset + to - fr])
                        ranges.append([fr + length - offset + 1, range[1], offset])
                        break
                    elif range[0] + offset < fr and range[1] + offset <= fr + length:
                        found = True
                        newRanges.append([fr - offset, range[1], offset + to - fr])
                        ranges.append([range[0], fr - offset - 1, offset])
                        break
                    elif range[0] + offset < fr and range[1] + offset > fr + length:
                        found = True
                        newRanges.append([fr-offset, fr + length - offset, offset + to - fr])
                        ranges.append([range[0], fr - offset - 1, offset])
                        ranges.append([fr + length - offset + 1, range[1], offset])
                        break
                if not found:
                    newRanges.append(range)
            ranges = newRanges
            if map == 'humidity-to-location':
                for range in ranges:
                    if range[0] + range[2] < minValue:
                        m = range[0]
                        minValue = range[0] + range[2]
                break
    print(m, minValue)
                    

            
