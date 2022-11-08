package org.resumebase;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {

        File file = new File("./src");
        listAllFilesIn(file, 0);

    }

    private static void listAllFilesIn(File path, int level) {
        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    for (int i = 0; i < level; i++) {
                        System.out.print(" ");
                    }
                    System.out.println(file.getName());
                    listAllFilesIn(file, ++level);
                } else {
                    for (int i = 0; i < level; i++) {
                        System.out.print(" ");
                    }
                    System.out.println("-" + file.getName());
                }
            }
        }
    }
}
