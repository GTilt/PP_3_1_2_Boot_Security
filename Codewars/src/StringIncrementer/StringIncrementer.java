package StringIncrementer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class StringIncrementer {
    public static String incrementString(String str) {
        int numStartIndex = str.length();

        for (int i = str.length() - 1; i >= 0; i--) {
            if (!Character.isDigit(str.charAt(i))) {
                numStartIndex = i + 1;
                break;
            }
        }

        String textPart = str.substring(0, numStartIndex);
        String numPart = str.substring(numStartIndex);

        if (numPart.isEmpty()) {
            return textPart + "1";
        }

        int number = Integer.parseInt(numPart);
        number++;

        String incrementedNum = String.format("%0" + numPart.length() + "d", number);

        List <? extends Integer> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);
        Iterator<Integer> iterator = list1.iterator();
        CopyOnWriteArrayList<Integer> list2 = new CopyOnWriteArrayList<>();
        while (iterator.hasNext()) {
            list1.add(2);
            iterator.next();
        }
        return incrementedNum;
    }

    /*public static void main(String[] args) {
        System.out.println(incrementString("hello009"));
    }*/
}
