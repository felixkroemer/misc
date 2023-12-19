with open("input") as input:
    lines = input.readlines()
    wf = [x.strip() for x in lines[:lines.index("\n")]]
    it = [x.strip() for x in lines[lines.index("\n") + 1:]]
    workflows = {}
    for x in wf:
        workflow = {}
        rules = x[x.find("{") + 1:-1]
        rules = rules.split(",")
        workflow['def'] =  rules[len(rules) - 1]
        rules = rules[:-1]
        workflow['rules'] = []
        for r in rules:
            rule = {}
            rule['c'] = r[0]
            rule['gt'] = True if r[1] == '>' else False
            rule['val'] = int(r[2:r.find(":")])
            rule['dst'] = r[r.find(":") + 1:]
            workflow['rules'].append(rule)
        workflows[x[:x.find("{")]] = workflow
    items = []
    for i in it:
        item = {}
        item['x'] = int(i[i.find("x=") + 2:i.find(",", i.find("x="))])
        item['m'] = int(i[i.find("m=") + 2:i.find(",", i.find("m="))])
        item['a'] = int(i[i.find("a=") + 2:i.find(",", i.find("a="))])
        item['s'] = int(i[i.find("s=") + 2:i.find("}", i.find("s="))])
        items.append(item)
    sum = 0
    for item in items:
        wf = 'in'
        while(True):
            if wf == 'A':
                sum += item['x'] + item['m']+ item['a'] + item['s']
                break
            elif wf == 'R':
                break
            workflow = workflows[wf]
            skip = False
            for rule in workflow['rules']:
                match = item[rule['c']] > rule['val'] if rule['gt'] else item[rule['c']] < rule['val']
                if match:
                    wf = rule['dst']
                    skip = True
                    break
            if not skip:
                wf = workflow['def']
    print(sum)
