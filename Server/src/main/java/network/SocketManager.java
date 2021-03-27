package network;

import entities.Item;
import entities.Player;
import server.GameServer;
import server.GameThread;
import utils.Coordinate;

public class SocketManager {

    private GameServer _gameServer;

    public SocketManager(GameServer gameServer) {
        _gameServer = gameServer;
    }

    public void sendPacketTo(Player player, String packet) {

    }

    public void sendPacketTo(GameThread gameThread, String packet) {
        gameThread.getOutput().print(packet + (char) 0xD);
        gameThread.getOutput().flush();
    }

    /**
     * Envoi les informations du joueur
     * @param gameThread    : Joueur a qui envoyer le paquet
     */
    public void SEND_HELLOGAME_PACKETS(GameThread gameThread) {
        Player player = gameThread.getPlayer();
        Coordinate coord = player.getCoordinate();
        StringBuilder packet = new StringBuilder();
        packet.append("s:").append(player);
        sendPacketTo(gameThread, packet.toString());

        for (Player p : _gameServer.getWorld().getPlayers()) {
            if (p != player) {
                sendPacketTo(gameThread, "np:" + p);
            }
        }
    }

    /**
     * Envoi un paquet a tous les joueurs pour indiquer une nouveau joueur
     * @param player    : Nouveau joueur qui se connecte
     */
    public void SEND_NEW_PLAYER_TOALL(Player player) {
        for (GameThread c : _gameServer.getClients()) {
            if (c.getPlayer() != player) {
                sendPacketTo(c, "np:"+player);
            }
        }
    }

    /**
     * Envoi un paquet a tous les joueurs pour signaler une deconnexion
     * @param client     : Client qui se deconnecte
     */
    public void SEND_DISCONNECT_CLIENT_TOALL(GameThread client) {
        for (GameThread c : _gameServer.getClients()) {
            sendPacketTo(c, "dp:"+client.getPlayer().getUUID());
        }
    }

    /**
     * Envoi un paquet a tous les clients pour mettre a jour la position d'un joueur
     * @param p : Joueur qui à bougé et qui doit être mis à jour chez les clients
     */
    public void SEND_PLAYER_POSITION_TOALL(Player p) {
        for (GameThread c : _gameServer.getClients()) {
            sendPacketTo(c, "pm:" + p.getUUID() + ":" + p.getCoordinate().getX() + ":" + p.getCoordinate().getY() + ":" + p.getDirection().getId());
        }
    }

    /**
     * Envoi un paquet a tous les joueurs pour indiquer une nouveau joueur
     * @param player    : Nouveau joueur qui se connecte
     */
    public void SEND_NEW_ITEM_TO_PLAYER(Player player, Item item) {
        for (GameThread c : _gameServer.getClients()) {
            if (player == c.getPlayer()) {
                sendPacketTo(c, "ni:" + item.getUUID() + ":" + item.getType().getId() + ":" + item.getCoordinate().getX() + ":" + item.getCoordinate().getY());
            }
        }
    }

    /**
     * Envoi un paquet a tous les clients pour dire qu'un item a été prit
     * @param item : Item qui a été pris
     */
    public void SEND_PICK_ITEM_TOALL(Item item, Player player) {
        for (GameThread c : _gameServer.getClients()) {
            sendPacketTo(c, "ip:" + item.getUUID() + ":" + player.getUUID());
        }
    }

    /**
     * Envoi un paquet a tous les clients pour dire que tous les scores sont remis à zero
     */
    public void SEND_INIT_SCORE() {
        for (GameThread c : _gameServer.getClients()) {
            sendPacketTo(c, "psi:" + 0);
        }
    }

    public void SEND_PLAYER_NAME(Player player, String pseudo) {
        for (GameThread c : _gameServer.getClients()) {
            sendPacketTo(c, "pn:" + player.getUUID() + ":" + pseudo);
        }
    }

    public void SEND_GAME_WIN() {
        for (GameThread c : _gameServer.getClients()) {
            sendPacketTo(c, "gw");
        }
    }
}
