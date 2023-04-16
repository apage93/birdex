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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;

import static imageInformation.imageInformationHelper.*;

public class createUpdateGui extends JFrame {
    int index = 0;
    JPanel menuPanel, imagePanel, actionPanel;
    createUpdateGui(String birdImagePath, final ArrayList<String> birdImages) throws IOException {
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

        JMenuBar menuBar = new JMenuBar();
        JMenu menuBarHelp = new JMenu("Help");

        menuBar.add(menuBarHelp);
        JMenuItem menuBarHelpAbout = new JMenuItem("About");
        menuBarHelp.add(menuBarHelpAbout);

        JLabel birdDate = new JLabel("Date");
        final JTextField dateTextField = new JTextField(getImageDate(birdImagePath));
        dateTextField.setPreferredSize(new Dimension(100, 20));

        JLabel birdName = new JLabel("Name");
        final JTextField nameTextField = new JTextField();
        nameTextField.setPreferredSize(new Dimension(100, 20));

        JLabel birdLocation = new JLabel("Location");
        final JTextField locationTextField = new JTextField(getImageLocation(birdImagePath));
        locationTextField.setPreferredSize(new Dimension(100, 20));

        JButton renameButton = new JButton("Rename");
        renameButton.setPreferredSize(new Dimension(100, 20));
        renameButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Path source = Paths.get(String.valueOf(birdImages.get(index)));
                    String image_extension = String.valueOf(birdImages.get(index)).replaceAll("^.*\\.(.*)$", "$1");

                    Files.move(source, source.resolveSibling(dateTextField.getText() + "_" +
                            locationTextField.getText() + "_" +
                            nameTextField.getText() + "." +
                            image_extension));
                    nameTextField.setText("");

                    deleteImage(imagePanel);
                    index++;

                    source = Paths.get(String.valueOf(birdImages.get(index)));

                    dateTextField.setText(getImageDate(source.toString()));
                    getImageLocation(source.toString());

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

        menuPanel.add(menuBar, BorderLayout.NORTH);
        actionPanel.setLayout(new FlowLayout());
        actionPanel.add(birdDate);
        actionPanel.add(dateTextField);

        actionPanel.add(birdName);
        actionPanel.add(nameTextField);

        actionPanel.add(birdLocation);
        actionPanel.add(locationTextField);

        actionPanel.add(renameButton);
        actionPanel.add(nextButton);

        add(menuPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
    }
    public static void createCreateUpdateGui(String bird_image, ArrayList<String> birdImages) throws IOException, InterruptedException, ParseException {
        createUpdateGui mainFrame = new createUpdateGui(bird_image, birdImages);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Create or Update Birdex");
        mainFrame.setVisible(true);
    }
}
