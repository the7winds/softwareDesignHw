package chat.model.network;

import chat.model.network.protocol.P2PMessenger;

/**
 * Created by the7winds on 03.12.16.
 */
public interface Handler {
    void handle(P2PMessenger.Message message);
}
