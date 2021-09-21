package chatrooms.server;

import chatrooms.port.PortNumbers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This part of the server will run concurrently. Over the socket it is provided
 * it will provide a Bot with an active port of a chatroom. As long as there is no
 * available chatroom the bot will receive an invalid port number.
 */
public class ThreadedBotPortDistributor implements Runnable {

    private final Server server;
    private final Socket s;

    /**
     * Creates a new ThreadedBotHandler
     *
     * @param server Server
     * @param s      Socket
     */
    public ThreadedBotPortDistributor(Server server, Socket s) {
        this.server = server;
        this.s = s;
    }

    /**
     * Writes a valid port number to the Bot using the sockets DataOutputStream. While no
     * ports are available it writes an invalid port of -1.
     */
    @Override
    public void run() {
        try {
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            while (server.getPorts().size() <= 0) {
                outputStream.writeInt(PortNumbers.INVALID_PORT);
            }
            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            int currPort = inputStream.readInt();
            String name = inputStream.readUTF();
            int newPort = checkPort(currPort);
            outputStream.writeInt(newPort);
            server.writeMessage("Gave <" + name + "> the port: " + newPort);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            System.out.println("No port could be distributed to the Bot!");
            server.writeMessage("No port could be distributed to the Bot!");
        }
    }

    /**
     * Check if the current port does not match the current selected port as it loops over all active chatroom ports
     *
     * @param currPort Current port number
     * @return New currPort
     */
    private int checkPort(int currPort) {
        ArrayList<Integer> ports = server.getPorts();
        Collections.shuffle(ports);
        for (int i : ports) {
            if (i != currPort) return i;
        }
        return PortNumbers.INVALID_PORT;
    }

}
