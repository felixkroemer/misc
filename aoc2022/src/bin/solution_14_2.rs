use scan_fmt::scan_fmt;
use std::{
    collections::HashMap,
    fs::read_to_string,
    process::exit,
};

fn main() {
    let mut field: HashMap<(u32, u32), bool> = HashMap::new();
    for line in read_to_string("input").unwrap().lines() {
        let points: Vec<(u32, u32)> = line
            .to_string()
            .split("->")
            .map(|point| scan_fmt!(point, "{},{}", u32, u32).unwrap())
            .collect();
        for i in 1..points.len() {
            if points[i].0 == points[i - 1].0 {
                let (start, end) = if points[i].1 <= points[i - 1].1 {
                    (points[i].1, points[i - 1].1)
                } else {
                    (points[i - 1].1, points[i].1)
                };
                for j in start..=end {
                    field.insert((points[i].0, j), true);
                }
            } else {
                let (start, end) = if points[i].0 <= points[i - 1].0 {
                    (points[i].0, points[i - 1].0)
                } else {
                    (points[i - 1].0, points[i].0)
                };
                for j in start..=end {
                    field.insert((j, points[i].1), true);
                }
            }
        }
    }
    let cutoff = field.keys().map(|&(_, second)| second).max().unwrap_or(0) + 1;

    let mut placed = 0;
    loop {
        let mut sand: (u32, u32) = (500, 0);
        loop {
            if sand.1 == cutoff {
                field.insert(sand, false);
                placed += 1;
                break;
            }
            if !field.contains_key(&(sand.0, sand.1 + 1)) {
                sand = (sand.0, sand.1 + 1)
            } else if !field.contains_key(&(sand.0 - 1, sand.1 + 1)) {
                sand = (sand.0 - 1, sand.1 + 1)
            } else if !field.contains_key(&(sand.0 + 1, sand.1 + 1)) {
                sand = (sand.0 + 1, sand.1 + 1)
            } else {
                if sand == (500, 0) {
                    println!("{}", placed + 1);
                    exit(0)
                }
                field.insert(sand, false);
                placed += 1;
                break;
            }
        }
    }
}
