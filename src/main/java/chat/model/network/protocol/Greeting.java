package chat.model.network.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static chat.model.network.protocol.ProtocolType.GREETING;

/**
 * Created by the7winds on 26.10.16.
 */
final public class Greeting extends BasicMessage {

    private String name;

    public Greeting() {
        super.type = GREETING;
    }

    public Greeting(String name) {
        super.type = GREETING;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void sendTo(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(name);
    }

    public void receiveFrom(DataInputStream dataInputStream) throws IOException {
        name = dataInputStream.readUTF();
    }
}
