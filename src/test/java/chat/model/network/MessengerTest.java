package chat.model.network;

import chat.model.network.protocol.P2PMessenger;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by the7winds on 04.12.16.
 */
public class MessengerTest {

    @Test(timeout = 2000)
    public void SimpleTest() throws IOException {

        final LinkedList<P2PMessenger.Message> received = new LinkedList<>();

        InputStreamObserverHandler receiver = new InputStreamObserverHandler(null);
        receiver.addHandler(P2PMessenger.Message.BodyCase.TEXTMESSAGE, received::add);

        InputStreamObserverHandler empty = new InputStreamObserverHandler(null);

        int port = 8000;

        Messenger server = new Messenger(port, empty);
        server.start();

        Messenger client = new Messenger("localhost", port, receiver);
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