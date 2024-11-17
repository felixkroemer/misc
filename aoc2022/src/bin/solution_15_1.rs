use scan_fmt::scan_fmt;
use std::{fs::read_to_string, string};

fn main() {
    let row = 2000000;
    let mut x: Vec<(i32, i32)> = Vec::new();
    for line in read_to_string("input").unwrap().lines() {
        let (s_x, s_y, b_x, b_y) = scan_fmt!(
            line,
            "Sensor at x={}, y={}: closest beacon is at x={}, y={}",
            i32,
            i32,
            i32,
            i32
        )
        .unwrap();
        let manhattan_dist = i32::abs(s_x - b_x) + i32::abs(s_y - b_y);
        let y_dist = i32::abs(row - s_y);
        let x_dist = manhattan_dist - y_dist;
        if y_dist <= manhattan_dist {
            let range = (s_x - x_dist, s_x + x_dist);
            x.push((range.0, 1));
            x.push((range.1, -1));
            println!(
                "{} {}  {} {} {}: {} {}",
                s_x, s_y, manhattan_dist, x_dist, y_dist, range.0, range.1
            );
        }
    }
    x.sort_by_key(|val| val.0);
    let mut height = 0;
    let mut count = 0;
    let mut prev = x[0].0;
    for event in x {
        if height > 0 {
            count += event.0 - prev
        }
        height += event.1;
        prev = event.0;
    }
    print!("{}", count);
}
