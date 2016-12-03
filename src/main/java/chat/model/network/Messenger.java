package chat.model.network;

import chat.model.network.protocol.P2PMessenger;

import java.io.IOException;

import static chat.model.network.protocol.P2PMessenger.Message;
import static chat.model.network.protocol.P2PMessenger.TextMessage;

/**
 * Created by the7winds on 03.12.16.
 */
public class Messenger implements ReceiverTransmitter {

    private ReceiverTransmitter messenger;

    public Messenger(int port, HandlerObserver handlerObserver) {
        messenger = new MessengerService(port, handlerObserver);
    }

    public Messenger(String host, int port, HandlerObserver handlerObserver) {
        messenger = new MessengerClient(host, port, handlerObserver);
    }

    @Override
    public void start() throws IOException {
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
        TextMessage.Builder textMessageBuilder = TextMessage.newBuilder()
                .setText(textMessage)
                .setDate(time);
        Message message = Message.newBuilder()
                .setTextMessage(textMessageBuilder)
                .build();
        sendMessage(message);
    }

    public void sendPeerInfo(String name) {
        P2PMessenger.PeerInfo.Builder peerInfo = P2PMessenger.PeerInfo.newBuilder()
                .setName(name);
        Message message = Message.newBuilder()
                .setPeerInfo(peerInfo)
                .build();
        sendMessage(message);
    }

    public void sendStartedTyping() {
        P2PMessenger.StartedTyping startedTyping = P2PMessenger.StartedTyping.getDefaultInstance();
        Message message = Message.newBuilder()
                .setStartedTyping(startedTyping)
                .build();
        sendMessage(message);
    }

    @Override
    public void stop() {
        messenger.stop();
    }
}
