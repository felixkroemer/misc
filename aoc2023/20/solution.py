import queue

with open("input") as input:
    lines = [line.strip() for line in input.readlines()]
    mods = {}
    back = {}
    for line in lines:
        source, dest = line.split(" -> ")
        if source[0] == 'b':
            t = 'b'
            name = source
        else:
            t = source[0]
            name = source[1:]
        mod = {}
        mod['type'] = t
        if t == '%':
            mod['state'] = False # off
        mod['dest'] = dest.split(", ")
        for dest in mod['dest']:
            if back.get(dest, None):
                back[dest].append(name)
            else:
                back[dest] = [name]
        mods[name] = mod
    for n, mod in mods.items():
        if mod['type'] == '&':
            mod['state'] = {name: False for name in back[n]}
    low = 0
    high = 0
    for _ in range(1000):
        q = queue.SimpleQueue()
        for mod in mods['broadcaster']['dest']:
            q.put(('broadcaster', mod, False))
        while not q.empty() :
            inst = q.get_nowait()
            if inst[2]:
                high += 1
            else:
                low += 1
            if inst[1] == 'output':
                continue
            mod = mods.get(inst[1], None)
            if not mod:
                continue
            if mod['type'] == '%':
                if inst[2]: # low pulse
                    continue
                if mod['state']:
                    pulse = False
                else:
                    pulse = True
                mod['state'] = not mod['state']
            if mod['type'] == '&':
                src = inst[0]
                mod['state'][src] = inst[2]
                pulse = True
                if all(mod['state'].values()):
                    pulse = False
            for dst in mod['dest']:
                q.put((inst[1], dst, pulse))
    print(low + 1000, high)
    print((low + 1000) * high)
                
