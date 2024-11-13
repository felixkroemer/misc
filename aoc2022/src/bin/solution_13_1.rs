use std::{cmp, fmt::Display, fs::read_to_string};

use serde::Deserialize;
use serde_json::Number;

#[derive(Debug, Deserialize, Clone)]
#[serde(untagged)]
enum NestedValue {
    Number(i32),
    Array(Vec<NestedValue>),
}

#[derive(Debug)]
enum Res {
    True,
    False,
    Continue,
}

fn compare(left: NestedValue, right: NestedValue) -> Res {
    match (left.clone(), right.clone()) {
        (NestedValue::Number(l), NestedValue::Number(r)) => {
            if l < r {
                return Res::True;
            } else if r < l {
                return Res::False;
            } else {
                return Res::Continue;
            }
        }
        (NestedValue::Number(l), NestedValue::Array(a)) => {
            return compare(
                NestedValue::Array(vec![NestedValue::Number(l)]),
                NestedValue::Array(a),
            );
        }
        (NestedValue::Array(a), NestedValue::Number(r)) => {
            return compare(
                NestedValue::Array(a),
                NestedValue::Array(vec![NestedValue::Number(r)]),
            );
        }
        (NestedValue::Array(l), NestedValue::Array(r)) => {
            for i in 0..cmp::min(l.len(), r.len()) {
                match compare(l.get(i).unwrap().clone(), r.get(i).unwrap().clone()) {
                    Res::True => {
                        return Res::True;
                    }
                    Res::False => return Res::False,
                    Res::Continue => {
                        continue;
                    }
                }
            }
            if r.len() > l.len() {
                return Res::True;
            } else if l.len() > r.len() {
                return Res::False;
            } else {
                return Res::Continue;
            }
        }
        _ => {
            panic!()
        }
    }
}

fn main() {
    let input = read_to_string("input").unwrap();
    let mut lines = input.lines();
    let mut i = 1;
    let mut sum = 0;
    while let Some(line) = lines.next() {
        let left: NestedValue = serde_json::from_str(line).unwrap();
        let right: NestedValue = serde_json::from_str(lines.next().unwrap()).unwrap();
        match compare(left, right) {
            Res::True => {
                sum += i;
            }
            Res::Continue => {
                sum += i;
            }
            _ => {}
        }
        lines.next();
        i += 1;
    }
    println!("{}", sum)
}
