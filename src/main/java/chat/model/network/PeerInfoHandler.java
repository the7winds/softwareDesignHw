package chat.model.network;

import chat.model.Controller;
import chat.model.network.protocol.P2PMessenger;

/**
 * Created by the7winds on 03.12.16.
 */
public class PeerInfoHandler implements Handler {

    final private Controller controller;

    public PeerInfoHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(P2PMessenger.Message message) {
        P2PMessenger.PeerInfo peerInfo = message.getPeerInfo();
        controller.setCompanionName(peerInfo.getName());
    }
}
