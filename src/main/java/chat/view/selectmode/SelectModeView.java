package chat.view.selectmode;

import chat.model.Model;
import chat.view.AppFrame;

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

    public SelectModeView(final Model model) {
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
                super.mouseClicked(e);
                setEnabled(false);

                model.setAddress(port.getText());
                model.startListening();
            }
        });

        clientStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setEnabled(false);

                model.setAddress(port.getText(), host.getText());
                model.startListening();

            }
        });
    }

    /**
     * blocks/ublocks ui
     * @param enabled
     */

    @Override
    public void setEnabled(boolean enabled) {
        serverStart.setEnabled(enabled);
        clientStart.setEnabled(enabled);
        port.setEnabled(enabled);
        host.setEnabled(enabled);
    }
}
