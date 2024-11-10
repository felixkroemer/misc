use scan_fmt::scan_fmt;
use std::fs::read_to_string;

fn main() {
    let mut sum: i32 = 0;
    let mut x: i32 = 1;
    let mut cycle = 1;
    for line in read_to_string("input").unwrap().lines() {
        if line.starts_with("addx") {
            match cycle {
                20 | 60 | 100 | 140 | 180 | 220 => {
                    sum = TryInto::<i32>::try_into(cycle).unwrap() * x + sum;
                }
                _ => {}
            }
            let val = scan_fmt!(line, "addx {}", i32).unwrap();
            cycle += 1;
            println!("{}: {}", cycle, x);
            match cycle {
                20 | 60 | 100 | 140 | 180 | 220 => {
                    sum = TryInto::<i32>::try_into(cycle).unwrap() * x + sum;
                }
                _ => {}
            }
            x += val;
            cycle += 1;
            println!("{}: {}", cycle, x);
        } else {
            match cycle {
                20 | 60 | 100 | 140 | 180 | 220 => {
                    sum = TryInto::<i32>::try_into(cycle).unwrap() * x + sum;
                }
                _ => {}
            }
            cycle += 1;
            println!("{}: {}", cycle, x);
        }
    }
    println!("{}", sum);
}
