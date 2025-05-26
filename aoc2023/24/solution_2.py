from z3 import *
import numpy as np

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

    s = Solver()

    rx, ry, rz = Ints("rx ry rz")
    rvx, rvy, rvz = Ints("rvx rvy rvz")
    for i, h in enumerate(hail):
        t = Int(f"t{i}")
        s.add(t > 0)

        s.add(rx + rvx * t == h["x"] + h["vx"] * t)
        s.add(ry + rvy * t == h["y"] + h["vy"] * t)
        s.add(rz + rvz * t == h["z"] + h["vz"] * t)

    s.check()
    m = s.model()
    rock_x = m[rx].as_long()
    rock_y = m[ry].as_long()
    rock_z = m[rz].as_long()
    print(str(rock_x) + " " + str(rock_y) + " " + str(rock_z))
    print(rock_x + rock_y + rock_z)
