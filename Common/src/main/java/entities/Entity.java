package entities;

import utils.Coordinate;

import java.util.UUID;

public abstract class Entity {

    /**
     * UUID de l'entitée
     */
    protected UUID _uuid;

    /**
     * Coordonnée de l'entité
     */
    protected Coordinate _coordinate;

    /**
     * la texture de l'entité
     */
    protected String _texture;

    public Entity(Coordinate coordinate) {
        _uuid = UUID.randomUUID();
        _coordinate = coordinate;
    }

    public Entity(String texture, Coordinate coordinate) {
        this(coordinate);
        _texture = texture;
    }

    protected Entity(String texture) {
        _texture = texture;
    }

    /**
     * Permet de récupérer le UUID
     * @return nom
     */
    public UUID getUUID() {
        return _uuid;
    }

    /**
     * Permet de replacer l'entité a une nouvelle coordonnée
     * @param coordinate
     */
    public void setCoordinate(Coordinate coordinate){
        _coordinate = coordinate;
    }

    /**
     * Permet de récupérer la coordonnée de l'entité
     * @return this.position
     */
    public Coordinate getCoordinate() {
        return _coordinate;
    }

    public String getTexture() {
        return _texture;
    }

    /**
     * UUID:x:y
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(_uuid).append(":")
                .append(_coordinate.getX()).append(":").append(_coordinate.getY());
        return str.toString();
    }
}
