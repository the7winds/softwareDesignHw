package chat.model.network;

import chat.model.network.protocol.MessengerGrpc;
import chat.model.network.protocol.MessengerGrpc.MessengerStub;
import chat.model.network.protocol.P2PMessenger;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

/**
 * Created by the7winds on 03.12.16.
 */

/**
 * just wraps gRPC
 */
public class MessengerClient implements ReceiverTransmitter {

    private MessengerStub messengerStub;
    private HandlerObserver handlerObserver;
    private ManagedChannel managedChannel;
    private volatile StreamObserver<P2PMessenger.Message> output;

    public MessengerClient(String host, int port, HandlerObserver handlerObserver) {
        managedChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
        messengerStub = MessengerGrpc.newStub(managedChannel);
        this.handlerObserver = handlerObserver;
    }

    @Override
    public void start() {
        output = messengerStub.chat(handlerObserver.setResponseObserver(output));
    }

    @Override
    public void sendMessage(P2PMessenger.Message message) {
        output.onNext(message);
    }

    @Override
    public boolean isConnected() {
        return output != null;
    }

    @Override
    public void stop() {
        output.onCompleted();
        managedChannel.shutdownNow();
    }
}
