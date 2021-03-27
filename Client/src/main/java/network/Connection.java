package network;

import client.Client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable {

    private Client _client;

    private Socket _socket;

    private BufferedReader _in;

    private PrintWriter _out;

    private Thread _thread;

    private PacketParser _packetParser;

    private SocketManager _socketManager;

    public Connection(Client client, String host, int port) {
        _client = client;
        _packetParser = new PacketParser(_client);
        _socketManager = new SocketManager(_client);
        try {
            _socket = new Socket(host, port);
            _in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
            _out = new PrintWriter(_socket.getOutputStream());

            // On attend de recevoir la réponse du serveur avec notre personnage
            while (_client.getPlayer() == null) {
                String packet = _in.readLine();
                _packetParser.parse(packet);
            }

            _thread = new Thread(this);
            _thread.setDaemon(true);
            _thread.start();
        } catch (IOException e) {
            // Perte de connexion avec le serveur
            JOptionPane.showMessageDialog(null, "Unable to find server.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    @Override
    public void run() {
        // On gere la lecture des paquets par le serveur
        try {
            String packet = "";

            while ((packet = _in.readLine()) != null) {
                _packetParser.parse(packet);
            }
        } catch(IOException e) {
            // Perte de connexion avec le serveur
            JOptionPane.showMessageDialog(null, "Connection lost !", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    public void send(String packet) {
        _out.print(packet + (char) 0xD);
        _out.flush();
    }

    public SocketManager getSocketManager() {
        return _socketManager;
    }

}
