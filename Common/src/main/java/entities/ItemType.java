package entities;

public enum ItemType {
    GREEN(1,1, "textures/items/green_gem.png"),
    BLUE(2,2, "textures/items/blue_gem.png"),
    RED(3,4, "textures/items/red_gem.png"),
    VIOLET(4,8, "textures/items/violet_gem.png"),
    CHEST(5,10, "textures/items/chest.png");

    /**
     * Identifiant du type de cellule
     */
    private int _id;

    /**
     * Valeur de l'item
     */
    private int _value;

    /**
     * Texture de l'item pour le rendu
     */
    private String _texture;


    ItemType(int id, int value, String texture) {
        _id = id;
        _value = value;
        _texture = texture;
    }

    public int getId() {
        return _id;
    }

    public int getValue(){return _value;}

    public String getTexture() {
        return _texture;
    }


    /**
     * Permet de retrouver un type d'item à partir de son identifiant
     * @param id : identifiant de l'item à récupérer
     * @return l'item recherché si l'identifiant existe sinon null
     */
    public static ItemType getById(int id) {
        for (ItemType type : ItemType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

}
