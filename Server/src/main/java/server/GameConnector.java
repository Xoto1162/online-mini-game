package server;

import entities.Item;
import entities.Player;

import java.io.IOException;
import java.net.ServerSocket;

public class GameConnector implements Runnable {

    private GameServer _gameServer;

    private ServerSocket _SS;

    private Thread _t;

    public GameConnector(int port, GameServer gameServer) {
        _gameServer = gameServer;
        try {
            _SS  = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        _t = new Thread(this);
        _t.start();
    }

    @Override
    public void run() {
        // On accepte les connexions entrantes
        while (true) {
            try {
                Player player = new Player(
                        _gameServer.getRandomCoord(),100
                );
                _gameServer.getClients().add(new GameThread(_SS.accept(), player, _gameServer));
                _gameServer.getWorld().addPlayer(player);
                _gameServer.getSocketManager().SEND_NEW_PLAYER_TOALL(player);
                for(Item item : _gameServer.getWorld().getItems()){
                    _gameServer.getSocketManager().SEND_NEW_ITEM_TO_PLAYER(player,item);
                }
                System.out.println("Acceptation d'une nouvelle connexion.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public GameServer getGameServer() {
        return _gameServer;
    }

}
