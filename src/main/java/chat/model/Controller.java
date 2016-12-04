package chat.model;

import chat.model.network.*;
import chat.model.network.protocol.P2PMessenger;
import chat.view.AppFrame;

import javax.swing.*;
import java.io.IOException;
import java.time.Instant;
import java.util.logging.Logger;

/**
 * Created by the7winds on 26.10.16.
 */

/**
 * It's a layer between networking and gui
 */

public class Controller {

    private final Logger logger = Logger.getLogger(Controller.class.getSimpleName());

    /**
     * gui reference
     */
    private AppFrame appFrame;

    /**
     * network reference
     */
    private Messenger messenger;
    private final HandlerObserver handlerObserver;

    /**
     * data (it's so small, that I don't create any other storage)
     */
    private String companion;


    /**
     * registers message handlers
     */
    public Controller() {
        handlerObserver = new HandlerObserver(this);

        handlerObserver.addHandler(P2PMessenger.Message.BodyCase.PEERINFO, new PeerInfoHandler(this));
        handlerObserver.addHandler(P2PMessenger.Message.BodyCase.TEXTMESSAGE, new TextMessageHandler(this));
        handlerObserver.addHandler(P2PMessenger.Message.BodyCase.STARTEDTYPING, new StartedTypingHandler(this));
    }

    public void setAddress(String host, int port) {
        messenger = new Messenger(host, port, handlerObserver);
    }

    public void setAddress(int port) {
        messenger = new Messenger(port, handlerObserver);
    }

    public void setAppFrame(AppFrame appFrame) {
        this.appFrame = appFrame;
    }

    /**
     * gives access to message handlers
     */
    public AppFrame getAppFrame() {
        return appFrame;
    }

    /**
     * we should do some actions with network before exit from the app
     */
    public void prepareToClose() {
        messenger.stop();
    }

    /**
     *
     */
    public void start() {
        try {
            messenger.start();
        } catch (IOException e) {
            logger.severe(e.getMessage());
            complete();
        }

        appFrame.setChatModeView();
    }

    public void send(String username, String text) {
        long time = Instant.now().toEpochMilli() / 1000;

        if (messenger.isConnected()) {
            messenger.sendPeerInfo(username);
            messenger.sendTextMessage(text, time);
            SwingUtilities.invokeLater(() -> appFrame.addMessage(username, time, text));
        }
    }

    public void setCompanionName(String name) {
        companion = name;
    }

    public void notifyStartedTyping() {
        if (messenger.isConnected()) {
            messenger.sendStartedTyping();
        }
    }

    public void complete() {
        SwingUtilities.invokeLater(() -> appFrame.showBye());
    }

    public String getCompanion() {
        return companion;
    }
}
