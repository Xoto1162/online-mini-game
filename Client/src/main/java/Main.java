import client.Client;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private TextField _host;
    private TextField _port;
    private TextField _pseudo;

    public Main() {
        setTitle("Connexion à un serveur");
        setPreferredSize(new Dimension(300, 300));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        JPanel inputPan = new JPanel();
        inputPan.setLayout(new BoxLayout(inputPan, BoxLayout.PAGE_AXIS));

        _host = new TextField();
        _port = new TextField();
        _pseudo = new TextField();
        Label hostLabel = new Label("Host");
        Label portLabel = new Label("Port");
        Label pseudoLabel = new Label("Pseudo");
        Button connection = new Button("Connexion");

        _host.setText("localhost");
        _port.setText("1337");

        connection.addActionListener(e -> {
            int port;
            String host = _host.getText();
            String pseudo = _pseudo.getText();
            try {
                port = Integer.parseInt(_port.getText());
            } catch (NumberFormatException err) {
                return;
            }

            if (!host.equals("") && port > 0 && !pseudo.equals("")) {
                launchGame(host, port, pseudo);
            }
        });

        inputPan.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPan.add(pseudoLabel);
        inputPan.add(_pseudo);
        inputPan.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPan.add(hostLabel);
        inputPan.add(_host);
        _host.setPreferredSize(new Dimension(250, 25));
        inputPan.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPan.add(portLabel);
        inputPan.add(_port);
        _port.setPreferredSize(new Dimension(250, 25));
        inputPan.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPan.add(connection);

        panel.add(inputPan);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new Main();
        //JOptionPane.showMessageDialog(null, "Connection serveur");
    }

    public void launchGame(String host, int port, String pseudo) {
        dispose();
        (new Thread(new Client(host, port, pseudo))).start();
    }
}
