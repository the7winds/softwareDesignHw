package chat.view.chatmod;

import javax.swing.*;

/**
 * Created by the7winds on 26.10.16.
 */

/**
 * shows one message unit
 */

public class SingleMessageView extends JTextArea {

    public SingleMessageView(String time, String author, String text) {
        super();
        setLineWrap(true);
        setText(String.format("[%s] %s: %s", time, author, text));
    }
}
