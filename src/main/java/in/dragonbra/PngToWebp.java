package in.dragonbra;

import in.dragonbra.util.FileUtils;

import java.io.IOException;
import java.io.UncheckedIOException;

public class PngToWebp {

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
                        convertToWebp(file.getAbsolutePath(), file.getAbsolutePath().replace(".png", ".webp"));
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }, "png");
    }

    private static void convertToWebp(String in, String out) throws IOException {
        System.out.println("Converting " + in + "...");
        Runtime.getRuntime().exec(new String[]{"cwebp", "-q", "90", in, "-o", out});
    }
}