package chat.view.chatmod;

import javax.swing.*;

/**
 * Created by the7winds on 26.10.16.
 */

/**
 * shows one message unit
 */

public class SingleMessageView extends JPanel {

    private final JLabel time;
    private final JLabel author;
    private final JTextArea message;

    public SingleMessageView(String time, String author, String text) {
        this.time = new JLabel(time);
        this.author = new JLabel(author);

        message = new JTextArea(text);
        message.setLineWrap(true);
        message.setEditable(false);
        message.setColumns(20);

        Box hbox = Box.createHorizontalBox();

        hbox.add(this.time);
        hbox.add(Box.createHorizontalStrut(10));
        hbox.add(this.author);
        hbox.add(Box.createHorizontalStrut(20));
        hbox.add(message);

        add(hbox);
    }
}
