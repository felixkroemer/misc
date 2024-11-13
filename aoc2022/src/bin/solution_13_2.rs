use std::{
    cmp::{self, Ordering},
    fmt::Display,
    fs::read_to_string,
};

use serde::Deserialize;
use serde_json::Number;

#[derive(Debug, Deserialize, Clone)]
#[serde(untagged)]
enum NestedValue {
    Number(i32),
    Array(Vec<NestedValue>),
}

fn compare(left: &NestedValue, right: &NestedValue) -> Ordering {
    match (left.clone(), right.clone()) {
        (NestedValue::Number(l), NestedValue::Number(r)) => {
            if l < r {
                return Ordering::Greater;
            } else if r < l {
                return Ordering::Less;
            } else {
                return Ordering::Equal;
            }
        }
        (NestedValue::Number(l), NestedValue::Array(a)) => {
            return compare(
                &NestedValue::Array(vec![NestedValue::Number(l)]),
                &NestedValue::Array(a),
            );
        }
        (NestedValue::Array(a), NestedValue::Number(r)) => {
            return compare(
                &NestedValue::Array(a),
                &NestedValue::Array(vec![NestedValue::Number(r)]),
            );
        }
        (NestedValue::Array(l), NestedValue::Array(r)) => {
            for i in 0..cmp::min(l.len(), r.len()) {
                match compare(l.get(i).unwrap(), r.get(i).unwrap()) {
                    Ordering::Greater => {
                        return Ordering::Greater;
                    }
                    Ordering::Less => return Ordering::Less,
                    Ordering::Equal => {
                        continue;
                    }
                }
            }
            if r.len() > l.len() {
                return Ordering::Greater;
            } else if l.len() > r.len() {
                return Ordering::Less;
            } else {
                return Ordering::Equal;
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
    let mut items: Vec<NestedValue> = Vec::new();
    while let Some(line) = lines.next() {
        items.push(serde_json::from_str(line).unwrap());
        items.push(serde_json::from_str(lines.next().unwrap()).unwrap());
        lines.next();
    }
    let two: NestedValue = serde_json::from_str("[[2]]").unwrap();
    items.push(two.clone());
    let six: NestedValue = serde_json::from_str("[[6]]").unwrap();
    items.push(six.clone());
    items.sort_by(compare);
    items.reverse();
    let mut a = 0;
    let mut b = 0;
    for (i, item) in items.into_iter().enumerate() {
        if compare(&item, &two) == Ordering::Equal {
            a = i;
        }
        if compare(&item, &six) == Ordering::Equal {
            b = i;
        }
    }
    println!("{}", (a + 1) * (b + 1));
}
