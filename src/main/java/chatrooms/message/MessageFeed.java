package chatrooms.message;

import java.util.ArrayList;

/**
 * Message model used for the client. It stores the last message that was sent to/received from the server
 */
public class MessageFeed {
    private final ArrayList<String> messages;
    private String message;

    /**
     * Creates a new Message Feed with default arraylist of messages.
     */
    public MessageFeed() {
        this(new ArrayList<>());
    }

    /**
     * Creates a new MessageFeed filled with the base messages arraylist provided
     *
     * @param baseMessages ArrayList
     */
    public MessageFeed(ArrayList<String> baseMessages) {
        this.messages = new ArrayList<>(baseMessages);
    }

    /**
     * Return the messages arraylist
     *
     * @return String arraylist of messages
     */
    public ArrayList<String> getMessages() {
        return this.messages;
    }

    /**
     * Latest message
     *
     * @return String message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Creates a new PropertyChangeEvent every time the message is updated. It then calls firePropertyChange with this event
     *
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
        this.messages.add(message);
    }

}
