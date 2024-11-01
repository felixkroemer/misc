use scan_fmt::scan_fmt;
use std::fs::read_to_string;

fn main() {
    let mut max: i32 = 0;
    let mut sum: i32 = 0;
    for line in read_to_string("input").unwrap().lines() {
        if line.trim().is_empty() {
            if sum > max {
                max = sum
            }
            sum = 0
        } else {
            sum += scan_fmt!(line, "{}\n", i32).unwrap();
        }
    }
    println!{"{max}"}
}
