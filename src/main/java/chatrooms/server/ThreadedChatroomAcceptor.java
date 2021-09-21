package chatrooms.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * This part of the server will run concurrently. Over the socket it is provided
 * it will register the chatrooms port as an active port in the server.
 */
public class ThreadedChatroomAcceptor implements Runnable {

    private final Server server;
    private final Socket s;

    /**
     * Create a new ThreadedChatroom Handler
     *
     * @param server Server
     * @param s      Socket
     */
    public ThreadedChatroomAcceptor(Server server, Socket s) {
        this.server = server;
        this.s = s;
    }

    /**
     * Reads the port of the chatroom by using the sockets DataInputStream and adds
     * it as an active port to the server.
     */
    @Override
    public void run() {
        try {
            int chatRoomPort = new DataInputStream(s.getInputStream()).readInt();
            server.addPort(chatRoomPort);
            server.writeMessage("Chatroom with port number: " + chatRoomPort + " has connected to the Server.");
        } catch (IOException e) {
            System.out.println("Port from Chatroom could not be registered!");
            server.writeMessage("Port from Chatroom could not be registered!");
        }
    }
}
