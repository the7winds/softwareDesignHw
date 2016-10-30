package chat.model.network.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by the7winds on 26.10.16.
 */

/**
 * basic class for all type of messages
 */

public abstract class BasicMessage {

    protected ProtocolType type;

    /**
     * writes data to dataOutputStream
     * CONTRACT: type has already written
     */
    public abstract void sendTo(DataOutputStream dataOutputStream) throws IOException;

    /**
     * reads data from dataInputStream
     * CONTRACT: type has already read
     */
    public abstract void receiveFrom(DataInputStream dataInputStream) throws IOException;

    public ProtocolType getType() {
        return type;
    }
}
