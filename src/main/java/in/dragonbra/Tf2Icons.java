package in.dragonbra;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class Tf2Icons {

    public static void main(String[] args) {
        BufferedReader reader = null;
        String itemsJsonStr;

        try {
            String urlString = String.format("http://api.steampowered.com/IEconItems_440/GetSchema/v0001/?key=%s&language=en", args[0]);
            URL url = new URL(urlString);
            InputStream inputStream = url.openStream();

            StringBuilder buffer = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            System.out.println("Reading JSON...");
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            itemsJsonStr = buffer.toString();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(itemsJsonStr);
            JSONObject result = (JSONObject) jsonObject.get("result");
            JSONArray items = (JSONArray) result.get("items");

            File file = new File("small/");
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();

            for (Object item : items) {
                JSONObject currentItem = (JSONObject) item;
                System.out.println("Reading " + currentItem.get("item_name"));

                long defindex = (Long) currentItem.get("defindex");

                System.out.println("Saving " + "" + defindex + ".png...");
                String imageUrl = (String) currentItem.get("image_url");
                downloadIcon(imageUrl, defindex);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Operation completed successfully.");
    }

    private static void downloadIcon(String url, long defindex) throws IOException {

        String folder = "small";
        InputStream is = null;
        OutputStream os = null;

        byte[] b = new byte[2048];
        int length;

        try {
            URL imageUrlObject = new URL(url);
            is = imageUrlObject.openStream();

            os = new FileOutputStream("./" + folder + "/" + defindex + ".png");

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (MalformedURLException e) {
            System.out.println("No image for " + defindex);
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }
}