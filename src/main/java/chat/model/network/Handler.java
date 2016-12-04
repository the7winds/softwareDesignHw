package chat.model.network;

import chat.model.network.protocol.P2PMessenger;

/**
 * Created by the7winds on 03.12.16.
 */

/**
 * Interface implements handling received messages.
 * Iit helps, to avoid switch at receiving method and use type-checking
 */

public interface Handler {
    void handle(P2PMessenger.Message message);
}
