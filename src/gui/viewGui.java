package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static imageInformation.imageInformationHelper.addImage;
import static imageInformation.imageInformationHelper.deleteImage;

public class viewGui extends JFrame {
    int index = 0;
    JPanel menuPanel, imagePanel, actionPanel;
    viewGui(String birdImagePath, final ArrayList<String> birdImages) throws IOException {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());

        imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());

        actionPanel = new JPanel();
        actionPanel.setLayout(new BorderLayout());

        BufferedImage image = ImageIO.read(new File(birdImagePath));
        java.awt.Image fitImage = image.getScaledInstance(1000,1000, java.awt.Image.SCALE_SMOOTH);
        JLabel bird_image = new JLabel(new ImageIcon(fitImage));
        imagePanel.add(bird_image);

        JButton nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(100, 20));
        nextButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    deleteImage(imagePanel);
                    index++;

                    BufferedImage image = ImageIO.read(new File(birdImages.get(index)));
                    addImage(image, imagePanel);
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

        actionPanel.add(nextButton);
        actionPanel.setLayout(new FlowLayout());

        add(menuPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
    }
    public static void createViewGui(String bird_image, ArrayList<String> birdImages) throws IOException {
        viewGui mainFrame = new viewGui(bird_image, birdImages);
        mainFrame.setSize(1400, 1000);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("View Birdex");
        mainFrame.setVisible(true);
    }
}