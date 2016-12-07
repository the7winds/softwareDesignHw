package chat.model;

import chat.model.network.*;
import chat.model.network.protocol.P2PMessenger;
import chat.view.AppFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.TimeoutException;

/**
 * Created by the7winds on 26.10.16.
 */

/**
 * It's a layer between networking and gui
 */

public class Controller {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * gui reference
     */
    private AppFrame appFrame;

    /**
     * network reference
     */
    private Messenger messenger;
    private final ReceiveMessageHandler receiveMessageHandler;

    /**
     * data (it's so small, that I don't create any other storage)
     */
    private String companion;


    /**
     * registers message handlers
     */
    public Controller() {
        receiveMessageHandler = new ReceiveMessageHandler();

        receiveMessageHandler.addHandler(P2PMessenger.Message.BodyCase.PEERINFO, new PeerInfoHandler(this));
        receiveMessageHandler.addHandler(P2PMessenger.Message.BodyCase.TEXTMESSAGE, new TextMessageHandler(this));
        receiveMessageHandler.addHandler(P2PMessenger.Message.BodyCase.STARTEDTYPING, new StartedTypingHandler(this));
    }

    public void setAddress(String host, int port) {
        messenger = new Messenger(host, port, receiveMessageHandler);
    }

    public void setAddress(int port) {
        messenger = new Messenger(port, receiveMessageHandler);
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
        try {
            messenger.stop();
        } catch (IOException | TimeoutException e) {
            logger.error("can't stop messenger", e);
        }
    }

    /**
     * starts networking
     */
    public void start() {
        try {
            messenger.start();
        } catch (IOException | TimeoutException e) {
            logger.error("can't start messenger", e);
            complete();
        }

        appFrame.setChatModeView();
    }

    public void send(String username, String text) {
        try {
            long time = Instant.now().toEpochMilli() / 1000;

            if (messenger.isConnected()) {
                messenger.sendPeerInfo(username);
                messenger.sendTextMessage(text, time);
                SwingUtilities.invokeLater(() -> appFrame.addMessage(username, time, text));
            }
        } catch (IOException e) {
            logger.error("can't send text or peer-info message message", e);
            complete();
        }
    }

    public void setCompanionName(String name) {
        companion = name;
    }

    public void notifyStartedTyping() {
        try {
            if (messenger.isConnected()) {
                messenger.sendStartedTyping();
            }
        } catch (IOException e) {
            logger.error("can't send started typing message", e);
        }
    }

    private void complete() {
        SwingUtilities.invokeLater(() -> appFrame.showBye());
    }

    public String getCompanion() {
        return companion;
    }
}
