package graphics;

import entities.Item;
import entities.Player;
import graphics.camera.AllMapCamera;
import graphics.camera.Camera;
import map.Cell;
import utils.Coordinate;
import world.World;

public class Renderer {

    /**
     * Caméra pour le rendu de la scene
     */
    private Camera _camera;

    /**
     * Outil pour dessiner le rendu
     */
    private Draw _draw;

    /**
     * Monde à dessiner
     */
    private World _world;

    public Renderer(World world, AllMapCamera camera) {
        _world = world;
        _camera = camera;

        _draw = new Draw();

        // permet le double buffering, pour permettre l'animation
        _draw.enableDoubleBuffering();

        // permet de redimensioner la fenetre
        _draw.setCanvasSize(
                (_camera.getGridSize()) * 40,
                (_camera.getGridSize()) * 40
        );
    }

    /**
     * Dessine l'etat actuel du jeu
     */
    public void render() {
        _draw.clear(Draw.BLACK);

        // Rendu de la map
        for(int i = _camera.getSize(); i > - _camera.getSize() - 1; i--) {
            for(int j = _camera.getSize(); j > - _camera.getSize() - 1; j--) {
                Coordinate coordCell = new Coordinate(_camera.getCenter().getX() + i, _camera.getCenter().getY() + j);
                int x = i + _camera.getSize();
                int y = j + _camera.getSize();
                Coordinate drawPosition = new Coordinate(x, y);
                if(_world.getMap().isCellAtCoordinate(coordCell)) {
                    drawCell(_world.getMap().getCells()[coordCell.getX()][coordCell.getY()], drawPosition);
                }
            }
        }

        // Rendu des joueurs
        for (Player p : _world.getPlayers()) {
            if (isInScope(p.getCoordinate())) {
                Coordinate diff = p.getCoordinate().diff(_camera.getCenter());
                drawEntity(p, new Coordinate(
                        _camera.getSize() - diff.getX(),
                        _camera.getSize() - diff.getY()
                ));
            }
        }

        // Rendu des itemps
        for (Item i : _world.getItems()) {
            if (isInScope(i.getCoordinate())) {
                Coordinate diff = i.getCoordinate().diff(_camera.getCenter());
                drawItem(i, new Coordinate(
                        _camera.getSize() - diff.getX(),
                        _camera.getSize() - diff.getY()
                ));
            }
        }
    }

    private void drawEntity(Player entity, Coordinate coord) {
        int textureID = entity.getDirection().getId();

        // Rendu de la texture de l'etre vivant a la coordonnée précisée par la caméra
        _draw.picture(
                (coord.getX() + 0.5) / _camera.getGridSize(),
                (coord.getY() + 0.5) / _camera.getGridSize(),
                entity.getTexture() + textureID + ".png",
                1.0 / _camera.getGridSize(),
                1.0 / _camera.getGridSize()
        );

        // Rendu de la barre de vie de l'etre vivant, en dessous de lui
        if(entity.getScore() > 0) {
            _draw.setPenColor(Draw.GREEN);
            _draw.filledRectangle(
                    (coord.getX() + 0.5) / _camera.getGridSize(),
                    (coord.getY() + 1.2) / _camera.getGridSize(),
                    (0.4d * ((float) entity.getScore() / entity.getMaxScore())) / _camera.getGridSize(),
                    0.05d / _camera.getGridSize());
            _draw.setPenColor(Draw.BLACK);
        }

        //rang au dessus du score
        _draw.text((coord.getX() + 0.5) / _camera.getGridSize(),
                (coord.getY() + 1.4) / _camera.getGridSize(),
                String.valueOf(_world.getRang(entity)));

        //pseudo en dessous du player
        _draw.text((coord.getX() + 0.5) / _camera.getGridSize(),
                (coord.getY() - 0.2) / _camera.getGridSize(),
                String.valueOf(entity.getName()));

    }

    private void drawCell(Cell cell, Coordinate coord) {
        _draw.picture(
                (coord.getX() + 0.5) / _camera.getGridSize(),
                (coord.getY() + 0.5) / _camera.getGridSize(),
                cell.getType().getTexture(),
                1.0 / _camera.getGridSize(),
                1.0 / _camera.getGridSize()
        );
    }

    private void drawItem(Item item, Coordinate coord) {
        _draw.picture(
                (coord.getX() + 0.5) / _camera.getGridSize(),
                (coord.getY() + 0.5) / _camera.getGridSize(),
                item.getType().getTexture(),
                1.0 / _camera.getGridSize(),
                1.0 / _camera.getGridSize()
        );
    }

    public boolean isInScope(Coordinate coord) {
        Coordinate pCoord = _camera.getCenter();
        if (coord.getX() >= pCoord.getX() - _camera.getSize() && coord.getX() <= pCoord.getX() + _camera.getSize()) {
            if (coord.getY() >= pCoord.getY() - _camera.getSize() && coord.getY() <= pCoord.getY() + _camera.getSize()) {
                return true;
            }
        }
        return false;
    }

    public Camera getCamera() {
        return _camera;
    }

    public Draw draw() {
        return _draw;
    }

    public World getWorld() {
        return _world;
    }

}

