package map;

public enum CellType {

    VOID(0, "textures/cells/void.png", false),
    FLOOR(1, "textures/cells/floor.png", true),
    WALL(2, "textures/cells/brick.png", false);

    /**
     * Identifiant du type de cellule
     */
    private int _id;

    /**
     * Texture de la cellule pour le rendu
     */
    private String _texture;

    /**
     * Indique s'il est possible de marcher sur la cellule
     */
    private boolean _walkable;

    CellType(int id, String texture, boolean walkable) {
        _id = id;
        _texture = texture;
        _walkable = walkable;
    }

    public int getId() {
        return _id;
    }

    public String getTexture() {
        return _texture;
    }

    public boolean isWalkable() {
        return _walkable;
    }

    /**
     * Permet de retrouver une cellule à partir de son identifiant
     * @param id : identifiant de la cellule à récupérer
     * @return la cellule recherché s'il l'identifiant existe sinon null
     */
    public static CellType getById(int id) {
        for (CellType type : CellType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

}

