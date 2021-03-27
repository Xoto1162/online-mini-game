package graphics.camera;

import utils.Coordinate;

public interface Camera {

    /**
     * Coordonnée sur laquel le centre de la caméra pointe
     * @return
     */
    Coordinate getCenter();

    /**
     * Nombre de bloques visible total en longueur et largeur
     * @return
     */
    int getGridSize();

    /**
     * Nombre de bloques viisble de chaque coté du centre de la caméra
     * @return int
     */
    int getSize();

}
