use scan_fmt::scan_fmt;
use std::fs::read_to_string;

fn main() {
    let mut max1: i32 = 0;
    let mut max2: i32 = 0;
    let mut max3: i32 = 0;
    let mut sum: i32 = 0;
    for line in read_to_string("input").unwrap().lines() {
        if line.trim().is_empty() {
            if sum > max1 {
                max3 = max2;
                max2 = max1;
                max1 = sum
            } else if sum > max2 {
                max3 = max2;
                max2 = sum;
            } else if sum > max3 {
                max1 = sum;
            }
            sum = 0
        } else {
            sum += scan_fmt!(line, "{}\n", i32).unwrap();
        }
    }
    let res = max1 + max2 + max3;
    println! {"{res}"}
}
