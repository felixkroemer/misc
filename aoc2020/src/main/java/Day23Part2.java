import java.util.*;

public class Day23Part2 {

  static int MIL = 1000000;

  static class Node {
    Node next;
    int val;

    public Node(Node next, int val) {
      this.next = next;
      this.val = val;
    }
  }

  public static void main(String[] args) throws Exception {
    var input = "784235916";
    var c = new LinkedList<Node>();
    for (var s : input.split("")) {
      var node = new Node(null, Integer.parseInt(s));
      c.add(node);
    }
    int k = 10;
    while (c.size() < MIL) {
      c.add(new Node(null, k));
      k++;
    }

    Node prev = null;
    Node first = null;
    for (Node node : c) {
      if (first == null) first = node;
      if (prev != null) prev.next = node;
      prev = node;
    }
    prev.next = first;

    Map<Integer, Node> m = new HashMap<>();
    for (var node : c) {
      m.put(node.val, node);
    }

    var current = m.get(Integer.parseInt(input.split("")[0]));
    for (int i = 0; i < MIL * 10; i++) {
      Node split = current.next;
      current.next = current.next.next.next.next;

      int dst = Math.floorMod(current.val - 2, (MIL)) + 1;
      var dstNode = m.get(dst);
      while (dstNode == split || dstNode == split.next || dstNode == split.next.next) {
        dst = Math.floorMod(dst - 2, (MIL)) + 1;
        dstNode = m.get(dst);
      }
      var n = dstNode.next;
      dstNode.next = split;
      split.next.next.next = n;

      current = current.next;
    }

    System.out.println(1L * m.get(1).next.val * m.get(1).next.next.val);
  }
}
