use std::{collections::HashSet, fs::read_to_string};

fn main() {
    let mut items = HashSet::new();
    let mut sum = 0;
    for line in read_to_string("input").unwrap().lines() {
        for i in 0..line.len()/2 {
            items.insert(line.chars().nth(i).unwrap());
        }
        for i in line.len()/2..line.len() {
            let c = line.chars().nth(i).unwrap();
            if items.contains(&c) {
                if c.is_lowercase() {
                    sum += c as u32 - 'a' as u32 + 1;
                } else {
                    sum += c as u32 - 'A' as u32 + 27;
                }
                break
            }
        }
        items.clear();
    }
    println!("{}", sum)
}
