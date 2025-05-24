import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Day18Part2 {

    static class Snailfish {
        Snailfish left;
        Snailfish right;
        Snailfish parent;
        int value;
        boolean isRegular;

        public String toString() {
            if (isRegular) {
                return "" + this.value;
            } else {
                return "[" + this.left + "," + this.right + "]";
            }
        }

        public void setParent(Snailfish parent) {
            this.parent = parent;
        }

        public Snailfish(Snailfish left, Snailfish right) {
            this.left = left;
            this.right = right;
            this.isRegular = false;
        }

        public Snailfish(int value) {
            this.value = value;
            this.isRegular = true;
        }

        public Snailfish add(Snailfish right) {
            var n = new Snailfish(this, right);
            this.setParent(n);
            right.setParent(n);
            while (true) {
                var reduced = n.reduce();
                if (!reduced) {
                    if (!n.s()) {
                        break;
                    }
                }
            }
            return n;
        }

        Optional<Snailfish> getParent() {
            return Optional.ofNullable(this.parent);
        }

        boolean addLeft(int value, Snailfish from) {
            if (this.isRegular) {
                this.value += value;
                return true;
            }
            if (from == this.left) {
                if (this.parent == null) {
                    return false;
                } else {
                    return this.parent.addLeft(value, this);
                }
            } else if (from == this.right) {
                return this.left.addLeft(value, this);
            } else if (from == this.parent) {
                return this.right.addLeft(value, this);
            } else {
                throw new RuntimeException();
            }
        }

        boolean addRight(int value, Snailfish from) {
            if (this.isRegular) {
                this.value += value;
                return true;
            }
            if (from == this.right) {
                if (this.parent == null) {
                    return false;
                } else {
                    return this.parent.addRight(value, this);
                }
            } else if (from == this.left) {
                return this.right.addRight(value, this);
            } else if (from == this.parent) {
                return this.left.addRight(value, this);
            } else {
                throw new RuntimeException();
            }
        }

        boolean reduce() {
            if (!left.isRegular && left.reduce()) {
                return true;
            }

            if (left.isRegular && right.isRegular && this.getParent().flatMap(Snailfish::getParent).flatMap(Snailfish::getParent).flatMap(Snailfish::getParent).isPresent()) {
                if (this.parent != null) {
                    this.parent.addRight(this.right.value, this);
                    this.parent.addLeft(this.left.value, this);
                }
                this.left = null;
                this.right = null;
                this.isRegular = true;
                this.value = 0;
                return true;
            }

            return !right.isRegular && right.reduce();
        }

        boolean s() {
            if (this.left != null && this.left.s()) {
                return true;
            }
            if (shouldSplit()) {
                this.split();
                return true;
            }
            return this.right != null && this.right.s();

        }

        int magnitude() {
            if (this.isRegular) {
                return value;
            } else {
                return 3 * this.left.magnitude() + 2 * this.right.magnitude();
            }
        }

        boolean shouldSplit() {
            return this.isRegular && this.value >= 10;
        }

        void split() {
            this.left = new Snailfish(value / 2);
            this.right = new Snailfish(value % 2 == 1 ? value / 2 + 1 : value / 2);
            this.left.setParent(this);
            this.right.setParent(this);
            this.value = 0;
            this.isRegular = false;
        }


    }

    public static void main(String[] args) throws Exception {
        var lines = Files.readAllLines(Path.of("input"));

        var list = new ArrayList<Snailfish>();
        for (var line : lines) {
            list.add(parse(line));
        }
        int max = 0;
        for (var line : lines) {
            for (var other : lines) {
                if (!line.equals(other)) {
                    var sum = parse(line).add(parse(other));
                    var mag = sum.magnitude();
                    if (mag > max) {
                        max = mag;
                    }
                }
            }
        }


        System.out.println(max);
    }

    static Snailfish parse(String s) {
        try {
            return new Snailfish(Integer.parseInt(s));
        } catch (NumberFormatException ignored) {
        }
        int c = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ',' && c == 1) {
                var left = parse(s.substring(1, i));
                var right = parse(s.substring(i + 1, s.length() - 1));
                var snailfish = new Snailfish(left, right);
                left.setParent(snailfish);
                right.setParent(snailfish);
                return snailfish;
            } else if (s.charAt(i) == '[') {
                c++;
            } else if (s.charAt(i) == ']') {
                c--;
            }
        }
        throw new RuntimeException();
    }
}
