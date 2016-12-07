package chat;

import chat.model.Controller;
import chat.view.AppFrame;

import javax.swing.*;

/**
 * Created by the7winds on 26.10.16.
 */
public class Main {

    /**
     * creates model and gui
     */

    public static void main(String[] args) {
        Controller controller = new Controller();

        final AppFrame appFrame = new AppFrame(controller);

        SwingUtilities.invokeLater(() -> appFrame.setVisible(true));
    }
}
