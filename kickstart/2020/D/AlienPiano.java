import java.util.Scanner;

public class AlienPiano {
	public static int solve(int[] ar) {
		int mistakes = 0;
		int current = 0;
		for (int i = 1; i < ar.length; i++) {
			if (ar[i] > ar[i - 1]) {
				if (current <= 0) {
					current = 1;
				} else {
					current++;
				}
			}
			if (ar[i] < ar[i - 1]) {
				if (current >= 0) {
					current = -1;
				} else {
					current--;
				}
			}
			if (current >= 4 || current <= -4) {
				mistakes++;
				current = 0;
			}
		}
		return mistakes;
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
				int res = solve(ar);
				System.out.println(String.format("Case #%d: %d", i + 1, res));
			}
			s.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
