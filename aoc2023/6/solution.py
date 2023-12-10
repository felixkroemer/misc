with open("input") as input:
    lines = input.readlines()
    time = [int(x) for x in lines[0].strip().split()[1:]]
    records = [int(x) for x in lines[1].strip().split()[1:]]
    product = 0
    for i, t in enumerate(time):
        prevMid = 0
        dist = (t-(t // 2)) * (t // 2)
        if(dist < records[i]):
            continue
        start = -(t // -2)
        end = t
        mid = 0
        while(True):
            mid = start + (end - start) // 2
            speed = t - mid
            dist = speed * mid
            if(prevMid == mid):
                if dist < records[i]:
                    mid -= 1
                break   
            if dist > records[i]:
                start = mid
            else:
                end = mid
            prevMid = mid
        prod = (mid + (t // -2)) * 2 + (1 if t % 2 == 0 else 2)
        print(prod)
        product = product * prod if product != 0 else prod
print(product)
