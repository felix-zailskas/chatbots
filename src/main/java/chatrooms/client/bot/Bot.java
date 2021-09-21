package chatrooms.client.bot;

import chatrooms.client.ClientType;
import chatrooms.interfaces.MessageWriter;
import chatrooms.message.MessageFeed;
import chatrooms.message.Messages;
import chatrooms.port.PortNumbers;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Abstract Bot class that has two sub type bot classes
 */
public abstract class Bot implements Runnable, MessageWriter {

    protected int portNumber;
    protected Socket socket;
    protected final Random random;
    protected PrintWriter out;
    protected BufferedReader in;
    protected final MessageFeed messageFeed;
    protected final String name;
    protected boolean running;

    /**
     * Create a new Bot with default port number and random chance of leaving
     */
    public Bot() {
        this.portNumber = PortNumbers.INVALID_PORT;
        this.messageFeed = new MessageFeed(Messages.BASE_MESSAGES);
        this.random = new Random(System.currentTimeMillis());
        this.name = chooseName();
        this.running = false;
    }

    /**
     * Gives the bot a random name from a list of available names
     *
     * @return random name
     */
    private String chooseName() {
        int rnd = new Random().nextInt(BotNames.BOT_NAMES.size());
        String name = BotNames.BOT_NAMES.get(rnd);
        BotNames.BOT_NAMES.remove(name);
        return "Bot " + name;
    }

    /**
     * Getter for port number the bot is connected to
     *
     * @return port number
     */
    public int getPortNumber() {
        return portNumber;
    }

    /**
     * Setter for port number
     *
     * @param portNumber port number
     */
    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * Getter for the name of the bot
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return an ArrayList of messages
     *
     * @return new ArrayList
     */
    public ArrayList<String> getMessages() {
        return this.messageFeed.getMessages();
    }

    /**
     * Attempt to connect Bot to an available and active port
     * Inform the server we are indeed a bot
     * Get port, close socket
     */
    protected synchronized void getPortFromServer() {
        try {
            socket = new Socket("localhost", PortNumbers.SERVER_PORT);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeInt(ClientType.BOT);
            do {
                outputStream.writeInt(portNumber);
                outputStream.writeUTF(this.getName());
                Thread.sleep(1000);
                portNumber = inputStream.readInt();
                Thread.sleep(1000);
            } while (portNumber < 0);
            socket.close();
        } catch (IOException e) {
            System.out.println("Could not receive port from server!");
        } catch (InterruptedException e) {
            System.out.println("Interrupted while receiving port!");
        }
    }

    /**
     * Try to connect to an available chat room
     *
     * @return Whether connection to a socket was indeed successful
     */
    protected boolean connectToChatRoom() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", portNumber), 1000);

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            sendMessage(name);

            if (!socket.isConnected()) {
                System.out.println("The socket is not connected!");
                return true;
            }
        } catch (IOException e) {
            System.out.println("Could not connect to the chatroom with port " + portNumber);
            return true;
        }
        return false;
    }

    /**
     * Sends a message to the server using the PrintWriter and adding it to the message feed
     *
     * @param message The message to be sent
     */
    @Override
    public void sendMessage(String message) {
        messageFeed.setMessage(message);
        out.println(this.getName() + " : " + message);
    }

    /**
     * Write a message to the server without adding it to the message feed
     *
     * @param message Message
     */
    @Override
    public void writeMessage(String message) {
        out.println(this.getName() + " : " + message);
    }

    /**
     * Creates a message containing of random words taken from the bots message feed
     *
     * @return compound message
     */
    public static String compoundMessage() {
        StringBuilder compound = new StringBuilder();
        Random random = new Random();
        int sizeRnd = Messages.BASE_MESSAGES.size() - random.nextInt(Messages.BASE_MESSAGES.size());
        while (sizeRnd > 0) {
            int phraseRnd = random.nextInt(Messages.BASE_MESSAGES.size());
            String phrase = Messages.BASE_MESSAGES.get(phraseRnd);
            String[] arr = phrase.split("\\s+");
            int wordRnd = random.nextInt(arr.length);
            compound.append(" ").append(arr[wordRnd]);
            sizeRnd--;
        }
        return compound.toString();
    }

    /**
     * Receives a message from the server the bot is connected to and adds it to its own message feed.
     */
    protected void readFromChatRoom() {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            String input = inputStream.readUTF();
            String sub = input.substring(input.indexOf(":") + 2);
            this.getMessages().add(sub.trim());
        } catch (IOException e) {
            System.out.println("Could not read from Chatroom!");
        }
    }

    /**
     * Generates a message for the Bot to send to the chatroom
     */
    protected void generateMessage() {
        String message;
        int rnd = random.nextInt(messageFeed.getMessages().size());
        if (random.nextInt(100) > BotSettings.COMPOUND_CHANCE) {
            message = messageFeed.getMessages().get(rnd);
            if (random.nextInt(100) > BotSettings.SPAM_CHANCE) {
                spamMessage(message);
                return;
            }
        } else message = compoundMessage();
        this.sendMessage(message);
    }

    /**
     * Sends a message multiple times in a row
     *
     * @param message message to spam
     */
    private void spamMessage(String message) {
        int rnd = random.nextInt(BotSettings.MAX_SPAM_DURATION);
        while (rnd > 0) {
            this.sendMessage(message);
            rnd--;
        }
    }

    /**
     * Shuts this Bot down by closing its streams and its socket.
     * The name of the Bot is again available and gets added to the pool of names.
     */
    public synchronized void shutdown() {
        try {
            sendMessage(Messages.KILLED_MESSAGE);
            running = false;
            BotNames.BOT_NAMES.add(name.substring(4));
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.out.println("Bot could not be shutdown!");
        }
    }

}
