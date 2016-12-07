package chat.model.network;

import chat.model.network.protocol.P2PMessenger;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * Created by the7winds on 04.12.16.
 */
public class MessengerTest {

    @Test(timeout = 2000)
    public void SimpleTest() throws IOException {

        final LinkedList<P2PMessenger.Message> received = new LinkedList<>();

        ReceiveMessageHandler receiver = new ReceiveMessageHandler();
        receiver.addHandler(P2PMessenger.Message.BodyCase.TEXTMESSAGE, received::add);

        ReceiveMessageHandler empty = new ReceiveMessageHandler();

        Messenger server = new Messenger(empty);
        server.start();

        Messenger client = new Messenger("localhost", receiver);
        client.start();

        String testString = "TEST";

        while (!server.isConnected());

        server.sendMessage(P2PMessenger.Message.newBuilder()
                .setTextMessage(
                        P2PMessenger.TextMessage.newBuilder()
                        .setText(testString)
                        .build()
                ).build()
        );

        while (received.isEmpty());

        assertEquals(testString, received.getFirst().getTextMessage().getText());
    }

}