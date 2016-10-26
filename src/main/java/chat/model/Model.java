package chat.model;

import chat.model.network.ReceiverTransmitter;
import chat.model.network.protocol.Bye;
import chat.model.network.protocol.Greeting;
import chat.model.network.protocol.Message;
import chat.model.network.protocol.ProtocolType;
import chat.view.AppFrame;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.Logger;

import static java.util.logging.Level.*;

/**
 * Created by the7winds on 26.10.16.
 */
public class Model {

    private final Logger LOG = Logger.getLogger(Model.class.getSimpleName());
    private AppFrame appFrame;

    private String name;
    private String companion;

    private ReceiverTransmitter receiverTransmitter;

    private Thread listenHandlerThread;
    private Runnable listenHandler = new Runnable() {
        public void run() {
            try {
                receiverTransmitter.start();
            } catch (IOException e) {
                LOG.log(WARNING, e.getLocalizedMessage());
                SwingUtilities.invokeLater(() -> appFrame.setSelectModeView());
                return;
            }

            SwingUtilities.invokeLater(() -> appFrame.setChatModeView());

            try {
                while (receiverTransmitter.isActive()) {
                    int type = receiverTransmitter.preReadMessage();
                    LOG.log(INFO, "received new message");
                    switch (ProtocolType.values()[type]) {
                        case GREETING:
                            handleGreeting();
                            break;
                        case MESSAGE:
                            handleMessage();
                            break;
                        case BYE:
                            handleBye();
                            break;
                    }
                }
            } catch (IOException e) {
                LOG.log(WARNING, e.getLocalizedMessage());
            }

            SwingUtilities.invokeLater(() -> appFrame.showBye());
        }
    };

    public void setAddress(String port, String host) {
        receiverTransmitter = new ReceiverTransmitter(host, Short.valueOf(port));
    }

    public void setAddress(String port) {
        receiverTransmitter = new ReceiverTransmitter(Short.valueOf(port));
    }

    public void setAppFrame(AppFrame appFrame) {
        this.appFrame = appFrame;
    }

    public void finishAllTasks() {
        try {
            listenHandlerThread.interrupt();
            receiverTransmitter.stop();
        } catch (IOException e) {
            LOG.log(SEVERE, e.getLocalizedMessage());
        }
    }

    private void handleMessage() throws IOException {
        final Message message = new Message();
        receiverTransmitter.readMessage(message);
        SwingUtilities.invokeLater(() -> appFrame.addMessage(companion, message.getSendTime(), message.getMessage()));
    }

    private void handleGreeting() throws IOException {
        Greeting greeting = new Greeting();
        receiverTransmitter.readMessage(greeting);
        companion = greeting.getName();
    }

    private void handleBye() throws IOException {
        receiverTransmitter.stop();
    }

    public void sendMessage(String text) {
        try {
            receiverTransmitter.sendMessage(new Message(text));
        } catch (IOException e) {
            LOG.log(WARNING, e.getLocalizedMessage());
            try {
                receiverTransmitter.stop();
            } catch (IOException e1) {
                LOG.log(SEVERE, e1.getLocalizedMessage());
            }
        }
    }

    public void sendGreeting(String name) {
        if (!name.equals(this.name)) {
            this.name = name;
            try {
                receiverTransmitter.sendMessage(new Greeting(name));
            } catch (IOException e) {
                LOG.log(WARNING, e.getLocalizedMessage());
                try {
                    receiverTransmitter.stop();
                } catch (IOException e1) {
                    LOG.log(SEVERE, e1.getLocalizedMessage());
                }
            }
        }
    }

    public void startListening() {
        listenHandlerThread = new Thread(listenHandler);
        listenHandlerThread.start();
    }

    public void sendBye() {
        try {
            receiverTransmitter.sendMessage(new Bye());
        } catch (IOException e) {
            LOG.log(WARNING, e.getLocalizedMessage());
            try {
                receiverTransmitter.stop();
            } catch (IOException e1) {
                LOG.log(SEVERE, e1.getLocalizedMessage());
            }
        }
    }
}
