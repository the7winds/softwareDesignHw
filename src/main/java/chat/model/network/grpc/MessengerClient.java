package chat.model.network.grpc;

import chat.model.network.InputStreamObserverHandler;
import chat.model.network.ReceiverTransmitter;
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
    private InputStreamObserverHandler inputStreamObserverHandler;
    private ManagedChannel managedChannel;
    private volatile StreamObserver<P2PMessenger.Message> output;

    public MessengerClient(String host, int port, InputStreamObserverHandler inputStreamObserverHandler) {
        managedChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
        messengerStub = MessengerGrpc.newStub(managedChannel);
        this.inputStreamObserverHandler = inputStreamObserverHandler;
    }

    @Override
    public void start() {
        output = messengerStub.chat(new InputStreamObserver(inputStreamObserverHandler, output));
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
