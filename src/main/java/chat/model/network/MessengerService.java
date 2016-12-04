package chat.model.network;

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
    private final HandlerObserver handlerObserver;
    private volatile StreamObserver<P2PMessenger.Message> output;

    @Override
    public StreamObserver<P2PMessenger.Message> chat(StreamObserver<P2PMessenger.Message> responseObserver) {
        output = responseObserver;
        return handlerObserver.setResponseObserver(responseObserver);
    }

    public MessengerService(int port, HandlerObserver handlerObserver) {
        server = ServerBuilder.forPort(port).addService(this).build();
        this.handlerObserver = handlerObserver;
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
