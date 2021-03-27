package server;

import entities.Player;
import network.PacketParser;
import server.GameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameThread implements Runnable {

    private Player _player;

    private Socket _socket;

    private GameServer _gameServer;

    private PacketParser _packetParser;

    private BufferedReader _in;

    private PrintWriter _out;

    private Thread _thread;

    public GameThread(Socket socket, Player player, GameServer gameServer) {
        _player = player;
        _gameServer = gameServer;
        _packetParser = new PacketParser(_gameServer, this);

        try {
            _socket = socket;
            _in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
            _out = new PrintWriter(_socket.getOutputStream());

            _thread = new Thread(this);
            _thread.setDaemon(true);
            _thread.start();
        } catch (IOException e) {
            if (!_socket.isClosed()) {
                try {
                    _socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            String packet = "";
            _gameServer.getSocketManager().SEND_HELLOGAME_PACKETS(this);

            while ((packet = _in.readLine()) != null) {
                getPacketParser().parse(packet);
            }
        } catch(IOException e) {
            // Gérer la deco d'un client
            _gameServer.getWorld().removePlayer(_player);
            _gameServer.getSocketManager().SEND_DISCONNECT_CLIENT_TOALL(this);
        }
    }

    public Player getPlayer() {
        return _player;
    }

    public PacketParser getPacketParser() {
        return _packetParser;
    }

    public BufferedReader getInput() {
        return _in;
    }

    public PrintWriter getOutput() {
        return _out;
    }

}
