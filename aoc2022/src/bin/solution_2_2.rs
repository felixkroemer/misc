use scan_fmt::scan_fmt;
use std::fs::read_to_string;

fn main() {
    let mut score = 0;
    for line in read_to_string("input").unwrap().lines() {
        let (x, y) = scan_fmt!(line, "{}{}", char, char).unwrap();
        match y {
            'X' => {
                match x {
                    'A' => score += 3,
                    'B' => score += 1,
                    'C' => score += 2,
                    _ => {}
                }
            }
            'Y' => {
                score += 3;
                match x {
                    'A' => score += 1,
                    'B' => score += 2,
                    'C' => score += 3,
                    _ => {}
                }
            }
            'Z' => {
                score += 6;
                match x {
                    'A' => score += 2,
                    'B' => score += 3,
                    'C' => score += 1,
                    _ => {}
                }
            }
            _ => {}
        }
    }
    println! {"{}", score}
}
