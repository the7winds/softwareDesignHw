package chat.model.network;

import chat.model.network.grpc.MessengerClient;
import chat.model.network.grpc.MessengerService;
import chat.model.network.protocol.P2PMessenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final ReceiverTransmitter messenger;

    /**
     * Creates server inside
     */
    public Messenger(int port, InputStreamObserverHandler inputStreamObserverHandler) {
        logger.debug("create server messenger");
        messenger = new MessengerService(port, inputStreamObserverHandler);
    }

    /**
     * Creates client inside
     */
    public Messenger(String host, int port, InputStreamObserverHandler inputStreamObserverHandler) {
        logger.debug("create client messenger");
        messenger = new MessengerClient(host, port, inputStreamObserverHandler);
    }

    @Override
    public void start() throws IOException {
        logger.debug("start messenger");
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
        logger.debug("send text message");
        TextMessage.Builder textMessageBuilder = TextMessage.newBuilder()
                .setText(textMessage)
                .setDate(time);
        Message message = Message.newBuilder()
                .setTextMessage(textMessageBuilder)
                .build();
        sendMessage(message);
    }

    public void sendPeerInfo(String name) {
        logger.debug("send peer info");
        P2PMessenger.PeerInfo.Builder peerInfo = P2PMessenger.PeerInfo.newBuilder()
                .setName(name);
        Message message = Message.newBuilder()
                .setPeerInfo(peerInfo)
                .build();
        sendMessage(message);
    }

    public void sendStartedTyping() {
        logger.debug("notify companion about typing");
        P2PMessenger.StartedTyping startedTyping = P2PMessenger.StartedTyping.getDefaultInstance();
        Message message = Message.newBuilder()
                .setStartedTyping(startedTyping)
                .build();
        sendMessage(message);
    }

    @Override
    public void stop() {
        logger.debug("stop messenger");
        messenger.stop();
    }
}
