use scan_fmt::scan_fmt;
use std::fs::read_to_string;

fn main() {
    for i in 0..=4000000 {
        if i % 10000 == 0 {
            println!("{}", i);
        }
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
            let y_dist = i32::abs(i - s_y);
            let x_dist = manhattan_dist - y_dist;
            if y_dist <= manhattan_dist {
                let range = (s_x - x_dist, s_x + x_dist);
                x.push((range.0, 1));
                x.push((range.1, -1));
            }
        }
        x.sort_by_key(|val| val.0);
        let mut height = 0;
        let mut prev = x[0].0;
        for event in x {
            if height == 0 && (event.0 - prev) > 1 {
                println!("{} {} {}", i, prev, event.0);
            }
            height += event.1;
            prev = event.0;
        }
    }
}
