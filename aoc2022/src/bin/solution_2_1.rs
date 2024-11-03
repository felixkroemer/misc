use scan_fmt::scan_fmt;
use std::fs::read_to_string;

fn main() {
    let mut score = 0;
    for line in read_to_string("input").unwrap().lines() {
        let (x, y) = scan_fmt!(line, "{}{}", char, char).unwrap();
        match y {
            'X' => {
                score += 1;
                match x {
                    'A' => score += 3,
                    'C' => score += 6,
                    _ => {}
                }
            }
            'Y' => {
                score += 2;
                match x {
                    'A' => score += 6,
                    'B' => score += 3,
                    _ => {}
                }
            }
            'Z' => {
                score += 3;
                match x {
                    'B' => score += 6,
                    'C' => score += 3,
                    _ => {}
                }
            }
            _ => {}
        }
    }
    println! {"{}", score}
}
