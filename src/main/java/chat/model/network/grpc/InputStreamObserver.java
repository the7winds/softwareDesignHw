package chat.model.network.grpc;

import chat.model.network.InputStreamObserverHandler;
import chat.model.network.protocol.P2PMessenger;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by the7winds on 08.12.16.
 */
public class InputStreamObserver implements StreamObserver<P2PMessenger.Message> {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final InputStreamObserverHandler handler;
    private final StreamObserver<P2PMessenger.Message> outputStreamObserver;

    public InputStreamObserver(InputStreamObserverHandler handler, StreamObserver<P2PMessenger.Message> outputStreamObserver) {
        this.handler = handler;
        this.outputStreamObserver = outputStreamObserver;
    }

    @Override
    public void onNext(P2PMessenger.Message message) {
        handler.onNext(message);
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error("error has occurred", throwable);
        outputStreamObserver.onCompleted();
        handler.onError();
    }

    @Override
    public void onCompleted() {
        logger.debug("completed");
        outputStreamObserver.onCompleted();
        handler.onCompleted();
    }
}
