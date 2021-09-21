package chatrooms.client.chatroom;

import chatrooms.client.ClientType;
import chatrooms.interfaces.MessageWriter;
import chatrooms.message.MessageFeed;
import chatrooms.port.PortNumbers;
import chatrooms.view.ChatRoomPanel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Create a new ChatRoom
 */
public class ChatRoom implements MessageWriter {

    private int port;
    private ServerSocket serverSocket;
    private boolean running;
    private final PropertyChangeSupport changeSupport;
    private final MessageFeed messageFeed;
    private final ArrayList<Socket> botSockets;


    /**
     * Creates a new ChatRoom and sets default port value
     */
    public ChatRoom() {
        this(PortNumbers.INVALID_PORT);
    }

    /**
     * Creates a new ChatRoom with Port port
     *
     * @param port Set the chatroom port
     */
    public ChatRoom(int port) {
        this.port = port;
        this.running = false;
        this.messageFeed = new MessageFeed();
        this.botSockets = new ArrayList<>();
        this.changeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Return whether the chatroom is runnin
     *
     * @return running boolean
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Return the PropertyChangeSupport
     *
     * @return PropertyChangeSupport of the chatroom
     */
    public PropertyChangeSupport getChangeSupport() {
        return changeSupport;
    }

    /**
     * Get the sockets of the bots
     *
     * @return ArrayList with all sockets of connected bots
     */
    public ArrayList<Socket> getBotSockets() {
        return botSockets;
    }

    /**
     * Get port
     *
     * @return Port
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Get message feed
     *
     * @return MessageFeed
     */
    public MessageFeed getMessageFeed() {
        return this.messageFeed;
    }

    /**
     * Reads port number from client and checks whether it is in correct format
     * Return the port number input from the user
     */
    private void getFreePort() {
        try {
            serverSocket = new ServerSocket(0);
            port = serverSocket.getLocalPort();
        } catch (IOException e) {
            System.out.println("Server socket with port 0 failed to create");
        }

    }

    /**
     * private ArrayList<String> getMessageFeed() {
     * return new ArrayList<>(this.messageFeed.getMessages());
     * }
     * <p>
     * /**
     * Connect to the Main server
     */
    private boolean registerToServer() {
        try {
            if (port == PortNumbers.INVALID_PORT) {
                getFreePort();
            }
            Socket socket = new Socket("localhost", PortNumbers.SERVER_PORT);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeInt(ClientType.CHATROOM);
            outputStream.writeInt(port);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            System.out.println("Connection to main server socket failed!");
            return false;
        }
        return true;
    }


    /**
     * Register to main server and start spawning bots and threads that listen for bot messages
     */
    public void start() {
        if (!registerToServer()) {
            return;
        }
        setupGUI();
        running = true;
        while (running) {
            Socket s;
            try {
                s = serverSocket.accept();
                botSockets.add(s);
                sendToOneBot(s);
                Thread listenThread = new Thread(new ThreadedBotListener(s, this));
                listenThread.start();
            } catch (IOException e) {
                System.out.println("Socket could not be accepted!");
            }

        }

    }

    /**
     * Send one bot a message to initiate messagefeed updating
     *
     * @param s Socket
     */
    private void sendToOneBot(Socket s) {
        try {
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            outputStream.writeUTF(" Welcome");
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Message could not be sent over socket!");
        }

    }


    /**
     * Distribute the latest message to all connected bot sockets
     *
     * @param message String message
     */
    public void distributeMessage(String message) {
        for (Socket s : botSockets) {
            try {
                DataOutputStream printWriter = new DataOutputStream(s.getOutputStream());
                printWriter.writeUTF(message);
                printWriter.flush();
            } catch (IOException e) {
                System.out.println("Message could not be sent over socket!");
            }
        }
    }


    /**
     * Sets up the basics for the GUI
     */
    private void setupGUI() {
        SwingUtilities.invokeLater(() -> {
            ChatRoomPanel panel = new ChatRoomPanel(this);
            JFrame frame = new JFrame();

            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    /**
     * Add new message to message array list
     *
     * @param message New message
     */
    @Override
    public void sendMessage(String message) {
        PropertyChangeEvent messageEvent = new PropertyChangeEvent(this, "Adding", "", message);
        changeSupport.firePropertyChange(messageEvent);
    }

    /**
     * Write a message to the UI chatroom
     *
     * @param message String message
     */
    @Override
    public void writeMessage(String message) {
        PropertyChangeEvent messageEvent = new PropertyChangeEvent(this, "Writing", "", message);
        changeSupport.firePropertyChange(messageEvent);
    }

    /**
     * Method used to add PropertyChangeListeners to changeSupport
     *
     * @param listener listener to be added to changeSupport
     */
    public void addListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove a bot socket from this chatroom's arraylist of sockets
     *
     * @param socket Socket
     */
    public void removeSocket(Socket socket) {
        botSockets.remove(socket);
    }
}
