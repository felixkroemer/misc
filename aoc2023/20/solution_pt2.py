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
    
    # rx -> &gf -> &qs[4] -> &mh -> %nr[~10]
    
    nestedDeps = set()
    counters = {}
    for dep in back['gf']:
        #print("Dep: ", dep)
        b = back[dep]
        #print("Back: ", b[0], len(b))
        for nestedDep in back[b[0]]:
            nestedDeps.add(nestedDep)
            counters[nestedDep] = None
            #print("BackBack: ", nestedDep, len(back[nestedDep]))
        #print()

    #quit()
    
    lastLow = 0
    lastHigh = 0
    i = 1
    while(True):

        #print(mods['mh']['state'])

        q = queue.SimpleQueue()
        for mod in mods['broadcaster']['dest']:
            q.put(('broadcaster', mod, False))
        while not q.empty() :
            inst = q.get_nowait()
            if inst[1] == 'output':
                continue

            if inst[1] == 'rn':
                if all([x for x in mods['rn']['state'].values()]):
                    print([x for x in mods['rn']['state'].values()])
                    print("pass")
                    print(i)

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

        #print([x for x in mods['mh']['state'].values()])
        #print(mods['nv']['state'])

        # useless
        for name, mod in mods.items():
            if name in nestedDeps:
                if mods[name]['state'] and not counters[name]:
                    counters[name] = i

        if(all([x for x in counters.values()])):
            pass

        i += 1

    # 4051
    # 3919
    # 3761
    # 3907