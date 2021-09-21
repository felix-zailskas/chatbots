package chatrooms.message;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Static class of base messages for the bots
 */
public class Messages {


    /**
     * Creates an arraylist of base messages
     */
    public static final ArrayList<String> BASE_MESSAGES = new ArrayList<>(Arrays.asList(
            "Hi!", "How are you?", "Do you have a lighter?", "Can anyone point me to the washroom?", "Are you really " +
                    "not a bot?", "Has anyone ever told you that you type really slow?", "I promise I am not a bot",
            "I could really fancy a beer", "I just like typing messages", "I might leave the server, MIGHT.",
            "A lot of bots just type the same thing as me.", "How many times will I be instantiated?",
            "Are you kidding me!", "Do I ask a question?", "Random random random", "Compound messages make " +
                    "no sense."));

    // LEAVING MESSAGE
    public static final String LEAVING_MESSAGE = "I am leaving!";

    // KILLED MESSAGE
    public static final String KILLED_MESSAGE = "I am dying!";

}
