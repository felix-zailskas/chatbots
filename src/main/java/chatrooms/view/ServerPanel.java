package chatrooms.view;

import chatrooms.server.Server;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Panel that contains Server information about ports received from ChatRooms and ports distributed to Bots
 */
public class ServerPanel extends JPanel implements PropertyChangeListener {

    private final JTextArea textArea;

    /**
     * Creates a new ServerPanel which contains its messages in the TextArea
     *
     * @param server Server
     */
    public ServerPanel(Server server) {
        server.addListener(this);

        int COLUMNS = 50;
        int ROWS = 30;
        textArea = new JTextArea(ROWS, COLUMNS);
        textArea.setFocusable(false);
        textArea.setEditable(false);
        int GAP = 3;
        setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(putInTitledScrollPane(textArea));
    }

    /**
     * Creates a new JPanel of a singular component and sets its title and border using a BorderFactory
     *
     * @param component The component to be put in the JPanel
     * @return a JPanel containing a title and border
     */
    private JPanel putInTitledScrollPane(JComponent component) {
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createTitledBorder("Server Feed"));
        wrapperPanel.add(new JScrollPane(component));
        return wrapperPanel;
    }

    /**
     * PropertyChange method to listen to PropertyChangedEvents from the Server.
     * Appends the message contained in the Event to the TextArea and scrolls to the newest message.
     *
     * @param evt PropertyChangedEvent of the Server
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        StringBuilder text = new StringBuilder();
        text.append(textArea.getText());
        text.append("\n");
        text.append((String) evt.getNewValue());
        textArea.setText(text.toString());
        textArea.setCaretPosition(textArea.getText().length());
    }
}
