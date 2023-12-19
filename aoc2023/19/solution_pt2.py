import copy

def sss(name, bounds):
    ranges = []
    workflow = workflows[name]
    for rule in workflow['rules']:
        new_bounds = copy.deepcopy(bounds)
        if rule['gt']:
            bounds[rule['c']][1] = min(rule['val'],bounds[rule['c']][1])
            new_bounds[rule['c']][0] = max(rule['val'] + 1,bounds[rule['c']][0])
        else:
            bounds[rule['c']][0] = max(rule['val'], bounds[rule['c']][0])
            new_bounds[rule['c']][1] = min(rule['val'] - 1,bounds[rule['c']][1])
        if rule['dst'] == 'A':
            ranges.append(new_bounds)
        elif rule['dst'] != 'R':
            ranges.extend(sss(rule['dst'], new_bounds))
    if workflow['def'] == 'A':
        ranges.append(bounds)
    elif workflow['def'] != 'R':
        ranges.extend(sss(workflow['def'], bounds))
    return ranges
    

with open("input") as input:
    lines = input.readlines()
    wf = [x.strip() for x in lines[:lines.index("\n")]]
    it = [x.strip() for x in lines[lines.index("\n") + 1:]]
    workflows = {}
    positions = {}
    for x in wf:
        name = x[:x.find("{")]
        workflow = {}
        rules = x[x.find("{") + 1:-1]
        rules = rules.split(",")
        workflow['def'] =  rules[len(rules) - 1]
        positions[workflow['def']] = (name, 'def')
        rules = rules[:-1]
        workflow['rules'] = []
        i = 0
        for r in rules:
            rule = {}
            rule['c'] = r[0]
            rule['gt'] = True if r[1] == '>' else False
            rule['val'] = int(r[2:r.find(":")])
            rule['dst'] = r[r.find(":") + 1:]
            positions[rule['dst']] = (name, i)
            workflow['rules'].append(rule)
            i += 1
        workflows[name] = workflow
    bounds = {
        'x' : [1,4000],
        'm' : [1,4000],
        'a' : [1,4000],
        's' : [1,4000]
    }
    ranges = sss('in', bounds)
    print(ranges)
    sum = 0
    for r in ranges:
        print(r)
        prod = 1
        for key, val in r.items():
            prod *= (val[1] - val[0] + 1)
        sum += prod
    print(sum)



"""     for name, workflow in workflows.items():
        position = None
        if workflow['def'] == 'A':
            position = 'def'
        for i, rule in enumerate(workflow['rules']):
            if rule['dst'] == 'A':
                position = i
        if position != None:
            bounds = {
                'x' : [1,4000],
                'm' : [1,4000],
                'a' : [1,4000],
                's' : [1,4000]
            }
            while(True):
                for rule in reversed(workflow['rules']):
                    if rule['c'] != 'A':
                        if rule['gt']:
                            bounds[rule['c']][1] = min(rule['val'] - 1,bounds[rule['c']][1])
                        else:
                            bounds[rule['c']][0] = max(rule['val'], bounds[rule['c']][0])
                name, position = positions.get(name, (None, None))
                if not name:
                    break
                workflow = workflows[name]
        ranges.append(bounds) """