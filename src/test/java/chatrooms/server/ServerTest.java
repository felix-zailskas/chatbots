package chatrooms.server;

import chatrooms.client.chatroom.ChatRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the test class for the server class.
 */
public class ServerTest {

    private Server server;

    /**
     * Initializes a new Server
     */
    @BeforeEach
    private void setUp() {
        server = new Server();
    }

    /**
     * Testing that a new Server is not NULL.
     * The Port ArrayList of a new Server should be not NULL and empty.
     */
    @Test
    public void newServerTest() {
        assertFalse(server.isRunning());
        assertNotNull(server);
        assertNotNull(server.getPorts());
        assertTrue(server.getPorts().isEmpty());
    }

    /**
     * Testing if added ports are put into the ArrayList correctly.
     */
    @Test
    public void addPortTest() {
        int port1 = 8888;
        int port2 = 1234;
        assertTrue(server.getPorts().isEmpty());
        server.addPort(port1);
        assertFalse(server.getPorts().isEmpty());
        assertEquals(port1, server.getPorts().get(0));
        assertEquals(1, server.getPorts().size());
        server.addPort(port2);
        assertFalse(server.getPorts().isEmpty());
        assertEquals(port1, server.getPorts().get(0));
        assertEquals(port2, server.getPorts().get(1));
        assertEquals(2, server.getPorts().size());
    }

    /**
     * Write Message Test
     */
    @Test
    void writeMessageTest() {
        PropertyChangeListener listener = evt -> {
            assertEquals("Test Message!", (String) evt.getNewValue());
            assertNull(evt.getOldValue());
            assertEquals("Writing", evt.getPropertyName());
            assertNotEquals("", (String) evt.getNewValue());
        };
        server.addListener(listener);
        server.writeMessage("Test Message!");
    }

}
