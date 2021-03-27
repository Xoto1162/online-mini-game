import server.GameConnector;
import server.GameServer;
import world.World;

public class Main {

    public final static int SERVER_PORT = 1337;

    public static void main(String[] args) {
        System.out.println("---- Initialisation du monde ----");
        long start = System.currentTimeMillis();
        World world = new World();
        System.out.println("Initialisé en " + (System.currentTimeMillis() - start) + "ms");
        System.out.println();

        System.out.println("---- Création du serveur de jeu ----");
        GameConnector server = new GameConnector(SERVER_PORT, new GameServer(world));
        server.getGameServer().setDebugMode(true);
        System.out.println("Serveur de jeu initialisé sur le port " + SERVER_PORT);
        System.out.println();

        System.out.println("---- En attente de nouvelles connexions ----");
    }

}
