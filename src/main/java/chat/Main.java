package chat;

import chat.model.Model;
import chat.view.AppFrame;

import javax.swing.*;

/**
 * Created by the7winds on 26.10.16.
 */
public class Main {

    public static void main(String[] args) {
        Model model = new Model();

        final AppFrame appFrame = new AppFrame(model);

        SwingUtilities.invokeLater(() -> {
            appFrame.setVisible(true);
        });
    }
}
