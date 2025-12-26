public class Day25Part1 {

    public static void main(String[] args) throws Exception {

        long n = 20201227;
        long b = 7;

        long current = 1;
        int p1 = 0;
        while(true) {
            if(current==1965712) {
                System.out.println(p1);
                break;
            }
            current = (current * b) % n;
            p1++;
        }
        current = 1;
        for(int i = 0; i<p1; i++) {
            current = (current * 19072108) % n;
        }
        System.out.println(current);
    }
}
