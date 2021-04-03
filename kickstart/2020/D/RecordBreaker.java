import java.util.Scanner;

public class RecordBreaker {
	public static long solve(int[] ar) {
		int max = -1;
		long res = 0;
		for (int i = 0; i < ar.length - 1; i++) {
			if (ar[i] > max) {
				if (ar[i + 1] < ar[i]) {
					res++;
				}
				max = ar[i];
			}
		}
		if (ar[ar.length - 1] > max) {
			res++;
		}
		return res;
	}

	public static void main(String[] args) {
		try {
			Scanner s = new Scanner(System.in);
			int t = s.nextInt();
			int n;
			for (int i = 0; i < t; i++) {
				n = s.nextInt();
				int[] ar = new int[n];
				for (int j = 0; j < n; j++) {
					ar[j] = s.nextInt();
				}
				long res = solve(ar);
				System.out.println(String.format("Case #%d: %d", i + 1, res));
			}
			s.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
