package network;

import client.Client;
import entities.Direction;
import entities.Item;
import entities.Player;
import utils.Coordinate;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

public class PacketParser {

    private Client _client;

    public PacketParser(Client client) {
        _client = client;
    }

    public void parse(String packet) {
        if (_client.DEBUG) {
            System.out.println("Received : " + packet);
        }

        String[] elems = packet.split(":");
        switch (elems[0]) {
            case "s":
                parseHelloPacket(elems);
                break;
            case "np":
                parseNewPlayerPacket(elems);
                break;
            case "dp":
                parseDisconnectPlayerPacket(elems);
                break;
            case "pm" :
                Player player = _client.getWorld().getPlayer(UUID.fromString(elems[1]));
                player.setCoordinate(new Coordinate(Integer.parseInt(elems[2]),Integer.parseInt(elems[3])));
                player.setDirection(Direction.getById(Integer.parseInt(elems[4])));
                break;
            case "ni":
                Item item = Item.fromPacket(elems);
                _client.getWorld().addItem(item);
                break;
            case "ip":
                Item itemPick = _client.getWorld().getItem(UUID.fromString(elems[1]));
                _client.getWorld().removeItem(itemPick);
                Player playerPick = _client.getWorld().getPlayer(UUID.fromString(elems[2]));
                playerPick.setScore(playerPick.getScore()+itemPick.getType().getValue());
                break;
            case "psi":
                _client.getWorld().getPlayers().forEach(pl -> pl.setScore(Integer.parseInt(elems[1])));
                break;
            case "pn":
                _client.getWorld().getPlayer(UUID.fromString(elems[1])).setName(elems[2]);
                break;
            case "gw":
                List<Player> listWinner = _client.getWorld().getWinner();
                StringBuilder message;
                if(listWinner.size() > 1){
                    message = new StringBuilder("Les gagnants sont " + listWinner.get(0).getName());
                    for(int i = 1 ; i < listWinner.size() ; i++){
                        message.append(", ").append(listWinner.get(i).getName());
                    }
                    message.append(" !");
                }
                else {
                    message = new StringBuilder("Le gagnant est " + listWinner.get(0).getName() + " !");
                }
                message.append("\n\nVotre rang est ").append(_client.getWorld().getRang(_client.getPlayer())).append(" !");
                JOptionPane.showMessageDialog(null, message.toString(), "GAME WIN", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }

    private void parseHelloPacket(String[] values) {
        Player p = Player.fromPacket(values);
        _client.setPlayer(p);
        _client.getWorld().addPlayer(p);
    }

    public void parseNewPlayerPacket(String[] values) {
        Player player = Player.fromPacket(values);
        _client.getWorld().addPlayer(player);
    }

    private void parseDisconnectPlayerPacket(String[] values) {
        _client.getWorld().removePlayer(UUID.fromString(values[1]));
    }

}
