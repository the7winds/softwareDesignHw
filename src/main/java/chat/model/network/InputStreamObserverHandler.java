package chat.model.network;

import chat.model.Controller;
import chat.model.network.protocol.Handler;
import chat.model.network.protocol.P2PMessenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;


/**
 * Created by the7winds on 03.12.16.
 */

/**
 * Some kind of listener to new messages, but separated from networking
 * It delegates handling to Handler's heirs, so to add new message
 * you should just register new handler via addHandler method
 */
public class InputStreamObserverHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Hashtable<P2PMessenger.Message.BodyCase, Handler> bodyCaseHandlerHashtable = new Hashtable<>();
    private final Controller controller;

    public InputStreamObserverHandler(Controller controller) {
        this.controller = controller;
    }

    /**
     * delegates handling to the handler
     */
    public void addHandler(P2PMessenger.Message.BodyCase bodyCase, Handler handler) {
        logger.debug("added new handler for %s", bodyCase.toString());
        bodyCaseHandlerHashtable.put(bodyCase, handler);
    }

    /**
     * we should handle data in message
     */
    public void onNext(P2PMessenger.Message value) {
        bodyCaseHandlerHashtable.get(value.getBodyCase()).handle(value);
    }

    /**
     * we should notify controller, to make the app in consistent state
     */
    public void onError() {
        controller.complete();
    }

    /**
     * we should notify controller, to stop the app
     */
    public void onCompleted() {
        controller.complete();
    }
}
