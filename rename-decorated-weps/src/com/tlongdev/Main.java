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

    private static void renameFile(String file) throws IOException {

        System.out.println("Renaming " + file + "...");

        File oldFile = new File(file);
        File newFile;

        if (oldFile.getName().contains("Battle_Scarred")) {
            newFile = new File(oldFile.getParent() + "/1065353216.png");
        } else if (oldFile.getName().contains("Factory_New")) {
            newFile = new File(oldFile.getParent() + "/1045220557.png");
        } else if (oldFile.getName().contains("Field-Tested")) {
            newFile = new File(oldFile.getParent() + "/1058642330.png");
        } else if (oldFile.getName().contains("Minimal_Wear")) {
            newFile = new File(oldFile.getParent() + "/1053609165.png");
        } else if (oldFile.getName().contains("Well-Worn")) {
            newFile = new File(oldFile.getParent() + "/1061997773.png");
        } else {
            return;
        }

        if (!newFile.exists()) {
            boolean success = oldFile.renameTo(newFile);
            if (!success) {
                throw new IOException("failed to rename " + oldFile.toString() + " to " + newFile.toString());
            }
        } else {
            throw new IOException(newFile.toString() + " already exists...");
        }
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
                    renameFile(file.getAbsolutePath());
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
            extension = fileName.substring(i + 1);
        }

        return extension;
    }
}
