import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;

public class Day13Part2 {

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));
        int i = 0;
        var ns = new HashMap<Integer, Long>();
        for (var item : lines.get(1).split(",")) {
            if (item.equals("x")) {
                i++;
                continue;
            }
            ns.put(Integer.parseInt(item), (long) ((Integer.parseInt(item) - i) % Integer.parseInt(item)));
            i++;
        }

        long N = 1;
        for (var e : ns.entrySet()) {
            N *= e.getKey();
        }

        var ys = new HashMap<Integer, Long>();
        for (var e : ns.entrySet()) {
            ys.put(e.getKey(), N / e.getKey());
        }

        var inv = new HashMap<Integer, Long>();
        for (var e : ns.entrySet()) {
            BigInteger y = new BigInteger(String.valueOf(ys.get(e.getKey())));
            BigInteger n = new BigInteger(String.valueOf(e.getKey()));
            inv.put(e.getKey(), y.modInverse(n).longValue());
        }

        long sum = 0;
        for (var e : ns.entrySet()) {
            sum += e.getValue() * ys.get(e.getKey()) * inv.get(e.getKey());
        }

        System.out.println(sum % N);


    }


}

