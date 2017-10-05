package in.dragonbra;

import in.dragonbra.util.FileUtils;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

public class ResizePng {

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
                        resizePng(file.getAbsolutePath());
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }, "png");
    }

    private static void resizePng(String file) throws IOException {
        System.out.println("Resizing " + file + "...");
        BufferedImage img = ImageIO.read(new File(file));
        BufferedImage resizedImg = Scalr.resize(img, 128);
        img.flush();
        ImageIO.write(resizedImg, "png", new File(file));
        resizedImg.flush();
    }
}
