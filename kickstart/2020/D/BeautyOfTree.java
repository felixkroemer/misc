import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.List;

public class BeautyOfTree {
	public static double solve(int a, int b, int[] ar) {
		ArrayList<Integer>[] par = new ArrayList[ar.length + 1];
		for (int j = 1; j <= ar.length; j++) {
			if (par[ar[j - 1] - 1] == null) {
				par[ar[j - 1] - 1] = new ArrayList<Integer>();
			}
			par[ar[j - 1] - 1].add(j);
		}
		LinkedList<Integer> path = new LinkedList<>();
		int[] aVisits = new int[ar.length + 1];
		int[] bVisits = new int[ar.length + 1];
		visit(0, par, path, aVisits, bVisits, a, b);
		double avg = 0;
		double n = ar.length + 1;
		for (int i = 0; i < aVisits.length; i++) {
			avg += (aVisits[i] + bVisits[i]) / n - (aVisits[i] / n) * (bVisits[i] / n);
		}
		return avg;
	}

	public static void visit(int k, List<Integer>[] par, LinkedList<Integer> path, int[] aVisits, int[] bVisits, int a,
			int b) {
		path.add(k);
		aVisits[k]++;
		bVisits[k]++;
		if (par[k] != null) {
			for (int i : par[k]) {
				visit(i, par, path, aVisits, bVisits, a, b);
			}
		}
		if (path.size() > a) {
			aVisits[path.get(path.size() - 1 - a)] += aVisits[k];
		}
		if (path.size() > b) {
			bVisits[path.get(path.size() - 1 - b)] += bVisits[k];
		}
		path.removeLast();
	}

	public static void main(String[] args) {
		try {
			Scanner s = new Scanner(System.in);
			int t = s.nextInt();
			int a, b, n;
			for (int i = 0; i < t; i++) {
				n = s.nextInt() - 1;
				a = s.nextInt();
				b = s.nextInt();
				int[] ar = new int[n];
				for (int j = 0; j < n; j++) {
					ar[j] = s.nextInt();
				}
				double res = solve(a, b, ar);
				System.out.println(String.format("Case #%d: %f", i + 1, res));
			}
			s.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
