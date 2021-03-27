package server;

import entities.Item;
import entities.ItemType;
import entities.Player;
import map.Cell;
import map.CellType;
import map.Map;
import network.SocketManager;
import utils.Coordinate;
import world.World;

import java.util.ArrayList;
import java.util.List;

public class GameServer implements Runnable {

    private World _world;

    private List<GameThread> _clients;

    private SocketManager _socketManager;

    private Thread _t;

    private boolean _debugMode;

    public GameServer(World world) {
        _world = world;
        _clients = new ArrayList<>();
        _socketManager = new SocketManager(this);
        _debugMode = false;

        _t = new Thread(this);
        _t.start();
    }

    @Override
    public void run() {
        //initialise les items
        initItems();

        // Boucle de jeu
        while (true) {
            //vérifier si les items ont tous été pris
            if(_world.getItems().isEmpty()){
                //envoyer le message de win
                this.getSocketManager().SEND_GAME_WIN();

                //mettre à zero tous les scores
                this.getSocketManager().SEND_INIT_SCORE();
                for(Player player : _world.getPlayers()){
                    player.setScore(0);
                }

                // remettre des items
                initItems();
                for (GameThread c : this.getClients()) {
                    for (Item item : _world.getItems()) {
                        this.getSocketManager().SEND_NEW_ITEM_TO_PLAYER(c.getPlayer(), item);
                    }
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void initItems() {
        for (int i = 0 ; i < 4 ; i++){
            int[] values = {1,2,3,4,5};
            for (int value : values){
                Item item = new Item(getRandomCoord(), ItemType.getById(value));
                _world.addItem(item);
            }
        }
    }

    public Coordinate getRandomCoord() {
        Map map = _world.getMap();
        Coordinate coord;
        Cell cell;
        do {
            coord = new Coordinate((int)(Math.random() * map.getWidth()), (int)(Math.random() * map.getHeight()));
            cell = map.getCells()[coord.getX()][coord.getY()];
        } while (!map.isCellAtCoordinate(coord) || !cell.getType().equals(CellType.FLOOR) || map.getItemAtCoordinate(_world,coord) !=null || map.isPlayerAtCoordinate(_world,coord));
        return coord;
    }

    public World getWorld() {
        return _world;
    }

    public List<GameThread> getClients() {
        return _clients;
    }

    public SocketManager getSocketManager() {
        return _socketManager;
    }

    public void setDebugMode(boolean state) {
        _debugMode = state;
    }

    public boolean hasDebugMode() {
        return _debugMode;
    }

}

