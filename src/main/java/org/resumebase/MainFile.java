package org.resumebase;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {

        File file = new File("./src");
        listAllFilesIn(file);

    }

    private static void listAllFilesIn(File path) {
        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(file.getName());
                    listAllFilesIn(file);
                } else {
                    System.out.println(file.getName());
                }
            }
        }
    }
}
