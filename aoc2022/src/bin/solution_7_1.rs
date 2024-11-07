use scan_fmt::scan_fmt;
use std::{collections::HashMap, fs::read_to_string};

fn main() {
    let mut dirs = Vec::new();
    let mut sizes: HashMap<String, (Vec<String>, u32)> = HashMap::new();
    for line in read_to_string("input").unwrap().lines() {
        if line.starts_with("$") {
            if line.contains("cd") {
                let dir = scan_fmt!(line, "$ cd {}", String).unwrap();
                if dir == ".." {
                    dirs.pop().unwrap();
                } else {
                    dirs.push(dir.clone());
                    if !sizes.contains_key(&dirs.join("/")) {
                        sizes.insert(dirs.join("/"), (Vec::new(), 0));
                    }
                }
            }
        } else {
            if line.starts_with("dir") {
                continue;
            }
            let size: u32 = line.split(' ').next().unwrap().parse().unwrap();
            for i in 1..dirs.len() + 1 {
                let key = dirs.iter().take(i).cloned().collect::<Vec<_>>().join("/");
                sizes.get_mut(&key).unwrap().1 += size;
            }
        }
    }
    let mut sum = 0;
    for x in sizes {
        if x.1 .1 < 100000 {
            sum += x.1 .1;
        }
    }
    println!("{}", sum);
}
