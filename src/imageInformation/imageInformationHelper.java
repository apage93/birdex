package imageInformation;

import javaxt.io.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class imageInformationHelper {
    public static boolean isImage(String image_name) {
        Pattern pattern = Pattern.compile(".(?:jpeg|jpg|tiff|tif|png)$");
        Matcher matcher = pattern.matcher(image_name);
        return matcher.find();
    }

    public static ArrayList<String> getBirdsList(File [] myBirdex) {
        ArrayList<String> birdImagePath = new ArrayList<>();
        for (File myBirdexItem : myBirdex) {
            if (myBirdexItem.isFile()) {
                String birdexImageName = myBirdexItem.getAbsolutePath();
                if (isImage(birdexImageName)) {
                    birdImagePath.add(birdexImageName);
                }
            }
        }
        return birdImagePath;
    }

    public static String getImageDate(String birdImagePath) {
        javaxt.io.File file2 = new javaxt.io.File(birdImagePath);
        Date date = new Date(file2.getLastModifiedTime().toString());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
        return formatter.format(date);
    }
    public static String getImageLocation(String birdImagePath) {
        try {
            String location_from_gps_points = getImageLocationViewMode(birdImagePath);
            String[] location = location_from_gps_points.split(",");
            String region = location[location.length - 4];
            String strip_region_dash = region.replaceAll("-", "_");
            String strip_region_spaces = strip_region_dash.replaceAll(" ", "");
            return strip_region_spaces.replaceAll("'", "_");

        } catch (Exception e) {
            return "";
        }
    }

    public static String getImageLocationViewMode(String birdImagePath) {
        try {
            Image image = new javaxt.io.Image(birdImagePath);
            double[] gps = image.getGPSCoordinate();
            String lon = String.valueOf(gps[0]);
            String lat = String.valueOf(gps[1]);

            ProcessBuilder builder = new ProcessBuilder("python", System.getProperty("user.dir") + "\\src\\imageInformation\\gps_conversion.py", lon, lat);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.ISO_8859_1));

            String location_from_gps_points = reader.readLine();
            location_from_gps_points = Normalizer.normalize(location_from_gps_points, Normalizer.Form.NFD);
            location_from_gps_points = location_from_gps_points.replaceAll("\\p{InCombiningDiacriticalMarks}", "");

            return location_from_gps_points;

        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }

    public static void deleteImage(JPanel imagePanel){
        Component[] parent = imagePanel.getComponents();
        for (Component c: parent) {
            imagePanel.remove(c);
        }
        imagePanel.revalidate();
    }

    public static void addImage(BufferedImage image, JPanel imagePanel){
        java.awt.Image fitImage = image.getScaledInstance(1000, 1000, java.awt.Image.SCALE_SMOOTH);
        JLabel bird_image = new JLabel(new ImageIcon(fitImage));
        imagePanel.add(bird_image);
        imagePanel.revalidate();
    }
}
