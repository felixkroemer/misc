use scan_fmt::scan_fmt;
use std::{collections::HashSet, fs::read_to_string, u32};

fn main() {
    let mut h = (0, 0);
    let mut t = (0, 0);
    let mut positions: HashSet<(i32, i32)> = HashSet::new();
    positions.insert(t.clone());
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
            if h.0 == t.0 {
                if (h.1 - t.1) == 2 {
                    t = (t.0, t.1 + 1)
                } else if h.1 - t.1 == -2 {
                    t = (t.0, t.1 - 1)
                }
            } else if h.1 == t.1 {
                if (h.0 - t.0 == 2) {
                    t = (t.0 + 1, t.1)
                } else if h.0 - t.0 == -2 {
                    t = (t.0 - 1, t.1)
                }
            } else {
                let diff = (h.0 - t.0, h.1 - t.1);
                match diff {
                    (2, 1) => t = (t.0 + 1, t.1 + 1),
                    (2, -1) => t = (t.0 + 1, t.1 - 1),
                    (-2, 1) => t = (t.0 - 1, t.1 + 1),
                    (-2, -1) => t = (t.0 - 1, t.1 - 1),
                    (1, 2) => t = (t.0 + 1, t.1 + 1),
                    (-1, 2) => t = (t.0 - 1, t.1 + 1),
                    (1, -2) => t = (t.0 + 1, t.1 - 1),
                    (-1, -2) => t = (t.0 - 1, t.1 - 1),
                    _ => {}
                }
            }
            positions.insert(t.clone());
        }
    }
    println!("{}", positions.len());
}
