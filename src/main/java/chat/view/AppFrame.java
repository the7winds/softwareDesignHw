package chat.view;

import chat.model.Controller;
import chat.view.chatmod.ChatView;
import chat.view.selectmode.SelectModeView;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.time.*;

/**
 * Created by the7winds on 26.10.16.
 */

/**
 * Class describes app's window
 * It has two modes: selectMode(server/client) and chatMode
 * At the beginning it stays in selectMode
 */
public class AppFrame extends JFrame {

    private SelectModeView selectModeView;
    private ChatView chatView;
    private final Controller controller;

    public AppFrame(final Controller controller) {
        controller.setAppFrame(this);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        selectModeView = new SelectModeView(controller);
        chatView = new ChatView(controller);

        setSize(600, 400);
        setResizable(false);

        addWindowStateListener(e -> {
            if (e.getID() == WindowEvent.WINDOW_CLOSING) {
                controller.prepareToClose();
            }
        });

        add(selectModeView);

        this.controller = controller;
    }

    /**
     *  tunes into selectMode
     */

    public void setSelectModeView() {
        setContentPane(selectModeView = new SelectModeView(controller));
        revalidate();
    }

    /**
     * Adds message view to chat
     * @param author who send the message
     * @param time message's send time
     * @param txt message's content
     */

    public void addMessage(String author, long time, String txt) {
        String timeString = LocalDateTime.ofInstant(Instant.ofEpochMilli(time * 1000), ZoneId.systemDefault()).toString();
        chatView.addMessage(author, timeString, txt);
    }

    /**
     * tunes into chatMode
     */

    public void setChatModeView() {
        setContentPane(chatView = new ChatView(controller));
        revalidate();
    }

    public void showBye() {
        chatView.showBye();
    }
}
