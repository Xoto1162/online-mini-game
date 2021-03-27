package utils;

public class Coordinate {

    /**
     * Coordonnée sur l'axe x
     */
    private int _x;

    /**
     * Coordonnée sur l'axe y
     */
    private int _y;

    public Coordinate(int x, int y) {
        _x = x;
        _y = y;
    }

    /**
     * Indique si deux coordonnée sont identiques
     * @param c : Coordonnée à comparer
     * @return true si les coordonnées sont identiques sinon false
     */
    public boolean equals(Coordinate c) {
        return (getX() == c.getX() && getY() == c.getY());
    }

    /**
     * Calcul la distance avec une autre coordonnée
     * @param cord : Coordonnée avec laquel calculer la distance
     * @return la distance entre les deux coordonnées
     */
    public double distanceTo(Coordinate cord) {
        return Math.sqrt(Math.pow(getX() - cord.getX(), 2) + Math.pow(getY() - cord.getY(), 2));
    }

    /**
     * Retourne la différence de coordonée entre coord et this
     * @param coord
     * @return
     */
    public Coordinate diff(Coordinate coord) {
        return new Coordinate(
                coord.getX() - getX(),
                coord.getY() - getY()
        );
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public void setX(int x) {
        _x = x;
    }

    public void setY(int y) {
        _y = y;
    }

    public void set(int x, int y) {
        _x = x;
        _y = y;
    }

    @Override
    public String toString() {
        return "x=" + _x + " y=" + _y;
    }
}

