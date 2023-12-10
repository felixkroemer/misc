with open("input") as input:
    lines = input.readlines()
    time = int("".join(lines[0].strip().split()[1:]))
    record = int("".join(lines[1].strip().split()[1:]))
    prevMid = 0
    dist = (time-(time // 2)) * (time // 2)
    start = -(time // -2)
    end = time
    mid = 0
    while(True):
        mid = start + (end - start) // 2
        speed = time - mid
        dist = speed * mid
        if(prevMid == mid):
            if dist < record:
                mid -= 1
            break   
        if dist > record:
            start = mid
        else:
            end = mid
        prevMid = mid
    prod = (mid + (time // -2)) * 2 + (1 if time % 2 == 0 else 2)
    print(prod)