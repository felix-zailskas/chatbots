package chatrooms.client.chatroom;

/**
 * Main class for ChatRoom
 */
public class ChatRoomMain {

    /**
     * Create new ChatRoom and chatroom thread
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.start();
    }
}
