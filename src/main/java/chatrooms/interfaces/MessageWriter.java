package chatrooms.interfaces;

/**
 * Interface for writing and sending a message over a socket
 */
public interface MessageWriter {

    /**
     * Send message
     *
     * @param message String message
     */
    void sendMessage(String message);

    /**
     * Write message to the UI
     *
     * @param message String message
     */
    void writeMessage(String message);
}
