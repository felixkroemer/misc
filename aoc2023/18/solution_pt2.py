import math

def get_size(steps):
    hMin = math.inf
    hMax = -math.inf
    wMin = math.inf
    wMax = -math.inf
    for pos in steps:
        if(pos[0] < hMin):
            hMin = pos[0]
        if(pos[0] > hMax):
            hMax = pos[0]
        if(pos[1] < wMin):
            wMin = pos[1]
        if(pos[1] > wMax):
            wMax = pos[1]
    size = (abs(hMax - hMin) + 1) * (abs(wMax - wMin) + 1)
    if(len(steps) == 5):
        return size

    on_edge = []
    on_edge_index = []
    start = False
    running = False
    s_pos = None
    s_index = None
    prev = None
    index = 0
    for pos in steps: 
        if pos[0] == hMax or pos[0] == hMin or pos[1] == wMax or pos[1] == wMin:
            start = True
            if running:
                on_edge.append((s_pos, pos))
                on_edge_index.append((s_index, index + 1))
                running = False
        else:
            if start and not running:
                running = True
                s_pos = prev
                s_index = index - 1
        prev = pos
        index += 1
    for x, index in zip(on_edge, on_edge_index):
        new_steps = []
        edge = 0
        start = False
        for i in range(index[0], index[1]):
            if start:
                edge += abs(steps[i][0] - steps[i-1][0]) + abs(steps[i][1] - steps[i-1][1]) + 1
            start = True
            new_steps.append(steps[i])
        edge -= (len(new_steps) - 2)
        new_steps.append(x[0])
        size -= (get_size(new_steps) - edge)
    #print(size)
    return size


with open("input") as input:
    lines = [line.strip() for line in input.readlines()]
    dirs = {'R': (0,1), 'L': (0,-1), 'U':(-1, 0), 'D': (1,0)}
    pos = (0,0)
    field = []
    field.append((0,0))
    for line in lines:
        dir, steps, color = line.split()
        steps = int(steps)
        
        steps = int(color[2:-2],16)
        dir = None
        if color[-2:-1] == '0':
            dir = 'R'
        elif color[-2:-1] == '1':
            dir = 'D'
        elif color[-2:-1] == '2':   
           dir = 'L'
        else:
            dir = 'U'
        print(dir, steps)
        pos = (pos[0] + (dirs[dir][0]*steps), pos[1] + (dirs[dir][1]*steps))
        field.append(pos)
    print(get_size(field))