import client.Client;
import entities.Direction;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Test {

    public static List<Client> _clients;

    public final static int NB_CLIENT = 6;

    public static void main(String[] args) {
        _clients = new LinkedList<>();

        for (int i = 0; i < NB_CLIENT; i++) {
            Client c = new Client("localhost", 1337, "player" + i);
            _clients.add(c);
            (new Thread(c)).start();
        }

        while (true) {
            for (Client c : _clients) {
                Direction[] directions = { Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT };

                Direction d = directions[ThreadLocalRandom.current().nextInt(0, 4)];
                int n = ThreadLocalRandom.current().nextInt(1, 6);

                for (int i = 0; i < n; i++) {
                    c.getConnection().getSocketManager().SEND_PLAYERMOVE_PACKET(d);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Iterator<Client> ite = _clients.iterator();
            Client c = ite.next();
            while (ite.hasNext()) {
                Client c2 = ite.next();
                if (!c.getWorld().equals(c2.getWorld())) {
                    System.out.println("Les états ne correspondent pas");
                    System.exit(-1);
                }
            }

            if (c.getWorld().getItems().isEmpty()) {
                System.out.println("Test OK !");
                System.exit(0);
            }
        }
    }

}
