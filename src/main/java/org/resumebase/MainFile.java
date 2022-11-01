package org.resumebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainFile {

    public static void main(String[] args) {
        File file = new File("./.gitignore");

        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file))) {
            System.out.println(inputStreamReader.read());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("that's it");
    }
}
