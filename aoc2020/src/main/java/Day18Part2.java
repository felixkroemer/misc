import java.nio.file.Files;
import java.nio.file.Path;

public class Day18Part2 {

  record Node(Node left, Node right, String op, int val) {}

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("input"));

    long sum = 0;
    for (var line : lines) {
      line = line.replace(" ", "");
      var res = eval(line);
      sum += getVal(res);
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
    int i = src.length() - 1;
    int counter = 0;
    int firstSumSign = -1;
    int firstMult = -1;
    while (i >= 0) {
      if (src.charAt(i) == ')') {
        counter++;
      }
      if (src.charAt(i) == '(') {
        counter--;
      }
      if (src.charAt(i) == '+' && counter == 0 && firstSumSign == -1) {
        firstSumSign = i;
      }
      if (src.charAt(i) == '*' && counter == 0) {
        firstMult = i;
        break;
      }
      i--;
    }
    if (firstMult != -1) {
      return new Node(eval(src.substring(0, firstMult)), eval(src.substring(firstMult + 1)), "*", -1);
    } else {
      if (src.charAt(src.length() - 1) == ')') {
        i = src.length() - 2;
        counter = 1;
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
        var num = Integer.parseInt(src.substring(firstSumSign + 1));
        var other = src.substring(0, firstSumSign);
        return new Node(eval(other), new Node(null, null, "", num), "+", -1);
      }
    }

  }
}
