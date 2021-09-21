package chatrooms.client;

import chatrooms.client.chatroom.ChatRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Chat room test
 */
class ChatRoomTest {

    private ChatRoom chatRoom;

    /**
     * Creates a new chatroom with port 8189 before each test
     */
    @BeforeEach
    void setUp() {
        chatRoom = new ChatRoom(8189);
    }

    /**
     * New chat room test, creating a new empty chat room
     */
    @Test
    void newChatRoomTest() {
        ChatRoom chatRoom1 = new ChatRoom();

        assertEquals(-1, chatRoom1.getPort());
        assertEquals(8189, chatRoom.getPort());

        assertNotNull(chatRoom1.getMessageFeed());
        assertNotNull(chatRoom.getMessageFeed());
        assertNotNull(chatRoom1.getBotSockets());
        assertNotNull(chatRoom.getBotSockets());
        assertNotNull(chatRoom1.getChangeSupport());
        assertNotNull(chatRoom.getChangeSupport());
        assertFalse(chatRoom1.isRunning());
        assertFalse(chatRoom.isRunning());

    }

    /**
     * Distribute message test, test sending a message to connected clients
     */
    @Test
    void distributeMessageTest() {
        Socket s1 = new Socket();
        Socket s2 = new Socket();
        chatRoom.getBotSockets().add(s1);
        chatRoom.getBotSockets().add(s2);
        try {
            DataInputStream inputStream1 = new DataInputStream(s1.getInputStream());
            DataInputStream inputStream2 = new DataInputStream(s2.getInputStream());
            chatRoom.distributeMessage("Test Message!");
            assertEquals("Test Message!", inputStream1.readUTF());
            assertEquals("Test Message!", inputStream2.readUTF());
            s1.close();
            s2.close();
        } catch (IOException e) {
            System.out.println("Socket error");
        }

    }

    /**
     * Send message test, send message to connected listeners
     */
    @Test
    void sendMessageTest() {
        PropertyChangeListener listener = evt -> {
            assertEquals("Test Message!", (String) evt.getNewValue());
            assertEquals("", (String) evt.getOldValue());
            assertEquals("Adding", evt.getPropertyName());
            assertNotEquals("", (String) evt.getNewValue());
        };
        chatRoom.addListener(listener);
        chatRoom.sendMessage("Test Message!");
    }

    /**
     * Write message test to connected listener
     */
    @Test
    void writeMessageTest() {
        PropertyChangeListener listener = evt -> {
            assertEquals("Test Message!", (String) evt.getNewValue());
            assertEquals("", (String) evt.getOldValue());
            assertEquals("Writing", evt.getPropertyName());
            assertNotEquals("", (String) evt.getNewValue());
        };
        chatRoom.addListener(listener);
        chatRoom.writeMessage("Test Message!");
    }

    /**
     * Remove socket test for when a bot disconnects from a chatroom socket
     */
    @Test
    void removeSocketTest() {
        assertTrue(chatRoom.getBotSockets().isEmpty());
        Socket s = new Socket();
        chatRoom.getBotSockets().add(s);
        assertFalse(chatRoom.getBotSockets().isEmpty());
        assertEquals(1, chatRoom.getBotSockets().size());
        chatRoom.removeSocket(s);
        assertTrue(chatRoom.getBotSockets().isEmpty());
    }
}