package chat.view.chatmod;

import javax.swing.*;

/**
 * Created by the7winds on 26.10.16.
 */

/**
 * shows one message unit
 */

public class SingleMessageView extends JLabel {

    public SingleMessageView(String time, String author, String text) {
        super(String.format("[%s|%d] %s", author, time, text));
    }
}
