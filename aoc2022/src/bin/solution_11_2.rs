use scan_fmt::scan_fmt;
use std::{fs::read_to_string, string};

struct Monkey {
    items: Vec<u64>,
    operation: Box<dyn Fn(u64) -> u64>,
    test_divisor: u64,
    true_monkey_index: u64,
    false_monkey_index: u64,
    inspected_count: u64,
}

fn main() {
    let mut monkeys: Vec<Monkey> = Vec::new();
    let input = read_to_string("input").unwrap();
    let mut lines = input.lines();
    while let Some(mut line) = lines.next() {
        let numbers: Vec<u64> = lines
            .next()
            .unwrap()
            .strip_prefix("  Starting items: ")
            .unwrap_or("")
            .split(", ")
            .filter_map(|s| s.parse().ok())
            .collect();
        line = lines.next().unwrap();
        let (left, op, right) =
            scan_fmt!(line, "  Operation: new = {} {} {}", String, String, String).unwrap();
        line = lines.next().unwrap();
        let operation = Box::new(move |input| {
            let l: u64 = match left.as_str() {
                "old" => input,
                _ => left.parse().unwrap(),
            };
            let r: u64 = match right.as_str() {
                "old" => input,
                _ => right.parse().unwrap(),
            };
            match op.as_str() {
                "*" => l * r,
                "+" => l + r,
                _ => 0,
            }
        });
        let divisor = scan_fmt!(line, "  Test: divisible by {}", u64).unwrap();
        line = lines.next().unwrap();
        let true_monkey_index = scan_fmt!(line, "    If true: throw to monkey {}", u64).unwrap();
        line = lines.next().unwrap();
        let false_monkey_index = scan_fmt!(line, "    If false: throw to monkey {}", u64).unwrap();
        let monkey = Monkey {
            items: numbers,
            operation: operation,
            test_divisor: divisor,
            true_monkey_index,
            false_monkey_index,
            inspected_count: 0,
        };
        monkeys.push(monkey);
        lines.next();
    }
    let mut divisor_prod = 1;
    for monkey in  monkeys.iter() {
        divisor_prod *= monkey.test_divisor;
    }
    for _ in 0..10000 {
        for i in 0..monkeys.len() {
            let monkey = monkeys.get_mut(i).unwrap();
            let mut transfers: Vec<(usize, u64)> = Vec::new();
            for &item in monkey.items.iter() {
                monkey.inspected_count += 1;
                let val = (monkey.operation)(item) % divisor_prod;
                let target_index = if val % monkey.test_divisor == 0 {
                    monkey.true_monkey_index
                } else {
                    monkey.false_monkey_index
                };
                transfers.push((target_index.try_into().unwrap(), val));
            }
            monkey.items.clear();
            for (target_index, value) in transfers {
                if let Some(target_monkey) = monkeys.get_mut(target_index) {
                    target_monkey.items.push(value);
                }
            }
        }
    }
    monkeys.sort_by_key(|x| x.inspected_count);
    monkeys.reverse();
    println!(
        "{}",
        monkeys[0].inspected_count * monkeys[1].inspected_count
    );
}
