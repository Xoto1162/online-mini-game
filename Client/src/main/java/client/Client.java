package client;

import entities.Player;
import graphics.camera.AllMapCamera;
import graphics.Renderer;
import graphics.UserInteraction;
import network.Connection;
import world.World;

import javax.swing.*;

public class Client implements Runnable {

    public final boolean DEBUG = true;

    private World _world;

    private Player _player;

    private Connection _connection;

    private Renderer _renderer;

    public Client(String host, int port, String pseudo) {
        _world = new World();

        _connection = new Connection(this, host, port);
        // Envoi du socket avec le pseudo
        _connection.getSocketManager().SEND_PLAYER_NAME(pseudo);

        // On attend de recevoir la réponse du serveur avec le pseudo du personnage
        while (this.getPlayer().getName() == null) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        _renderer = new Renderer(_world, new AllMapCamera(_world));
        _renderer.draw().setLocationRelativeTo(null);
        _renderer.draw().addListener(new UserInteraction(this, _renderer));
        _renderer.draw().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        // la boucle principale de notre jeu
        while (true) {
            //GameWorld.raycasting();

            //GameWorld.step();

            // dessine la carte
            _renderer.render();

            // Montre la fenetre graphique mise jour et attends 20 millisecondes
            _renderer.draw().show();
            _renderer.draw().pause(20);
        }
    }

    public World getWorld() {
        return _world;
    }

    public Player getPlayer() {
        return _player;
    }

    public Connection getConnection() {
        return _connection;
    }

    public Renderer getRenderer() {
        return _renderer;
    }

    public void setPlayer(Player player) {
        _player = player;
    }
}
