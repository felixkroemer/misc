use scan_fmt::scan_fmt;
use std::fs::read_to_string;

fn main() {
    let s1: Vec<char> = vec!['D', 'T', 'W', 'F', 'J', 'S', 'H', 'N'];
    let s2: Vec<char> = vec!['H', 'R', 'P', 'Q', 'T', 'N', 'B', 'G'];
    let s3: Vec<char> = vec!['L', 'Q', 'V'];
    let s4: Vec<char> = vec!['N', 'B', 'S', 'W', 'R', 'Q'];
    let s5: Vec<char> = vec!['N', 'D', 'F', 'T', 'V', 'M', 'B'];
    let s6: Vec<char> = vec!['M', 'D', 'B', 'V', 'H', 'T', 'R'];
    let s7: Vec<char> = vec!['D', 'B', 'Q', 'J'];
    let s8: Vec<char> = vec!['D', 'N', 'J', 'V', 'R', 'Z', 'H', 'Q'];
    let s9: Vec<char> = vec!['B', 'N', 'H', 'M', 'S'];
    let mut s = vec![s1, s2, s3, s4, s5, s6, s7, s8, s9];
    for line in read_to_string("input").unwrap().lines() {
        if line.starts_with("move") {
            let (num, from, to) = scan_fmt!(line, "move {} from {} to {}", usize, usize, usize).unwrap();
            for _ in 0..num {
                let x = s[from-1].pop().unwrap();
                s[to-1].push(x);
            }
        }
    }
    for i in 0..9 {
        print!("{}", s[i].last().unwrap());
    }
}
