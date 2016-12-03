package chat.model.network;

import chat.model.network.protocol.P2PMessenger;

import java.io.IOException;

/**
 * Created by the7winds on 03.12.16.
 */
public interface ReceiverTransmitter {

    void start() throws IOException;

    void stop();

    void sendMessage(P2PMessenger.Message message);
}
