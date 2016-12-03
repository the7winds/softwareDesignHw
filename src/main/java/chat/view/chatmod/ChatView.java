package chat.view.chatmod;

import chat.model.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;


/**
 * Created by the7winds on 26.10.16.
 */

/**
 * gets able to send/receive messages and change your
 * name during the conversation
 */

public class ChatView extends JLayeredPane {

    private JPanel messages;
    private JTextArea enterMessageArea;
    private JTextField name;
    private JButton sendButton;

    public ChatView(final Controller controller) {
        setLayout(new GridBagLayout());

        messages = new JPanel();

        messages.setLayout(new BoxLayout(messages, BoxLayout.Y_AXIS));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 3;
        gbc.weightx = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(new JScrollPane(messages, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER), gbc);

        name = new JTextField("username");
        name.setPreferredSize(new Dimension(80, 20));
        name.setMinimumSize(new Dimension(80, 20));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(name, gbc);

        enterMessageArea = new JTextArea();
        enterMessageArea.setLineWrap(true);
        gbc.gridx = 1;
        add(enterMessageArea, gbc);

        sendButton = new JButton("send");
        gbc.gridx = 2;
        add(sendButton, gbc);

        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (name.getText().length() > 0 && enterMessageArea.getText().length() > 0) {
                    String nameStr = name.getText();
                    long time = System.currentTimeMillis() / 1000;
                    String text = enterMessageArea.getText();

                    controller.send(nameStr, text, time);

                    addMessage(nameStr, Long.toString(time), text);
                    enterMessageArea.setText("");
                }
            }
        });
    }

    public void addMessage(String author, String time, String txt) {
        messages.add(new SingleMessageView(author, time,  txt));
        revalidate();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        enterMessageArea.setEnabled(enabled);
        name.setEnabled(enabled);
        sendButton.setEnabled(enabled);
    }

    public void showBye() {
        setEnabled(false);
        addMessage("", "", "the conversation is finished");
        revalidate();
    }
}
