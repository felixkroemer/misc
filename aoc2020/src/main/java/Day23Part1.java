import java.util.*;

public class Day23Part1 {

  public static void main(String[] args) throws Exception {
    var input = "784235916";
    var c = new LinkedList<Integer>(Arrays.stream(input.split("")).map(Integer::valueOf).toList());
    int x, y, z;
    var current = 0;
    for (int i = 0; i < 100; i++) {
      x = c.remove((current + 1) % c.size());
      if (current == c.size()) {
        current--;
      }
      y = c.remove((current + 1) % c.size());
      if (current == c.size()) {
        current--;
      }
      z = c.remove((current + 1) % c.size());
      if (current == c.size()) {
        current--;
      }

      int dst = Math.floorMod(c.get(current) - 2, (input.length())) + 1;
      while (dst == x || dst == y || dst == z) {
        dst = Math.floorMod(dst - 2, (input.length())) + 1;
      }
      int dstIndex = -1;
      int j = 0;
      for (var val : c) {
        if (val == dst) {
          dstIndex = j;
          break;
        }
        j++;
      }
      c.add(dstIndex + 1, z);
      c.add(dstIndex + 1, y);
      c.add(dstIndex + 1, x);
      if (dstIndex <= current) {
        current += 3;
      }
      current = (current + 1) % c.size();
    }
    var res = String.join("", c.stream().map(String::valueOf).toList());
    var oneIdx = res.indexOf('1');
    System.out.println(res.substring(oneIdx + 1) + res.substring(0, oneIdx));
  }
}
