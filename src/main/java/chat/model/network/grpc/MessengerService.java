package chat.model.network.grpc;

import chat.model.network.InputStreamObserverHandler;
import chat.model.network.ReceiverTransmitter;
import chat.model.network.protocol.MessengerGrpc;
import chat.model.network.protocol.P2PMessenger;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

/**
 * Created by the7winds on 03.12.16.
 */

/**
 * just wraps gRPC
 */
public class MessengerService extends MessengerGrpc.MessengerImplBase implements ReceiverTransmitter {

    private Server server;
    private final InputStreamObserverHandler inputStreamObserverHandler;
    private volatile StreamObserver<P2PMessenger.Message> output;

    @Override
    public StreamObserver<P2PMessenger.Message> chat(StreamObserver<P2PMessenger.Message> responseObserver) {
        output = responseObserver;
        return new InputStreamObserver(inputStreamObserverHandler, responseObserver);
    }

    public MessengerService(int port, InputStreamObserverHandler inputStreamObserverHandler) {
        server = ServerBuilder.forPort(port).addService(this).build();
        this.inputStreamObserverHandler = inputStreamObserverHandler;
    }

    @Override
    public void sendMessage(P2PMessenger.Message message) {
        output.onNext(message);
    }

    @Override
    public void start() throws IOException {
        server.start();
    }

    @Override
    public void stop() {
        output.onCompleted();
        server.shutdownNow();
    }

    @Override
    public boolean isConnected() {
        return output != null;
    }
}
