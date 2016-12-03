package chat.model;

import chat.model.network.*;
import chat.model.network.protocol.P2PMessenger;
import chat.view.AppFrame;

import java.io.IOException;
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

    private final HandlerObserver handlerObserver = new HandlerObserver();

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

    public void prepareToClose() {
        messenger.stop();
    }

    public void startListening() throws IOException {
        messenger.start();
    }

    public void send(String nameStr, String text) {
        messenger.sendPeerInfo(nameStr);
        messenger.sendTextMessage(text);
    }

    public void addReceived(String text) {
        throw new UnsupportedOperationException();
    }

    public void changeCompanionName(String name) {
        throw new UnsupportedOperationException();
    }

    public void notifyStartedTyping() {
        throw new UnsupportedOperationException();
    }
}
