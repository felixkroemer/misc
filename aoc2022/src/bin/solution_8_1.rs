use std::{collections::HashSet, fs::read_to_string, u32};
use std::convert::TryInto;

fn main() {
    let mut map: Vec<Vec<u32>> = Vec::new();
    for line in read_to_string("input").unwrap().lines() {
        let mut r = Vec::new();
        for c in line.chars() {
            r.push(c.to_digit(10).unwrap());
        }
        map.push(r);
    }
    let mut found: HashSet<String> = HashSet::new();
    for i in 0..map.len() {
        let mut h: i32 = -1;
        for j in 0..map[0].len() {
            if h == -1 || TryInto::<i32>::try_into(map[i][j]).unwrap() > h {
                println!("{} {}", i, j);
                h = map[i][j].try_into().unwrap();
                let key = format!("{},{}", i, j);
                found.insert(key);
            }
        }
    }
    for i in 0..map.len() {
        let mut h = -1;
        for j in (0..map[0].len()).rev() {
            if h == -1 || map[i][j] > h.try_into().unwrap() {
                println!("{} {}", i, j);
                h = map[i][j].try_into().unwrap();
                let key = format!("{},{}", i, j);
                found.insert(key);
            }
        }
    }
    for j in 0..map[0].len() {
        let mut h = -1;
        for i in 0..map.len() {
            if h == -1 || map[i][j] > h.try_into().unwrap() {
                println!("{} {}", i, j);
                h = map[i][j].try_into().unwrap();
                let key = format!("{},{}", i, j);
                found.insert(key);
            }
        }
    }
    for j in 0..map[0].len() {
        let mut h = -1;
        for i in (0..map.len()).rev() {
            if h == -1 || map[i][j] > h.try_into().unwrap() {
                println!("{} {}", i, j);
                h = map[i][j].try_into().unwrap();
                let key = format!("{},{}", i, j);
                found.insert(key);
            }
        }
    }
    println!("{}", found.len());
    println!()
}
