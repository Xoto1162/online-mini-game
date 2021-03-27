package entities;

import utils.Coordinate;

import java.util.UUID;

public class Player extends LivingEntity {

    private static final String TEXTURE = "textures/player/";

    private Player() {
        super(TEXTURE);
    }

    public Player(Coordinate coordinate, int scoreMax) {
        super(TEXTURE, coordinate, scoreMax);
    }

    public static Player fromPacket(String[] packet) {
        Player p = new Player();
        p._uuid = UUID.fromString(packet[1]);
        p._coordinate = new Coordinate(Integer.parseInt(packet[2]), Integer.parseInt(packet[3]));
        p._score = Integer.parseInt(packet[4]);
        p._scoreMax = Integer.parseInt(packet[5]);
        p._name = packet[6];
        return p;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Player player = (Player) o;

        return this.getName().equals(player.getName())
                && this.getScore() == player.getScore()
                && this.getDirection().equals(player.getDirection())
                && this.getCoordinate().equals(player.getCoordinate())
                && this.getMaxScore() == player.getMaxScore()
                && this.getTexture().equals(player.getTexture());
    }

}
