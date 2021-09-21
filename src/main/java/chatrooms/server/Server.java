package chatrooms.server;

import chatrooms.client.ClientType;
import chatrooms.interfaces.MessageWriter;
import chatrooms.port.PortNumbers;
import chatrooms.view.ServerPanel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Server in the client-server model
 */
public class Server implements MessageWriter {

    private final ArrayList<Integer> ports;
    private boolean running;
    private final PropertyChangeSupport changeSupport;

    /**
     * Creates a new server with ports, and clients, and a property change support for updating clients
     */
    public Server() {
        this.ports = new ArrayList<>();
        this.running = false;
        this.changeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Getter method for the running boolean.
     *
     * @return true if the server is running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Getter method for the ports.
     *
     * @return the ports stored in the server
     */
    public ArrayList<Integer> getPorts() {
        return ports;
    }

    /**
     * Start the server and perform the following steps:
     * 1. Wait for a connection from a client
     * 2. Create a new Socket for the client to connect to
     * 3. Wait for the information on what client has connected
     * 4. Create the corresponding Thread for the type of client connected
     */
    public void start() {
        setupGUI();
        try (ServerSocket ss = new ServerSocket(PortNumbers.SERVER_PORT)) {
            running = true;
            while (running) {
                Socket s = ss.accept();
                DataInputStream inputStream = new DataInputStream(s.getInputStream());
                int clientType = inputStream.readInt();
                if (clientType == ClientType.CHATROOM) {
                    Thread t = new Thread(new ThreadedChatroomAcceptor(this, s));
                    t.start();
                }
                if (clientType == ClientType.BOT) {
                    Thread t = new Thread(new ThreadedBotPortDistributor(this, s));
                    t.start();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not recognize client type!");
            writeMessage("Could not recognize client type!");
        }
    }

    /**
     * Adds an active port to the list of ports of the server.
     *
     * @param port active port to add
     */
    public void addPort(int port) {
        if (port != -1) {
            this.ports.add(port);
        }
    }

    /**
     * Empty method, do not need send message
     *
     * @param message String message
     */
    @Override
    public void sendMessage(String message) {
    }

    /**
     * Write a message to the UI only, do not store in any other way
     *
     * @param message String message
     */
    @Override
    public void writeMessage(String message) {
        PropertyChangeEvent messageEvent = new PropertyChangeEvent(this, "Writing", null, message);
        changeSupport.firePropertyChange(messageEvent);
    }

    /**
     * Sets up the basics for the GUI
     */
    private void setupGUI() {
        SwingUtilities.invokeLater(() -> {
            ServerPanel panel = new ServerPanel(this);
            JFrame frame = new JFrame();

            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    /**
     * Method used to add PropertyChangeListeners to changeSupport
     *
     * @param listener listener to be added to changeSupport
     */
    public void addListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
}
