package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static helper.helper.*;
import static imageInformation.imageInformationHelper.*;

public class viewGui extends JFrame {
    int index = 0;
    JPanel gps_pannel, imagePanel, actionPanel;
    viewGui(String birdImagePath, final ArrayList<String> birdImages) throws IOException {
        gps_pannel = new JPanel();
        gps_pannel.setLayout(new BorderLayout());

        imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());

        actionPanel = new JPanel();
        actionPanel.setLayout(new BorderLayout());

        final BufferedImage[] image = {ImageIO.read(new File(birdImagePath))};
        addImage(image[0], imagePanel, G.mainWidth, G.mainHeight);

        Path source = Paths.get(String.valueOf(birdImages.get(index)));
        final JLabel label = new JLabel(getImageLocationViewMode(source.toString()));
        gps_pannel.add(label);

        JButton nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(100, 20));
        nextButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    label.setText("");
                    deleteImage(imagePanel);
                    index++;

                    BufferedImage image = ImageIO.read(new File(birdImages.get(index)));
                    addImage(image, imagePanel, G.mainWidth, G.mainHeight);

                    Path source = Paths.get(String.valueOf(birdImages.get(index)));
                    label.setText(getImageLocationViewMode(source.toString()));
                    gps_pannel.add(label);
                } catch(Exception eo) {
                    JOptionPane.showMessageDialog(null, "All Done!");
                    System.exit(0);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        JButton rotateButton = new JButton("Rotate");
        rotateButton.setPreferredSize(new Dimension(100, 20));
        rotateButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    rotateButton(ImageIO.read(new File(birdImages.get(index))), imagePanel);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        actionPanel.add(nextButton);
        actionPanel.add(rotateButton);
        actionPanel.setLayout(new FlowLayout());

        add(gps_pannel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
    }
    public static void createViewGui(String bird_image, ArrayList<String> birdImages) throws IOException {
        viewGui mainFrame = new viewGui(bird_image, birdImages);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("View Birdex");
        mainFrame.setVisible(true);
    }
}