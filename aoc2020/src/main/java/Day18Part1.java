import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day18Part1 {

  record Node(Node left, Node right, String op, int val) {}

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("input"));

    long sum = 0;
    for (var line : lines) {
      line = line.replace(" ", "");
      sum += getVal(eval(line));
    }
    System.out.println(sum);
  }

  static long getVal(Node n) {
    if (n.left == null && n.right == null) {
      return n.val;
    } else {
      switch (n.op) {
        case "+" -> {
          return getVal(n.left) + getVal(n.right);
        }
        case "*" -> {
          return getVal(n.left) * getVal(n.right);
        }
      }
    }
    throw new RuntimeException();
  }

  static Node eval(String src) {
    try {
      return new Node(null, null, "", Integer.parseInt(src));
    } catch (NumberFormatException ignored) {
    }
    if (src.charAt(src.length() - 1) == ')') {
      int i = src.length() - 2;
      int counter = 1;
      while (counter > 0) {
        char c = src.charAt(i);
        if (c == ')') counter++;
        else if (c == '(') counter--;
        i--;
      }
      if (i != -1) {
        return new Node(
            eval(src.substring(0, i)),
            eval(src.substring(i + 2, src.length() - 1)),
            "" + src.charAt(i),
            -1);
      } else {
        return eval(src.substring(i + 2, src.length() - 1));
      }
    } else {
      int i = src.length() - 2;
      while (src.charAt(i) != '+' && src.charAt(i) != '*') {
        i--;
      }
      var num = Integer.parseInt(src.substring(i + 1));
      var op = src.charAt(i);
      var other = src.substring(0, i);
      return new Node(eval(other), new Node(null, null, "", num), "" + op, -1);
    }
  }
}
