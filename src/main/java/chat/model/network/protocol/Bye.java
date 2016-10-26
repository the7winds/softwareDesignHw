package chat.model.network.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static chat.model.network.protocol.ProtocolType.BYE;

/**
 * Created by the7winds on 26.10.16.
 */
final public class Bye extends BasicMessage {

    public Bye() {
        super.type = BYE;
    }


    public void sendTo(DataOutputStream dataOutputStream) throws IOException {
        // pass
    }

    public void receiveFrom(DataInputStream dataInputStream) throws IOException {
        // pass
    }
}
