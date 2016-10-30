package chat.view;

import chat.model.Model;
import chat.view.chatmod.ChatView;
import chat.view.selectmode.SelectModeView;

import javax.swing.*;
import java.awt.event.WindowEvent;

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
    private final Model model;

    public AppFrame(final Model model) {
        model.setAppFrame(this);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        selectModeView = new SelectModeView(model);
        chatView = new ChatView(model);

        setSize(600, 400);
        setResizable(false);

        addWindowStateListener(e -> {
            if (e.getID() == WindowEvent.WINDOW_CLOSING) {
                model.sendBye();
                model.finishAllTasks();
            }
        });

        add(selectModeView);

        this.model = model;
    }

    /**
     *  tunes into selectMode
     */

    public void setSelectModeView() {
        setContentPane(selectModeView = new SelectModeView(model));
        revalidate();
    }

    /**
     * Adds message view to chat
     * @param author who send the message
     * @param time message's send time
     * @param txt message's content
     */

    public void addMessage(String author, long time, String txt) {
        chatView.addMessage(author, Long.toString(time / 1000), txt);
    }

    /**
     * shows message when conversation is finished and another user
     * us unavailable
     */

    public void showBye() {
        chatView.showBye();
    }

    /**
     * tunes into chatMode
     */

    public void setChatModeView() {
        setContentPane(chatView = new ChatView(model));
        revalidate();
    }
}
