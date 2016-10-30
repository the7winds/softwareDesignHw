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

/**
 * operates listening to socket and get interface for network
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
                LOG.log(INFO, "trying to start receiving-transmitting");
                receiverTransmitter.start();
                LOG.log(INFO, "receiving-transmitting has started");
            } catch (IOException e) {
                LOG.log(WARNING, e.getLocalizedMessage());
                SwingUtilities.invokeLater(() -> appFrame.setSelectModeView());
                return;
            }

            SwingUtilities.invokeLater(() -> appFrame.setChatModeView());

            LOG.log(INFO, "start listening to socket");
            try {
                while (receiverTransmitter.isActive()) {
                    int type = receiverTransmitter.preReadMessage();
                    if (0 <= type && type < ProtocolType.values().length) {
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
                    } else {
                        LOG.log(WARNING, "SOME TRASH HAS ARRIVED");
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
            LOG.log(INFO, "finishing socket listener, stopping receiving-transmitting");
            listenHandlerThread.interrupt();
            receiverTransmitter.stop();
        } catch (IOException e) {
            LOG.log(SEVERE, e.getLocalizedMessage());
        }
    }

    private void handleMessage() throws IOException {
        LOG.log(INFO, "received text message");
        final Message message = new Message();
        receiverTransmitter.readMessage(message);
        SwingUtilities.invokeLater(() -> appFrame.addMessage(companion, message.getSendTime(), message.getMessage()));
    }

    private void handleGreeting() throws IOException {
        LOG.log(INFO, "received greeting message");
        Greeting greeting = new Greeting();
        receiverTransmitter.readMessage(greeting);
        companion = greeting.getName();
    }

    private void handleBye() throws IOException {
        LOG.log(INFO, "received bye message");
        receiverTransmitter.stop();
    }

    public void sendMessage(String text) {
        try {
            LOG.log(INFO, "sending text message");
            receiverTransmitter.sendMessage(new Message(text));
            LOG.log(INFO, "the text message has sent");
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
                LOG.log(INFO, "sending greeting message");
                receiverTransmitter.sendMessage(new Greeting(name));
                LOG.log(INFO, "the greeting message has sent");
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
            LOG.log(INFO, "sending bye message");
            receiverTransmitter.sendMessage(new Bye());
            LOG.log(INFO, "the bye has sent");
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
