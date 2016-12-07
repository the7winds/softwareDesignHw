package chat.model.network;

import chat.model.network.protocol.P2PMessenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;

/**
 * Created by the7winds on 03.12.16.
 */

/**
 * Some kind of listener to new messages.
 * It delegates handling to Handler's heirs,
 * so to add new message you should just register
 * new handler via addHandler method
 */
public class ReceiveMessageHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Hashtable<P2PMessenger.Message.BodyCase, Handler> bodyCaseHandlerHashtable = new Hashtable<>();

    /**
     * delegates handling to the handler
     */
    public void addHandler(P2PMessenger.Message.BodyCase bodyCase, Handler handler) {
        logger.debug("added new handler for %s", bodyCase.toString());
        bodyCaseHandlerHashtable.put(bodyCase, handler);
    }

    public void onNext(P2PMessenger.Message value) {
        logger.debug("received new message");
        bodyCaseHandlerHashtable.get(value.getBodyCase()).handle(value);
    }
}
