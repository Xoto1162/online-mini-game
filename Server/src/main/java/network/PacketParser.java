package network;

import entities.Direction;
import entities.Item;
import entities.Player;
import map.Map;
import server.GameServer;
import server.GameThread;
import utils.Coordinate;

import java.util.Random;

public class PacketParser {

    private GameServer _gameServer;

    private GameThread _client;

    public PacketParser(GameServer gameServer, GameThread client) {
        _gameServer = gameServer;
        _client = client;
    }

    public void parse(String packet) {
        if (_gameServer.hasDebugMode()) {
            System.out.println("Received : " + packet);
        }

        String[] elems = packet.split(":");
        switch (elems[0].charAt(0)) {
            case 'p':
                parsePlayerPackets(elems);
                break;
        }
    }

    private void parsePlayerPackets(String[] values) {
        switch (values[0]) {
            case "pm": // Demande de mouvement
                playerMove(values[1]);
                break;
            case "pn": // Demande de pseudo
                String pseudo = values[1];
                Player player = _client.getPlayer();
                for(Player p : _gameServer.getWorld().getPlayers()){
                    if(player != p && p.getName().equals(pseudo)){
                        Random r = new Random();
                        pseudo = pseudo + r.nextInt(100);
                    }
                }
                player.setName(pseudo);
                _gameServer.getSocketManager().SEND_PLAYER_NAME(player, pseudo);
                break;
        }
    }

    private synchronized void playerMove(String value) {
        Player player = _client.getPlayer();
        Coordinate coord_player = player.getCoordinate();
        Map map = _gameServer.getWorld().getMap();
        Direction direction = Direction.getById(Integer.parseInt(value));
        Coordinate coord = new Coordinate(coord_player.getX() + direction.getX(),coord_player.getY() + direction.getY());
        if (map.canMoveAt(_gameServer.getWorld(),coord_player,coord)){
            player.setCoordinate(coord);
            player.setDirection(direction);
            _gameServer.getSocketManager().SEND_PLAYER_POSITION_TOALL(player);
            Item item = map.getItemAtCoordinate(_gameServer.getWorld(),coord);
            if(item != null){
                player.setScore(player.getScore()+item.getType().getValue());
                _gameServer.getSocketManager().SEND_PICK_ITEM_TOALL(item, player);
                _gameServer.getWorld().removeItem(item);
            }
        }
    }

}
