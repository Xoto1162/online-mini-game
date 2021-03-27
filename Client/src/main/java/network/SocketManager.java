package network;

import client.Client;
import entities.Direction;

public class SocketManager {

    private Client _client;

    public SocketManager(Client client) {
        _client = client;
    }

    public void SEND_PLAYERMOVE_PACKET(Direction direction) {
        _client.getConnection().send("pm:"+direction.getId());
    }

    public void SEND_PLAYER_NAME(String pseudo) {
        _client.getConnection().send("pn:" + pseudo);
    }
}
