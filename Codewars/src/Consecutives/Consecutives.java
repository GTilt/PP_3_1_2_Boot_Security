package Consecutives;

import java.util.ArrayList;
import java.util.List;

public class Consecutives {
    public static List<Integer> sumConsecutives(List<Integer> s) {
        List<Integer> res = new ArrayList<>();
        if (s.isEmpty()) {
            return res;
        }

        int current = s.get(0);
        int sum = current;

        for (int i = 1; i < s.size(); i++) {
            if (s.get(i) == current) {
                sum += s.get(i);
            } else {
                res.add(sum);
                current = s.get(i);
                sum = current;
            }
        }
        res.add(sum);
        return res;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(4);
        list.add(4);
        list.add(4);
        list.add(0);
        list.add(4);
        list.add(3);
        list.add(3);
        list.add(1);
        System.out.println(sumConsecutives(list));
    }
}
