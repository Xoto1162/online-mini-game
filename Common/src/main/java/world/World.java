package world;

import entities.Item;
import entities.Player;
import map.Map;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    /**
     * Map des joueurs connectés au jeu
     */
    private java.util.Map<UUID, Player> _players;

    /**
     * Carte du jeu
     */
    private Map _map;

    /**
     * Carte du jeu
     */
    private java.util.Map<UUID, Item> _items;

    public World() {
        _players = new ConcurrentHashMap<>();
        _map = Map.readFromFile("map_simple.txt");
        _items = new ConcurrentHashMap<>();
    }

    public Map getMap() {
        return _map;
    }

    public Collection<Player> getPlayers() {
        return _players.values();
    }

    public Player getPlayer(UUID uuid) {
        return _players.get(uuid);
    }

    public void addPlayer(Player p) {
        _players.put(p.getUUID(), p);
    }

    public void removePlayer(Player p) {
        _players.remove(p.getUUID());
    }

    public void removePlayer(UUID uuid) {
        _players.remove(uuid);
    }

    public Collection<Item> getItems() {
        return _items.values();
    }

    public Item getItem(UUID uuid) {
        return _items.get(uuid);
    }

    public void addItem(Item i) {
        _items.put(i.getUUID(), i);
    }

    public void removeItem(Item i) {
        _items.remove(i.getUUID());
    }

    public void removeItem(UUID uuid) {
        _items.remove(uuid);
    }

    public int getRang(Player p){
        int playerScore = p.getScore();
        int rang = 1;
        for(java.util.Map.Entry<UUID,Player> player : _players.entrySet()){
            if (p != player.getValue()){
                if(player.getValue().getScore() > playerScore){
                    rang++;
                }
            }
        }
        return rang;
    }

    public List<Player> getWinner(){
        List<Player> listWinner = new ArrayList<>();
        for(java.util.Map.Entry<UUID,Player> player : _players.entrySet()){
            if(!listWinner.isEmpty()) {
                if (player.getValue().getScore() == listWinner.get(0).getScore()) {
                    listWinner.add(player.getValue());
                } else if (player.getValue().getScore() > listWinner.get(0).getScore()) {
                    listWinner.clear();
                    listWinner.add(player.getValue());
                }
            }
            else {
                listWinner.add(player.getValue());
            }
        }
        return listWinner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        World w = (World) o;

        // Comparaison de la map
        if (!_map.equals(w.getMap())) {
            System.out.println("La map ne correspond pas");
            return false;
        }

        // Comparaison des joueurs
        if (_players.size() != w._players.size()) {
            System.out.println("Le nombre de joueurs ne correspond pas");
            return false;
        }
        for (java.util.Map.Entry<UUID, Player> entry : _players.entrySet()) {
            Player p = w.getPlayer(entry.getKey());
            if (p == null || !p.equals(entry.getValue())) {
                System.out.println("Les joueurs ne correspondent pas");
                return false;
            }
        }

        // Comparaison des items
        if (_items.size() != w._items.size()) {
            System.out.println("Le nombre d'items ne correspond pas");
            return false;
        }
        for (java.util.Map.Entry<UUID, Item> entry : _items.entrySet()) {
            Item i = w.getItem(entry.getKey());
            if (i == null ||!i.equals(entry.getValue())) {
                System.out.println("Les items ne correspondent pas");
                return false;
            }
        }

        return true;
    }

}
