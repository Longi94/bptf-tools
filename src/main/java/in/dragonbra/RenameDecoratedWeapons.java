package in.dragonbra;

import in.dragonbra.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

public class RenameDecoratedWeapons {

    public static void main(String[] args) throws IOException {
        boolean recursive = false;

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

        FileUtils.walk(System.getProperty("user.dir"), recursive,
                file -> {
                    try {
                        renameFile(file.getAbsolutePath());
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }, "png");
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
}
