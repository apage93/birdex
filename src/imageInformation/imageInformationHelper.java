package imageInformation;

import javaxt.io.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
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
            Image image = new javaxt.io.Image(birdImagePath);
            double[] gps = image.getGPSCoordinate();
            System.out.println(gps[0]);
            System.out.println(gps[1]);
            return "";
        } catch (Exception e) {
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
