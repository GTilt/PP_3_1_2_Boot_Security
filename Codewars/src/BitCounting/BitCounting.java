package BitCounting;

public class BitCounting {
    public static int countBits(int n) {
        return (Integer.bitCount(n));
    }

    public static void main(String[] args) {
        System.out.println(countBits(7));
    }
}
