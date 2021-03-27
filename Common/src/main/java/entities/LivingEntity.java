package entities;

import utils.Coordinate;

public abstract class LivingEntity extends Entity {

    /**
     * Direction de l'entity
     */
    protected Direction _direction;

    /**
     * Score de l'entitée
     */
    protected int _score;

    /**
     * Score maximum de l'entité
     */
    protected int _scoreMax;

    /**
     * Nom de l'entité
     */
    protected String _name;

    public LivingEntity(Coordinate coordinate, int scoreMax) {
        super(coordinate);
        _scoreMax = scoreMax;
        _score = 0;
        _direction = Direction.DOWN;
    }

    public LivingEntity(String texture, Coordinate coordinate, int scoreMax) {
        super(texture, coordinate);
        _scoreMax = scoreMax;
        _score = 0;
        _direction = Direction.DOWN;
    }

    protected LivingEntity(String texture) {
        super(texture);
        _direction = Direction.DOWN;

    }

    public int getScore() {
        return _score;
    }

    public int getMaxScore() {
        return _scoreMax;
    }

    public Direction getDirection() {
        return _direction;
    }

    public void setDirection(Direction direction) {
        _direction = direction;
    }

    public void setScore(int score) {
        _score = score;
    }

    public String getName(){
        return _name;
    }

    public void setName(String name){
        _name = name;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(super.toString())
                .append(":").append(_score).append(":").append(_scoreMax).append(":").append(_name);
        return str.toString();
    }
}

