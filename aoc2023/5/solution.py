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
    for i, seed in enumerate(seeds):
        seed = int(seed)
        if i % 2 == 0:
            continue
        for j in range(seed, seed + int(seeds[i + 1])):
            mappedValue = j
            for map in maps:
                for mapping in maps[map]:
                    if mappedValue >= mapping[1] and mappedValue < mapping[1]   + mapping[2]:
                        mappedValue = mappedValue + mapping[0] - mapping[1]
                        break
            solutions.append(mappedValue)
print(min(solutions))
            
