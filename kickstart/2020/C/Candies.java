import java.util.Scanner;

class Candies {
	public static long solve(int[] ar, int[][] ops) {
		long sum = 0;
		long[] prefix = new long[2 * ar.length];
		long[] multPrefix = new long[2 * ar.length];
		for (int i = 0; i < ar.length; i++) {
			prefix[ar.length + i] = ar[i] * (i % 2 == 0 ? 1 : -1);
			multPrefix[ar.length + i] = ar[i] * (i + 1) * (i % 2 == 0 ? 1 : -1);
		}
		for (int i = ar.length - 1; i > 0; i--) {
			prefix[i] = prefix[i * 2] + prefix[i * 2 + 1];
			multPrefix[i] = multPrefix[i * 2] + multPrefix[i * 2 + 1];
		}

		for (int i = 0; i < ops.length; i++) {
			if (ops[i][0] == 0) {
				long res = query(ops[i][1] - 1, ops[i][2], multPrefix);
				res -= (ops[i][1] - 1) * query(ops[i][1] - 1, ops[i][2], prefix);
				if (ops[i][1] % 2 == 0) {
					res *= -1;
				}
				sum += res;
			} else {
				modify(ops[i][1] - 1, ops[i][2] * (ops[i][1] % 2 == 0 ? -1 : 1), prefix);
				modify(ops[i][1] - 1, ops[i][1] * ops[i][2] * (ops[i][1] % 2 == 0 ? -1 : 1), multPrefix);
			}
		}

		return sum;
	}

	public static void modify(int i, int v, long[] ar) {
		i += ar.length / 2;
		ar[i] = v;
		while (i > 1) {
			i = i / 2;
			ar[i] = ar[2 * i] + ar[2 * i + 1];
		}
	}

	public static long query(int l, int r, long[] ar) {
		l += ar.length / 2;
		r += ar.length / 2;
		long res = 0;
		while (l < r) {
			if (l % 2 == 1) {
				res += ar[l++];
			}
			if (r % 2 == 1) {
				res += ar[--r];
			}
			l = l / 2;
			r = r / 2;
		}
		return res;
	}

	public static void main(String[] args) {
		try {
			Scanner s = new Scanner(System.in);
			int t = s.nextInt();
			int n, q;
			for (int i = 0; i < t; i++) {
				n = s.nextInt();
				q = s.nextInt();
				int[] ar = new int[n];
				for (int j = 0; j < n; j++) {
					ar[j] = s.nextInt();
				}
				int[][] ops = new int[q][3];
				for (int k = 0; k < q; k++) {
					ops[k][0] = s.next().charAt(0) == 'Q' ? 0 : 1;
					ops[k][1] = s.nextInt();
					ops[k][2] = s.nextInt();
				}
				long res = solve(ar, ops);
				System.out.println(String.format("Case #%d: %d", i + 1, res));
			}
			s.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
