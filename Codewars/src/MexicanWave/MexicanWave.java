package MexicanWave;

import java.util.Arrays;

public class MexicanWave {
    public static String[] wave(String str) {
        int count = str.length();

        String[] waveArray = new String[count];
        int index = 0;

        for (int i = 0; i < str.length(); i++) {
            StringBuilder sb = new StringBuilder(str);
            sb.setCharAt(i, Character.toUpperCase(str.charAt(i)));
            waveArray[index++] = sb.toString();
        }
        return waveArray;
    }
    /*public static void main(String[] args) {
        System.out.println(Arrays.toString(wave("hello")));
    }*/
}
