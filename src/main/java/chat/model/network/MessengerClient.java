package chat.model.network;

import chat.model.network.protocol.P2PMessenger;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by the7winds on 03.12.16.
 */

/**
 * just wraps gRPC
 */
public class MessengerClient implements ReceiverTransmitter {

    private static final String SEND_QUEUE = "client_queue";
    private static final String RECEIVE_QUEUE = "service_queue";
    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private Connection connection;
    private Channel channel;
    private ReceiveMessageHandler receiveMessageHandler;

    public MessengerClient(String host, int port, ReceiveMessageHandler receiveMessageHandler) {
        connectionFactory.setHost(host);
        this.receiveMessageHandler = receiveMessageHandler;
    }

    @Override
    public void start() throws IOException, TimeoutException {
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(SEND_QUEUE, false, false, false, null);
        channel.queueDeclare(RECEIVE_QUEUE, false, false, false, null);
        channel.basicConsume(RECEIVE_QUEUE, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                receiveMessageHandler.onNext(P2PMessenger.Message.parseFrom(body));
            }
        });
    }

    @Override
    public void sendMessage(P2PMessenger.Message message) throws IOException {
        channel.basicPublish("", SEND_QUEUE, null, message.toByteArray());
    }

    @Override
    public boolean isConnected() {
        return channel.isOpen();
    }

    @Override
    public void stop() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}
