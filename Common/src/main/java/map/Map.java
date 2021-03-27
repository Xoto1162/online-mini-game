package map;

import entities.Item;
import entities.Player;
import utils.Coordinate;
import world.World;

import java.io.*;
import java.util.Collection;

public class Map {

    public Map(int width, int height) {
        _cells = new Cell[width][height];
    }

    /**
     * Tableau des cellules qui representent la carte
     */
    private Cell[][] _cells;

    /**
     * Indique si une cooronnée est dans la map
     * @param coord : coordonnée de la cellule
     * @return true si la coordonnée est dans la map false sinon
     */
    public boolean isCellAtCoordinate(Coordinate coord) {
        if(coord.getX() >= 0 && coord.getX() < _cells.length) {
            if(coord.getY() >= 0 && coord.getY() < _cells[coord.getX()].length) {
                return true;
            }
        }
        return false;
    }

    public int getWidth() {
        return _cells.length;
    }

    public int getHeight() {
        return _cells[0].length;
    }

    public Cell[][] getCells() {
        return _cells;
    }

    public static Map readFromFile(String mapName) {
        Map map = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Map.class.getResourceAsStream("/" + mapName)
            ));

            String[] size = reader.readLine().split(" ");
            map = new Map(Integer.parseInt(size[0]), Integer.parseInt(size[1]));

            for(int i = 0; i < map.getHeight(); i++) {
                String[] line = reader.readLine().split(" ");
                for(int j = 0; j < map.getWidth(); j++) {
                    int cellType = Integer.parseInt(line[j]);
                    int x = j;
                    int y = map.getHeight() - 1 - i;
                    map._cells[x][y] = new Cell(CellType.getById(cellType));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Indique si l'on peut se deplacer a une postion de la map
     * @param movement
     * 				Position ou l'on souhaite se deplacer
     * @return true si l'on peut se deplacer false sinon
     */
    public boolean canMoveAt(World world, Coordinate player, Coordinate movement) {
        if (isCellAtCoordinate(movement)) {
            Cell cell = _cells[movement.getX()][movement.getY()];
            if (cell.getType().equals(CellType.FLOOR) && !isPlayerAtCoordinate(world, movement)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indique si un joueur est à une position de la map
     * @param world : world
     * @param coord : position de la map
     * @return true s'il y a un joueur à cette position false sinon
     */
    public boolean isPlayerAtCoordinate(World world, Coordinate coord){
        Collection<Player> players = world.getPlayers();
        for(Player player : players){
            if(player.getCoordinate().getX() == coord.getX() && player.getCoordinate().getY() == coord.getY()){
                return true;
            }
        }
        return false;
    }

    /**
     * collecte l'item à une position
     * @param world : world
     * @param coord : position de la map
     * @return null si rien sinon item
     */
    public Item getItemAtCoordinate(World world, Coordinate coord){
        Collection<Item> items = world.getItems();
        for(Item item : items){
            if(item.getCoordinate().getX() == coord.getX() && item.getCoordinate().getY() == coord.getY()){
                return item;
            }
        }
        return null;
    }

    /**
     * collecte le player à une position
     * @param world : world
     * @param coord : position de la map
     * @return null si rien sinon player
     */
    public Player getPlayerAtCoordinate(World world, Coordinate coord){
        Collection<Player> players = world.getPlayers();
        for(Player p : players){
            if(p.getCoordinate().getX() == coord.getX() && p.getCoordinate().getY() == coord.getY()){
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Map map = (Map) o;

        for (int i = 0 ; i < _cells.length ; i++){
            for(int j = 0 ; j < _cells[0].length ; j++){
                if (!_cells[i][j].equals(map._cells[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

}
