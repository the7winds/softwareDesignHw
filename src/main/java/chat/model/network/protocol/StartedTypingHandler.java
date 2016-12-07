package chat.model.network.protocol;

import chat.model.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * Created by the7winds on 03.12.16.
 */
public class StartedTypingHandler implements Handler {

    private final Controller controller;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public StartedTypingHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(P2PMessenger.Message message) {
        logger.debug("handle");
        SwingUtilities.invokeLater(() -> controller.getAppFrame().notifyStartedTyping());
    }
}
