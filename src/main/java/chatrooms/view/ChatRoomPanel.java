package chatrooms.view;

import chatrooms.client.chatroom.ChatRoom;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Panel that contains the messages from a ChatRoom.
 */
public class ChatRoomPanel extends JPanel implements PropertyChangeListener {

    private final JTextArea textArea;

    private final ChatRoom chatRoom;

    /**
     * Creates new ChatRoomPanel the TextArea contains messages about clitextents joining and leaving
     * and their messages.
     *
     * @param chatRoom ChatRoom
     */
    public ChatRoomPanel(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.addListener(this);

        int COLUMNS = 50;
        int ROWS = 40;
        textArea = new JTextArea(ROWS, COLUMNS);
        textArea.setFocusable(false);
        textArea.setEditable(false);
        int GAP = 3;
        setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(putInTitledScrollPane(textArea, "Chatroom: " + chatRoom.getPort()));
    }

    /**
     * Creates a new JPanel of a singular component and sets its title and border using a BorderFactory
     *
     * @param component The component to be put in the JPanel
     * @param title     The title that will be put on the JPanel
     * @return a JPanel containing a title and border
     */
    private JPanel putInTitledScrollPane(JComponent component, String title) {
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createTitledBorder(title));
        wrapperPanel.add(new JScrollPane(component));
        return wrapperPanel;
    }


    /**
     * PropertyChange method to listen to PropertyChangedEvents from the ChatRoom.
     * Appends the message contained in the Event to the TextArea and scrolls to the newest message.
     *
     * @param evt PropertyChangedEvent of the ChatRoom
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.chatRoom.getMessageFeed().getMessages().add((String) evt.getNewValue());
        StringBuilder text = new StringBuilder();
        for (String message : this.chatRoom.getMessageFeed().getMessages()) {
            text.append(message).append("\n");
        }
        textArea.setText(text.toString());
        textArea.setCaretPosition(textArea.getText().length());
    }
}
