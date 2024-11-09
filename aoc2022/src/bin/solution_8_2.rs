use std::{fs::read_to_string, u32};

fn main() {
    let mut map: Vec<Vec<u32>> = Vec::new();
    for line in read_to_string("input").unwrap().lines() {
        let mut r = Vec::new();
        for c in line.chars() {
            r.push(c.to_digit(10).unwrap());
        }
        map.push(r);
    }
    let mut max = 0;
    for i in 1..map.len() - 1 {
        for j in 1..map[0].len() - 1 {
            let h = map[i][j];
            let mut m = 1;
            let mut n = 1;
            for x in i + 1..map.len() - 1 {
                if map[x][j] < h {
                    n += 1;
                } else {
                    break;
                }
            }
            m *= n;
            n = 1;
            for x in (1..i).rev() {
                if map[x][j] < h {
                    n += 1;
                } else {
                    break;
                }
            }
            m *= n;
            n = 1;
            for x in j + 1..map[0].len() - 1 {
                if map[i][x] < h {
                    n += 1;
                } else {
                    break;
                }
            }
            m *= n;
            n = 1;
            for x in (1..j).rev() {
                if map[i][x] < h {
                    n += 1;
                } else {
                    break;
                }
            }
            m *= n;
            if m > max {
                max = m;
            }
        }
    }
    println!("{}", max)
}
