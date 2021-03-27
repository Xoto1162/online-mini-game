package graphics.camera;

import utils.Coordinate;
import world.World;

public class AllMapCamera implements Camera {

    private World _world;

    public AllMapCamera(World world) {
        _world = world;
    }

    @Override
    public Coordinate getCenter() {
        return new Coordinate(_world.getMap().getWidth()/2, _world.getMap().getHeight()/2);
    }

    @Override
    public int getGridSize() {
        return getSize()*2+1;
    }

    @Override
    public int getSize() {
        return Math.max(_world.getMap().getHeight(), _world.getMap().getWidth())/2;
    }

}
