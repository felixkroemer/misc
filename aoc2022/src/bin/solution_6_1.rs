use std::{collections::HashSet, fs::read_to_string};

fn main() {
    let line = read_to_string("input").unwrap();
    let mut window = Vec::new();
    for (i, c) in line.chars().enumerate() {
        if window.len() < 4 {
            window.push(c);
        } else {
            let mut unique = HashSet::new();
            if window.iter().all(|c| unique.insert(c)) {
                println!("{}", i);
                break;
            } else {
                window.remove(0);
                window.push(c);
            }
        }
    }
}
