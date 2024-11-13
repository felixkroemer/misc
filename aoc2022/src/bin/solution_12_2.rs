use std::{
    collections::{HashMap, VecDeque},
    fs::read_to_string,
    u32,
};

fn main() {
    let mut map: Vec<Vec<u32>> = Vec::new();
    let mut end: (u32, u32) = (0, 0);
    let mut a_positions: Vec<(u32, u32)> = Vec::new();
    for (i, line) in read_to_string("input").unwrap().lines().enumerate() {
        let mut r = Vec::new();
        for (j, mut c) in line.chars().enumerate() {
            if c == 'S' {
                c = 'a';
            }
            if c == 'E' {
                c = 'z';
                end = (i.try_into().unwrap(), j.try_into().unwrap());
            }
            if c == 'a' {
                a_positions.push((i as u32, j as u32));
            }
            r.push(c as u32);
        }
        map.push(r);
    }
    let mut min_dist = u32::MAX;
    for a_pos in a_positions {
        println!("{} {}", a_pos.0, a_pos.1);
        let mut queue: VecDeque<(u32, u32)> = VecDeque::new();
        queue.push_back(a_pos);
        let mut steps: HashMap<(u32, u32), u32> = HashMap::new();
        steps.insert(a_pos, 0);
        loop {
            if queue.is_empty() {
                break;
            }
            let field = queue.pop_front().unwrap();
            let s = steps.get(&field).unwrap().clone();
            let dirs: [(i32, i32); 4] = [(0, 1), (0, -1), (1, 0), (-1, 0)];
            let mut dist = u32::MAX;
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
                            dist = s + 1;
                            break;
                        }
                        queue.push_back(new_field);
                        steps.insert(new_field, s + 1);
                    }
                }
            }
            if dist < min_dist {
                min_dist = dist;
                println!("{}", min_dist);
            }
            if dist != u32::MAX {
                println!("break");
                break;
            }
        }
    }
    println!("{}", min_dist)
}
