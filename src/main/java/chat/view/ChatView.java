package chat.view;

import chat.model.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;

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
    private JLabel startedTypingIndicator;
    private Timer timer;

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
        gbc.gridwidth = 3;
        add(new JScrollPane(messages, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER), gbc);

        startedTypingIndicator = new JLabel();
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(startedTypingIndicator, gbc);
        timer = new Timer(3000, e -> startedTypingIndicator.setText(""));
        timer.start();

        name = new JTextField();
        name.setPreferredSize(new Dimension(80, 20));
        name.setMinimumSize(new Dimension(80, 20));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(name, gbc);

        enterMessageArea = new JTextArea();
        enterMessageArea.setLineWrap(true);
        enterMessageArea.addKeyListener(new KeyAdapter() {
            private long previous = 0;
            private long period = 1000;
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                if (System.currentTimeMillis() - previous > period) {
                    previous = System.currentTimeMillis();
                    controller.notifyStartedTyping();
                }
            }
        });
        gbc.gridx = 1;
        add(enterMessageArea, gbc);

        sendButton = new JButton("send");
        gbc.gridx = 2;
        add(sendButton, gbc);

        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (name.getText().length() > 0 && enterMessageArea.getText().length() > 0) {
                    controller.send(name.getText(), enterMessageArea.getText());
                    enterMessageArea.setText("");
                }
            }
        });
    }

    void addMessage(String author, String time, String txt) {
        messages.add(new SingleMessageView(time, author, txt));
        revalidate();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        enterMessageArea.setEnabled(enabled);
        name.setEnabled(enabled);
        sendButton.setEnabled(enabled);
    }

    /**
     * disables interface
     */
    void showBye() {
        setEnabled(false);
        addMessage("SYSTEM", LocalTime.now().toString(), "the conversation is finished");
        revalidate();
    }

    void notifyStartedTyping() {
        startedTypingIndicator.setText("your companion is typing...");
    }

    /**
     * shows one message on the screen
     */

    public static class SingleMessageView extends JTextArea {

        SingleMessageView(String time, String author, String text) {
            super();
            setLineWrap(true);
            setText(String.format("[%s] %s: %s", time, author, text));
        }
    }
}
