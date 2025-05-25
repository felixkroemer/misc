import numpy as np

FROM = 200000000000000
TO = 400000000000000
# FROM = 7
# TO = 27

with open("input") as input:
    lines = input.readlines()
    hail = []
    for line in lines:
        items = line.split()
        hail.append(
            {
                "x": int(items[0][:-1]),
                "y": int(items[1][:-1]),
                "z": int(items[2]),
                "vx": int(items[4][:-1]),
                "vy": int(items[5][:-1]),
                "vz": int(items[6]),
            }
        )
    i = 0
    for hailA in hail:
        for hailB in hail:
            if hailA == hailB:
                continue
            p1 = np.array([hailA["x"], hailA["y"]])
            d1 = np.array([hailA["vx"], hailA["vy"]])

            p2 = np.array([hailB["x"], hailB["y"]])
            d2 = np.array([hailB["vx"], hailB["vy"]])

            A = np.column_stack((d1, -d2))
            b = p2 - p1

            try:
                sol = np.linalg.solve(A, b)
                if sol[0] < 0 or sol[1] < 0:
                    continue
                intersection = (
                    hailA["x"] + hailA["vx"] * sol[0],
                    hailA["y"] + hailA["vy"] * sol[0],
                )
                if (
                    intersection[0] >= FROM
                    and intersection[0] <= TO
                    and intersection[1] >= FROM
                    and intersection[1] <= TO
                ):
                    i += 1
            except np.linalg.LinAlgError as e:
                continue

    print(i / 2)
