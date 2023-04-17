package helper;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static imageInformation.imageInformationHelper.addImage;
import static imageInformation.imageInformationHelper.deleteImage;

public class helper {
    public static void rotateButton(BufferedImage imageToRotate, JPanel imagePanel){
        try {
            deleteImage(imagePanel);

            int widthOfImage = imageToRotate.getWidth();
            int heightOfImage = imageToRotate.getHeight();
            int typeOfImage = imageToRotate.getType();

            BufferedImage newImageFromBuffer = new BufferedImage(widthOfImage, heightOfImage, typeOfImage);

            Graphics2D graphics2D = newImageFromBuffer.createGraphics();
            graphics2D.rotate(Math.toRadians(90), widthOfImage / 2, heightOfImage / 2);
            graphics2D.drawImage(imageToRotate, null, 0, 0);

            addImage(newImageFromBuffer, imagePanel);
        } catch(Exception eo) {
            System.exit(0);
        }
    }
}
