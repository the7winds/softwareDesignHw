package chat.model.network.protocol;

import chat.model.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;


/**
 * Created by the7winds on 03.12.16.
 */
public class TextMessageHandler implements Handler {

    private final Controller controller;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public TextMessageHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(P2PMessenger.Message message) {
        logger.debug("handle");
        P2PMessenger.TextMessage textMessage = message.getTextMessage();
        SwingUtilities.invokeLater(() -> controller.getAppFrame()
                .addMessage(controller.getCompanion(), textMessage.getDate(), textMessage.getText()));
    }
}
