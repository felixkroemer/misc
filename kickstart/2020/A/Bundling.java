import java.util.HashMap;
import java.util.Scanner;

class Bundling {
    public static void insert(TrieNode t, String s) {
        TrieNode child;
        for (int i = 0; i < s.length(); i++) {
            child = null;
            if ((child = t.getChildren().get(s.charAt(i))) == null) {
                child = new TrieNode(s.charAt(i));
                t.getChildren().put(s.charAt(i), child);
            }
            t = child;
            t.addVisit();
        }
    }

    public static int solve(String[] ar, int k) {
        TrieNode root = new TrieNode((char) 0);
        for (String s : ar) {
            insert(root, s);
        }
        return root.getValue(k);
    }

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            int t = s.nextInt();
            int n, k;
            for (int i = 0; i < t; i++) {
                n = s.nextInt();
                k = s.nextInt();
                s.nextLine();
                String[] ar = new String[n];
                for (int j = 0; j < n; j++) {
                    ar[j] = s.nextLine();
                }
                int res = solve(ar, k);
                System.out.println(String.format("Case #%d: %d", i + 1, res));
            }
            s.close();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}

class TrieNode {
    private char c;
    private HashMap<Character, TrieNode> children;
    private int visits;

    public TrieNode(char c) {
        this.c = c;
        this.visits = 0;
        this.children = new HashMap<Character, TrieNode>();
    }

    public HashMap<Character, TrieNode> getChildren() {
        return this.children;
    }

    public void addVisit() {
        this.visits++;
    }

    public char getChar() {
        return this.c;
    }

    public int getValue(int k) {
        int sum = 0;
        for (TrieNode n : this.children.values()) {
            sum += n.getValue(k);
        }
        return sum += this.visits / k;
    }
}