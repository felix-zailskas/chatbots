package chatrooms.client.chatroom;

import chatrooms.message.Messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Creates a thread to listen for bot messages over the connected socket
 */
public class ThreadedBotListener implements Runnable {

    private final Socket socket;
    private final ChatRoom chatRoom;

    /**
     * Set the bot socket and the chatroom for this thread
     *
     * @param socket   Socket
     * @param chatRoom ChatRoom
     */
    public ThreadedBotListener(Socket socket, ChatRoom chatRoom) {
        this.socket = socket;
        this.chatRoom = chatRoom;
    }

    /**
     * Thread created to listen on each bot's socket in order to add their messages to that chatroom message feed,
     * indicate if they are leaving and when they join a chatroom.
     */
    @Override
    public void run() {
        String botName = "Bot";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            botName = in.readLine();
            botName = botName.substring(botName.indexOf(":") + 2);
            chatRoom.writeMessage("\t \t<" + botName + "> has joined the chatroom!");
            String currMessage;
            while (true) {
                currMessage = in.readLine();
                if (currMessage.equals(botName + " : " + Messages.KILLED_MESSAGE)) throw new IOException();
                if (!currMessage.equals(botName + " : " + botName)) {
                    if (!currMessage.equals(botName + " : " + Messages.LEAVING_MESSAGE)) {
                        chatRoom.sendMessage(currMessage);
                        chatRoom.distributeMessage(currMessage);
                    } else {
                        chatRoom.writeMessage(currMessage);
                        break;
                    }
                }
            }
            chatRoom.writeMessage("\t \t<" + botName + "> has left the chatroom!");
            chatRoom.removeSocket(socket);
        } catch (IOException e) {
            System.out.println("This bot has been killed, IO exception caught!");
            chatRoom.writeMessage("\t \t<" + botName + "> has been killed!");
        }
    }


}
