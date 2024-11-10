use scan_fmt::scan_fmt;
use std::{collections::HashSet, fs::read_to_string, u32};

fn main() {
    let mut h = (0, 0);
    let mut t = [
        (0, 0),
        (0, 0),
        (0, 0),
        (0, 0),
        (0, 0),
        (0, 0),
        (0, 0),
        (0, 0),
        (0, 0),
    ];
    let mut positions: HashSet<(i32, i32)> = HashSet::new();
    positions.insert(t[8].clone());
    for line in read_to_string("input").unwrap().lines() {
        let (dir, steps) = scan_fmt!(line, "{} {}", char, u32).unwrap();
        for _ in 0..steps {
            match dir {
                'U' => {
                    h = (h.0 - 1, h.1);
                }
                'D' => {
                    h = (h.0 + 1, h.1);
                }
                'L' => {
                    h = (h.0, h.1 - 1);
                }
                'R' => {
                    h = (h.0, h.1 + 1);
                }
                _ => {}
            }
            let mut next = h;
            for i in 0..9 {
                let p = t[i];
                if next.0 == p.0 {
                    if next.1 - p.1 == 2 {
                        t[i] = (p.0, p.1 + 1)
                    } else if next.1 - p.1 == -2 {
                        t[i] = (p.0, p.1 - 1)
                    }
                } else if next.1 == p.1 {
                    if next.0 - p.0 == 2 {
                        t[i] = (p.0 + 1, p.1)
                    } else if next.0 - p.0 == -2 {
                        t[i] = (p.0 - 1, p.1)
                    }
                } else {
                    let diff = (next.0 - p.0, next.1 - p.1);
                    match diff {
                        (2, 1) => t[i] = (p.0 + 1, p.1 + 1),
                        (2, -1) => t[i] = (p.0 + 1, p.1 - 1),
                        (-2, 1) => t[i] = (p.0 - 1, p.1 + 1),
                        (-2, -1) => t[i] = (p.0 - 1, p.1 - 1),
                        (1, 2) => t[i] = (p.0 + 1, p.1 + 1),
                        (-1, 2) => t[i] = (p.0 - 1, p.1 + 1),
                        (1, -2) => t[i] = (p.0 + 1, p.1 - 1),
                        (-1, -2) => t[i] = (p.0 - 1, p.1 - 1),
                        (2, 2) => t[i] = (p.0 + 1, p.1 + 1),
                        (2, -2) => t[i] = (p.0 + 1, p.1 - 1),
                        (-2, 2) => t[i] = (p.0 - 1, p.1 + 1),
                        (-2, -2) => t[i] = (p.0 - 1, p.1 - 1),
                        _ => {}
                    }
                }
                next = t[i];
            }
            positions.insert(t[8].clone());
        }
    }
    println!("{}", positions.len());
}
