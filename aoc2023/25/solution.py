from collections import defaultdict


with open("input") as input:
    lines = input.readlines()
    g = defaultdict(lambda: set())
    for line in lines:
        line = line.strip()
        items = line.split(" ")
        for item in items[1:]:
            if item not in g:
                g[item] = set()
            g[items[0][:-1]].add(item)
            # g[item].add(items[0][:-1])

    graph = defaultdict(lambda: defaultdict(int))
    merged_nodes = defaultdict(lambda: set())
    for v in g:
        merged_nodes[v].add(v)

    for u in g:
        for v in g[u]:
            graph[u][v] += 1
            graph[v][u] += 1
    nodes = list(graph.keys())
    min_cut = float("inf")
    while len(nodes) > 1:
        merged = set()
        weights = defaultdict(int)
        prev = None
        for i in range(len(nodes)):
            selected = max(
                (n for n in nodes if n not in merged),
                key=lambda x: weights[x],
                default=None,
            )
            if selected is None:
                break
            if i == len(nodes) - 1:
                for v, w in graph[selected].items():
                    if v != prev:
                        graph[prev][v] += w
                        graph[v][prev] += w
                    del graph[v][selected]
                del graph[selected]
                nodes.remove(selected)
                for node in merged_nodes[selected]:
                    merged_nodes[prev].add(node)
                merged_nodes[selected] = merged_nodes[prev]
                if weights[selected] < min_cut:
                    min_cut = weights[selected]
                    print(min_cut)
                    print(
                        (len(merged_nodes[prev]) - 1)
                        * (len(g) - len(merged_nodes[prev]) + 1)
                    )
                break
            merged.add(selected)
            prev = selected
            for v in graph[selected]:
                if v not in merged:
                    weights[v] += graph[selected][v]
