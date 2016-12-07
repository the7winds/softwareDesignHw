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

        serverStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setEnabled(false);
                controller.setAddress();
                controller.start();
            }
        });

        clientStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setEnabled(false);
                controller.setAddress(host.getText());
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
        host.setEnabled(enabled);
    }
}
