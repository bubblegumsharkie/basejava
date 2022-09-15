package org.resumebase;

public class MainString {

    public static void main(String[] args) {
        String[] str = new String[]{"1", "2", "3", "4", "5"};
        StringBuilder sb = new StringBuilder();
        for (String s : str) {
            sb.append(s).append(", ");
        }
        System.out.println(sb);
    }
}
