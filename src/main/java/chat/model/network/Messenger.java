package chat.model.network;

import chat.model.network.protocol.P2PMessenger;

import java.io.IOException;
import java.util.logging.Logger;

import static chat.model.network.protocol.P2PMessenger.Message;
import static chat.model.network.protocol.P2PMessenger.TextMessage;

/**
 * Created by the7winds on 03.12.16.
 */

/**
 * Receiver-Transmitter Implementation which hides difference between
 * server and client, it just delegate all calls to them
 */

public class Messenger implements ReceiverTransmitter {

    private Logger logger = Logger.getLogger(getClass().getName());
    private ReceiverTransmitter messenger;

    /**
     * Creates server inside
     */
    public Messenger(int port, HandlerObserver handlerObserver) {
        logger.info("create server messenger");
        messenger = new MessengerService(port, handlerObserver);
    }

    /**
     * Creates client inside
     */
    public Messenger(String host, int port, HandlerObserver handlerObserver) {
        logger.info("create client messenger");
        messenger = new MessengerClient(host, port, handlerObserver);
    }

    @Override
    public void start() throws IOException {
        logger.info("start messenger");
        messenger.start();
    }

    @Override
    public void sendMessage(Message message) {
        messenger.sendMessage(message);
    }

    @Override
    public boolean isConnected() {
        return messenger.isConnected();
    }

    public void sendTextMessage(String textMessage, long time) {
        logger.info("send text message");
        TextMessage.Builder textMessageBuilder = TextMessage.newBuilder()
                .setText(textMessage)
                .setDate(time);
        Message message = Message.newBuilder()
                .setTextMessage(textMessageBuilder)
                .build();
        sendMessage(message);
    }

    public void sendPeerInfo(String name) {
        logger.info("send peer info");
        P2PMessenger.PeerInfo.Builder peerInfo = P2PMessenger.PeerInfo.newBuilder()
                .setName(name);
        Message message = Message.newBuilder()
                .setPeerInfo(peerInfo)
                .build();
        sendMessage(message);
    }

    public void sendStartedTyping() {
        logger.info("notify companion about typing");
        P2PMessenger.StartedTyping startedTyping = P2PMessenger.StartedTyping.getDefaultInstance();
        Message message = Message.newBuilder()
                .setStartedTyping(startedTyping)
                .build();
        sendMessage(message);
    }

    @Override
    public void stop() {
        logger.info("stop messenger");
        messenger.stop();
    }
}
