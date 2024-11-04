use scan_fmt::scan_fmt;
use std::fs::read_to_string;

fn main() {
    let mut sum = 0;
    for line in read_to_string("input").unwrap().lines() {
        let (a, b, c, d) = scan_fmt!(line, "{}-{},{}-{}", u32, u32, u32, u32).unwrap();
        if (c >= a && d <= b) || (a >= c && b <= d) {
            sum += 1;
        }
    }
    println!("{}", sum)
}
