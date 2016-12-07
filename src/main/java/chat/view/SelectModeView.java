package chat.view;

import chat.model.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by the7winds on 26.10.16.
 */

/**
 * asks host (if need), port and mode either server or client
 */
public class SelectModeView extends JLayeredPane {

    private final JButton serverStart;
    private final JButton clientStart;
    private final JTextField host;
    private final JTextField port;

    SelectModeView(final Controller controller) {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        serverStart = new JButton("server");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(serverStart, gbc);

        clientStart = new JButton("client");
        gbc.gridy = 1;
        add(clientStart, gbc);

        host = new JTextField("localhost");
        gbc.gridy = 2;
        add(host, gbc);

        port = new JTextField("8000");
        gbc.gridy = 3;
        add(port, gbc);

        serverStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setEnabled(false);
                controller.setAddress(Integer.valueOf(port.getText()));
                controller.start();
            }
        });

        clientStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setEnabled(false);
                controller.setAddress(host.getText(), Integer.valueOf(port.getText()));
                controller.start();

            }
        });
    }

    /**
     * block/unblock ui
     */

    @Override
    public void setEnabled(boolean enabled) {
        serverStart.setEnabled(enabled);
        clientStart.setEnabled(enabled);
        port.setEnabled(enabled);
        host.setEnabled(enabled);
    }
}
