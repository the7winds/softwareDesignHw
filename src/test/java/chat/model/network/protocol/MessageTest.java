package chat.model.network.protocol;

import org.junit.Assert;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import static org.junit.Assert.*;

/**
 * Created by the7winds on 30.10.16.
 */
public class MessageTest {
    @Test
    public void simpleSendReceiveTest() throws Exception {
        PipedInputStream inputStream = new PipedInputStream();
        PipedOutputStream outputStream = new PipedOutputStream();
        inputStream.connect(outputStream);

        DataInputStream dataInputStream = new DataInputStream(inputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        Message sent = new Message("test", 10);
        sent.sendTo(dataOutputStream);

        Message received = new Message();
        received.receiveFrom(dataInputStream);

        Assert.assertEquals(sent.getMessage(), received.getMessage());
        Assert.assertEquals(sent.getSendTime(), 10);
    }
}