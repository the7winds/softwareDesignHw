package chat.model.network;

import chat.model.network.protocol.BasicMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by the7winds on 26.10.16.
 */

/**
 * get interface to send write message without thinking about socket
 */

public class ReceiverTransmitter {

    private String host;
    private short port;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ReceiverTransmitter(short port) {
        this.port = port;
    }

    public ReceiverTransmitter(String host, short port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        if (host == null) {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
        } else {
            socket = new Socket(host, port);
        }

        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void sendMessage(BasicMessage message) throws IOException {
        dataOutputStream.writeInt(message.getType().ordinal());
        message.sendTo(dataOutputStream);
    }

    public int preReadMessage() throws IOException {
        return dataInputStream.readInt();
    }

    public void readMessage(BasicMessage basicMessage) throws IOException {
        basicMessage.receiveFrom(dataInputStream);
    }

    public void stop() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
        socket.close();
    }

    public boolean isActive() {
        return !socket.isClosed();
    }
}
