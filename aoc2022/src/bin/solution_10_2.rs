use scan_fmt::scan_fmt;
use std::fs::read_to_string;

fn draw(x: i32, cycle: u32) {
    let c = (cycle -1) % 40;
    if (TryInto::<i32>::try_into(c).unwrap() - x).abs() <= 1 {
        print!("o");
    } else {
        print!(".");
    }
    match cycle {
        40 | 80 | 120 | 160 | 200 | 240 => {
            println!();
        }
        _ => {}
    }
}

fn main() {
    let mut x: i32 = 1;
    let mut cycle = 1;
    for line in read_to_string("input").unwrap().lines() {
        if line.starts_with("addx") {
            let val = scan_fmt!(line, "addx {}", i32).unwrap();
            draw(x, cycle);
            cycle += 1;
            draw(x, cycle);
            x += val;
            cycle += 1;
        } else {
            draw(x, cycle);
            cycle += 1;
        }
    }
}
