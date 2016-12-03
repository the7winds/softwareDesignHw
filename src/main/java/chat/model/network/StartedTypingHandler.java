package chat.model.network;

import chat.model.Controller;
import chat.model.network.protocol.P2PMessenger;

/**
 * Created by the7winds on 03.12.16.
 */
public class StartedTypingHandler implements Handler {

    private final Controller controller;

    public StartedTypingHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(P2PMessenger.Message message) {
        P2PMessenger.StartedTyping startedTyping = message.getStartedTyping();
        controller.notifyStartedTyping();
    }
}
