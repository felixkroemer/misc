use std::{collections::HashSet, fs::read_to_string};

fn main() {
    let mut items1 = HashSet::new();
    let mut items2 = HashSet::new();
    let mut items3 = HashSet::new();
    let mut sum = 0;
    let mut c = 0;
    for line in read_to_string("input").unwrap().lines() {
        match c % 3 {
            0 => {
                for i in 0..line.len() {
                    items1.insert(line.chars().nth(i).unwrap());
                }
            }
            1 => {
                for i in 0..line.len() {
                    items2.insert(line.chars().nth(i).unwrap());
                }
            }
            2 => {
                for i in 0..line.len() {
                    items3.insert(line.chars().nth(i).unwrap());
                }
            }
            _ => {}
        }

        if c % 3 == 2 {
            let intersection: HashSet<char> = items1.intersection(&items2).cloned().collect();
            let res: HashSet<char>= intersection.intersection(&items3).cloned().collect();
            let c: char = res.iter().next().unwrap().to_owned();
            if c.is_lowercase() {
                sum += c as u32 - 'a' as u32 + 1;
            } else {
                sum += c as u32 - 'A' as u32 + 27;
            }
            items1.clear();
            items2.clear();
            items3.clear();
        }
        c += 1;
    }
    println!("{}", sum)
}
