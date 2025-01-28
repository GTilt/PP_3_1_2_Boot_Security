package Multiplesof3or5;

public class Solution {
    public int solution(int number) {
        int totalSum = 0;
        for (int i = 0; i < number; i++) {
            if (i % 3 == 0 || i % 5 == 0) {
                totalSum += i;
            }
        }
        return totalSum;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.solution(25));
    }
}
