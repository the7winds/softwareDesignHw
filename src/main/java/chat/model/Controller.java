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
 * operates listening to socket and get interface for network
 */

public class Controller {

    private final Logger logger = Logger.getLogger(Controller.class.getSimpleName());
    private AppFrame appFrame;

    private String companion;

    private final HandlerObserver handlerObserver = new HandlerObserver(this);

    public Controller() {
        handlerObserver.addHandler(P2PMessenger.Message.BodyCase.PEERINFO, new PeerInfoHandler(this));
        handlerObserver.addHandler(P2PMessenger.Message.BodyCase.TEXTMESSAGE, new TextMessageHandler(this));
        handlerObserver.addHandler(P2PMessenger.Message.BodyCase.STARTEDTYPING, new StartedTypingHandler(this));
    }

    private Messenger messenger;

    public void setAddress(String host, int port) {
        messenger = new Messenger(host, port, handlerObserver);
    }

    public void setAddress(int port) {
        messenger = new Messenger(port, handlerObserver);
    }

    public void setAppFrame(AppFrame appFrame) {
        this.appFrame = appFrame;
    }

    public AppFrame getAppFrame() {
        return appFrame;
    }

    public void prepareToClose() {
        messenger.stop();
    }

    public void start() {
        try {
            messenger.start();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }

        appFrame.setChatModeView();
    }

    public void send(String name, String text) {
        long time = Instant.now().toEpochMilli() / 1000;

        if (messenger.isConnected()) {
            messenger.sendPeerInfo(name);
            messenger.sendTextMessage(text, time);
            SwingUtilities.invokeLater(() -> appFrame.addMessage(name, time, text));
        }
    }

    public void setCompanionName(String name) {
        companion = name;
    }

    public void notifyStartedTyping() {
        messenger.sendStartedTyping();
    }

    public void complete() {
        SwingUtilities.invokeLater(() -> appFrame.showBye());
    }

    public String getCompanion() {
        return companion;
    }
}
