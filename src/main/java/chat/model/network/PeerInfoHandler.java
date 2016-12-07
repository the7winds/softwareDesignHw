package chat.model.network;

import chat.model.Controller;
import chat.model.network.protocol.P2PMessenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by the7winds on 03.12.16.
 */
public class PeerInfoHandler implements Handler {

    private final Controller controller;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public PeerInfoHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(P2PMessenger.Message message) {
        logger.debug("handle");
        P2PMessenger.PeerInfo peerInfo = message.getPeerInfo();
        controller.setCompanionName(peerInfo.getName());
    }
}
