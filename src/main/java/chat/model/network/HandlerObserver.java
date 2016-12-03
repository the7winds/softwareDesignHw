package chat.model.network;

import chat.model.network.protocol.P2PMessenger;
import io.grpc.stub.StreamObserver;

import java.util.Hashtable;
import java.util.logging.Logger;

/**
 * Created by the7winds on 03.12.16.
 */
public class HandlerObserver implements StreamObserver<P2PMessenger.Message> {

    Logger logger = Logger.getLogger(getClass().getName());
    Hashtable<P2PMessenger.Message.BodyCase, Handler> bodyCaseHandlerHashtable = new Hashtable<>();

    private StreamObserver<P2PMessenger.Message> responseObserver;

    public HandlerObserver setResponseObserver(StreamObserver<P2PMessenger.Message> responseObserver) {
        this.responseObserver = responseObserver;
        return this;
    }

    public void addHandler(P2PMessenger.Message.BodyCase bodyCase, Handler handler) {
        bodyCaseHandlerHashtable.put(bodyCase, handler);
    }

    @Override
    public void onNext(P2PMessenger.Message value){
        bodyCaseHandlerHashtable.get(value.getBodyCase()).handle(value);
    }

    @Override
    public void onError(Throwable t){
        logger.warning(t.getMessage());
    }

    @Override
    public void onCompleted(){
        responseObserver.onCompleted();
    }
}
