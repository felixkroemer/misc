use scan_fmt::scan_fmt;
use std::{
    collections::{HashMap, VecDeque}, fs::read_to_string, process::exit, string
};

fn main() {
    let mut map: Vec<Vec<u32>> = Vec::new();
    let mut start: (u32, u32) = (0, 0);
    let mut end: (u32, u32) = (0, 0);
    for (i, line) in read_to_string("input").unwrap().lines().enumerate() {
        let mut r = Vec::new();
        for (j, mut c) in line.chars().enumerate() {
            if c == 'S' {
                c = 'a';
                start = (i.try_into().unwrap(), j.try_into().unwrap());
            }
            if c == 'E' {
                c = 'z';
                end = (i.try_into().unwrap(), j.try_into().unwrap());
            }
            r.push(c as u32);
        }
        map.push(r);
    }
    let mut queue: VecDeque<(u32, u32)> = VecDeque::new();
    queue.push_back(start);
    let mut steps: HashMap<(u32, u32), u32> = HashMap::new();
    steps.insert(start, 0);
    loop {
        let field = queue.pop_front().unwrap();
        println!("{} {}", field.0, field.1);
        let s = steps.get(&field).unwrap().clone();
        let dirs: [(i32, i32); 4] = [(0, 1), (0, -1), (1, 0), (-1, 0)];
        for (x_dir, y_dir) in dirs {
            let x = field.0 as i32 + x_dir;
            let y = field.1 as i32 + y_dir;
            if x < 0 || x > map.len() as i32 - 1 || y < 0 || y > map[0].len() as i32 - 1 {
                continue;
            } else {
                let new_field = (x as u32, y as u32);
                if steps.contains_key(&new_field) {
                    continue;
                }
                if map[new_field.0 as usize][new_field.1 as usize]
                    <= map[field.0 as usize][field.1 as usize] + 1
                {
                    if new_field == end {
                        println!("{}", s + 1);
                        exit(0);
                    }
                    queue.push_back(new_field);
                    steps.insert(new_field, s + 1);
                }
            }
        }
    }
}
