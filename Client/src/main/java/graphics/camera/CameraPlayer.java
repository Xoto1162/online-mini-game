package graphics.camera;

import entities.Player;
import utils.Coordinate;

public class CameraPlayer implements Camera {

    /**
     * Taille de la caméra :
     * (Nombre de blocks visible de chaque coté du personnage)
     */
    private int _size;

    /**
     * Joueur que la caméra doit suivre
     */
    private Player _player;

    public CameraPlayer(Player player, int size) {
        _player = player;
        _size = size;
    }

    public Coordinate getCenter() {
        return _player.getCoordinate();
    }

    public int getGridSize() { return _size * 2 + 1; }

    public int getSize() {
        return _size;
    }

}
