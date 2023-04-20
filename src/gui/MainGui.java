package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static imageInformation.imageInformationHelper.getBirdsList;

public class MainGui extends JFrame {
    JPanel menuPanel, mainPanel, choicePanel;

    final ImageIcon viewBirdexImage = new ImageIcon(ClassLoader.getSystemResource("viewBirdex.png"));
    final ImageIcon createUpdateBirdexImage = new ImageIcon(ClassLoader.getSystemResource("createUpdateBirdex.png"));

    MainGui() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        choicePanel = new JPanel();
        choicePanel.setLayout(new BorderLayout());

        JLabel titleBirdex = new JLabel();
        titleBirdex.setIcon(new ImageIcon(ClassLoader.getSystemResource("birdexTitle.png")));

        JLabel greatHeronPicture = new JLabel();
        greatHeronPicture.setIcon(new ImageIcon(ClassLoader.getSystemResource("greatHeron.jpg")));

        final JLabel viewBirdex = new JLabel();
        viewBirdex.setIcon(viewBirdexImage);
        viewBirdex.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ArrayList<String> birdImages;
                File[] myBirdex = fileChooser.generateFileChooser();
                dispose();
                birdImages = getBirdsList(myBirdex);
                try {
                    viewGui.createViewGui(birdImages);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) {
                final ImageIcon imageIconTwo = new ImageIcon(ClassLoader.getSystemResource("viewBirdexSelection.png"));
                viewBirdex.setIcon(imageIconTwo);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewBirdex.setIcon(viewBirdexImage);
            }
        });

        final JLabel createUpdateBirdex = new JLabel();
        createUpdateBirdex.setIcon(createUpdateBirdexImage);
        createUpdateBirdex.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ArrayList<String> birdImages;
                File[] myBirdex = fileChooser.generateFileChooser();
                dispose();
                birdImages = getBirdsList(myBirdex);
                try {
                    createUpdateGui.createCreateUpdateGui(birdImages.get(0), birdImages);
                } catch (IOException | InterruptedException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                final ImageIcon imageIconTwo = new ImageIcon(ClassLoader.getSystemResource("createUpdateBirdexSelection.png"));
                createUpdateBirdex.setIcon(imageIconTwo);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                createUpdateBirdex.setIcon(createUpdateBirdexImage);
            }
        });

        menuPanel.add(titleBirdex, BorderLayout.CENTER);

        mainPanel.add(greatHeronPicture, BorderLayout.CENTER);

        choicePanel.add(viewBirdex, BorderLayout.NORTH);
        choicePanel.add(createUpdateBirdex, BorderLayout.CENTER);

        add(menuPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.LINE_START);
        add(choicePanel, BorderLayout.CENTER);
    }
    public static void createMainGui() {
        MainGui mainFrame = new MainGui();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Birdex");
        mainFrame.setVisible(true);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        G.mainHeight = mainFrame.getHeight();
        G.mainWidth = mainFrame.getWidth();
        mainFrame.setSize(1400, 1000);
        mainFrame.setLocationRelativeTo(null);
    }
}
