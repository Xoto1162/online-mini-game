package entities;

public enum Direction {
    /**
     * Direction du personnage
     *   0
     * 3   1
     *   2
     */
    UP(0, 0, 1),
    DOWN(2, 0, -1),
    RIGHT(1, 1, 0),
    LEFT(3, -1, 0);


    /**
     * Identifiant de la direction
     */
    private int _id;

    /**
     * Valeur en x
     */
    private int _x;

    /**
     * Valeur en y
     */
    private int _y;


    Direction(int id, int x, int y) {
        _id = id;
        _x = x;
        _y = y;
    }

    public int getId() {
        return _id;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }


    /**
     * Permet de retourner la direction avec son id
     * @param id : identifiant de la cellule à récupérer
     * @return direction recherché si l'identifiant existe sinon null
     */
    public static Direction getById(int id) {
        for (Direction type : Direction.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }
}
