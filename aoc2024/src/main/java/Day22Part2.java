import io.smallrye.mutiny.Uni;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22Part2 {

    static final long MOD = 16777216;

    public static void main(String[] args) throws Exception {
        Uni.createFrom().item(Files.readAllLines(Path.of("input"))).map(lines -> {
            List<List<Integer>> allPrices = new ArrayList<>();
            List<List<Integer>> allChanges = new ArrayList<>();

            for (var line : lines) {
                long initialNum = Long.parseLong(line);
                List<Integer> prices = getPrices(initialNum);
                List<Integer> changes = getChanges(prices);

                allPrices.add(prices);
                allChanges.add(changes);
            }

            Map<List<Integer>, Integer> results = new HashMap<>();
            for (int buyerIndex = 0; buyerIndex < allChanges.size(); buyerIndex++) {
                List<Integer> changes = allChanges.get(buyerIndex);
                List<Integer> prices = allPrices.get(buyerIndex);

                for (int i = 0; i <= changes.size() - 4; i++) {
                    List<Integer> sequence = Arrays.asList(changes.get(i), changes.get(i + 1), changes.get(i + 2), changes.get(i + 3));
                    int price = prices.get(i + 4);
                    results.putIfAbsent(sequence, 0);

                    boolean alreadyProcessed = false;
                    for (int j = 0; j < i; j++) {
                        if (changes.get(j).intValue() == sequence.get(0) && changes.get(j + 1)
                                                                                   .intValue() == sequence.get(1) && changes.get(j + 2)
                                                                                                                            .intValue() == sequence.get(2) && changes.get(j + 3)
                                                                                                                                                                     .intValue() == sequence.get(3)) {
                            alreadyProcessed = true;
                            break;
                        }
                    }

                    if (!alreadyProcessed) {
                        results.put(sequence, results.get(sequence) + price);
                    }
                }
            }

            int max = 0;
            for (var entry : results.entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                }
            }
            return max;
        }).subscribe().with(System.out::println);
    }

    static List<Integer> getPrices(long first) {
        List<Integer> prices = new ArrayList<>(2001);
        long num = first;
        for (int i = 0; i < 2001; i++) {
            prices.add((int) (num % 10));
            num = calculate(num);
        }
        return prices;
    }

    static List<Integer> getChanges(List<Integer> prices) {
        List<Integer> changes = new ArrayList<>(prices.size() - 1);
        for (int i = 1; i < prices.size(); i++) {
            changes.add(prices.get(i) - prices.get(i - 1));
        }
        return changes;
    }

    static long calculate(long secretNumber) {
        long m = secretNumber << 6;
        secretNumber = (secretNumber ^ m) % MOD;
        long d = secretNumber >> 5;
        secretNumber = (secretNumber ^ d) % MOD;
        long mm = secretNumber << 11;
        secretNumber = (secretNumber ^ mm) % MOD;
        return secretNumber;
    }
}