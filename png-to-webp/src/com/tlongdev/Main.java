package com.tlongdev;

import java.io.File;
import java.io.IOException;

public class Main {

    public static boolean recursive = false;

    public static void main(String[] args) throws IOException {
        for (String arg : args) {
            switch (arg) {
                case "-r":
                    recursive = true;
                    break;
                default:
                    System.out.println("Unknown parameter: " + arg);
                    System.exit(0);
                    break;
            }
        }

        walk(System.getProperty("user.dir"));
    }

    private static void convertToWebp(String in, String out) throws IOException {
        System.out.println("Converting " + in + "...");
        Runtime.getRuntime().exec(new String[]{"cwebp", "-q", "90", in, "-o", out});
    }

    public static void walk(String path) throws IOException {

        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) return;

        for (File file : list) {
            if (file.isDirectory()) {
                if (recursive) {
                    walk(file.getAbsolutePath());
                }
            } else {

                if (getExtension(file).equals("png")) {
                    convertToWebp(file.getAbsolutePath(), file.getAbsolutePath().replace(".png", ".webp"));
                }
            }
        }
    }

    public static String getExtension(File file) {

        String extension = null;
        String fileName = file.getName();

        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (i > p) {
            extension = fileName.substring(i+1);
        }

        return extension;
    }
}
