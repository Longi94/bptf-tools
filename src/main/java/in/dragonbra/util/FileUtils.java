package in.dragonbra.util;

import java.io.File;
import java.io.IOException;

/**
 * @author lngtr
 * @since 2017-10-05
 */
public class FileUtils {
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

    public static void walk(String path, boolean recursive, FileFunc fileFunc, String extension) throws IOException {
        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) return;

        for (File file : list) {
            if (file.isDirectory()) {
                if (recursive) {
                    walk(file.getAbsolutePath(), true, fileFunc, extension);
                }
            } else {
                if (extension == null || FileUtils.getExtension(file).equals(extension)) {
                    fileFunc.process(file);
                }
            }
        }
    }
}
