package chat.model.network.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static chat.model.network.protocol.ProtocolType.MESSAGE;

/**
 * Created by the7winds on 26.10.16.
 */
final public class Message extends BasicMessage {

    private long sendTime;
    private String message;

    public Message() {
        super.type = MESSAGE;
    }

    public Message(String message, long time) {
        super.type = MESSAGE;
        this.sendTime = time;
        this.message = message;
    }

    public long getSendTime() {
        return sendTime;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void sendTo(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeLong(sendTime);
        dataOutputStream.writeUTF(message);
    }

    @Override
    public void receiveFrom(DataInputStream dataInputStream) throws IOException {
        sendTime = dataInputStream.readLong();
        message = dataInputStream.readUTF();
    }
}
