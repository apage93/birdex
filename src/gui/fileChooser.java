package gui;

import javax.swing.*;
import java.io.File;

public class fileChooser {
    public static File[] generateFileChooser() {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showOpenDialog(null);
        File folder = new File(f.getSelectedFile().toURI());
        return folder.listFiles();
    }
}
