package graphics;

import client.Client;
import entities.Direction;

import java.awt.event.KeyEvent;

public class UserInteraction implements DrawListener {

    private Client _client;

    private Renderer _renderer;

    public UserInteraction(Client client, Renderer renderer) {
        _client = client;
        _renderer = renderer;
    }

    @Override
    public void mousePressed(double x, double y) {
        _client.getConnection().send("Mouse pressed");
    }

    @Override
    public void mouseDragged(double x, double y) {

    }

    @Override
    public void mouseReleased(double x, double y) {
        _client.getConnection().send("Mouse released");
    }

    @Override
    public void mouseClicked(double x, double y) {

    }

    @Override
    public void mouseMoved(double x, double y) {
    }

    @Override
    public void keyTyped(char c) {
    }

    @Override
    public void keyPressed(int keycode) {
        switch(keycode){
            case KeyEvent.VK_Z :
            case KeyEvent.VK_UP :
                _client.getConnection().getSocketManager().SEND_PLAYERMOVE_PACKET(Direction.UP);
                break;
            case KeyEvent.VK_Q :
            case KeyEvent.VK_LEFT :
                _client.getConnection().getSocketManager().SEND_PLAYERMOVE_PACKET(Direction.LEFT);
                break;
            case KeyEvent.VK_S :
            case KeyEvent.VK_DOWN :
                _client.getConnection().getSocketManager().SEND_PLAYERMOVE_PACKET(Direction.DOWN);
                break;
            case KeyEvent.VK_D :
            case KeyEvent.VK_RIGHT :
                _client.getConnection().getSocketManager().SEND_PLAYERMOVE_PACKET(Direction.RIGHT);
                break;
        }
    }

    @Override
    public void keyReleased(int keycode) {
    }

}
