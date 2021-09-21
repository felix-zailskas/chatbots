package chatrooms.server;

/**
 * Server main to start server
 */
public class ServerMain {

    /**
     * Create new server and start it
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}

