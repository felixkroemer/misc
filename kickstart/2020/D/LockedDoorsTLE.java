import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class LockedDoorsTLE {

	public static List<Integer> solve(int[] ar, int[] qry) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		int[] lBound = new int[ar.length];
		int[] rBound = new int[ar.length];
		Stack<Integer> s = new Stack<Integer>();
		for (int i = ar.length - 1; i >= 0; i--) {
			while (!s.isEmpty() && ar[i] > ar[s.peek()]) {
				s.pop();
			}
			if (s.isEmpty()) {
				rBound[i] = ar.length;
			} else {
				rBound[i] = s.peek();
			}
			s.push(i);
		}
		s.clear();
		for (int i = 0; i < ar.length; i++) {
			while (!s.isEmpty() && ar[i] > ar[s.peek()]) {
				s.pop();
			}
			if (s.isEmpty()) {
				lBound[i] = -1;
			} else {
				lBound[i] = s.peek();
			}
			s.push(i);
		}

		for (int i = 0; i < qry.length / 2; i++) {
			int room = qry[i * 2] - 1;
			int steps = qry[i * 2 + 1] - 1;
			int left = room - 1;
			int right = room;
			while (true) {
				if (left == -1) {
					res.add(right + steps + 1);
					break;
				} else if (right == ar.length) {
					res.add(left - steps + 2);
					break;
				} else if (lBound[right] < left) {
					int diff = left - lBound[right];
					if (diff >= steps) {
						res.add(left - steps + 2);
						break;
					} else {
						left -= diff;
						steps -= diff;
					}
				} else {
					int diff = rBound[left] - right;
					if (diff >= steps) {
						res.add(right + steps + 1);
						break;
					} else {
						right += diff;
						steps -= diff;
					}
				}
			}
		}

		return res;
	}

	public static void main(String[] args) {
		try {
			Scanner s = new Scanner(System.in);
			int t = s.nextInt();
			int n, q;
			for (int i = 0; i < t; i++) {
				n = s.nextInt() - 1;
				q = s.nextInt();
				int[] ar = new int[n];
				for (int j = 0; j < n; j++) {
					ar[j] = s.nextInt();
				}
				int[] qry = new int[q * 2];
				for (int j = 0; j < q; j++) {
					qry[j * 2] = s.nextInt();
					qry[j * 2 + 1] = s.nextInt();
				}
				List<Integer> res = solve(ar, qry);
				System.out.print(String.format("Case #%d: ", i + 1));
				for (int j = 0; j < res.size(); j++) {
					System.out.print(res.get(j) + " ");
				}
				System.out.println();
			}
			s.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
