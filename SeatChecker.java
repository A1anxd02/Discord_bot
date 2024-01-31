package bot;

import tools.*;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

/**
 * This bot will check the seat of a quiz or practical test for a student.
 *
 * This message bot will only work in private message.
 * The user should first type "seat" to start the conversation.
 * The bot will then ask for the student ID. The bot is expecting
 * a 8-digit number as the student ID and ignore any other message.
 * After received the 8-digit number in a private message, the bot
 * will check the seat of the student and return the seat number.
 *
 * The bot allows the user to check seat for other students or check
 * the seat even if the user did not register to UserManagementBot before.
 *
 * We will assume the seat will never change during the execution of the
 * program. Any change of seat will require the program to restart.
 */
public class SeatChecker implements MessageListener {
    //TODO: Add your private data member here
    private boolean digit;
    private ArrayList<User> seat = new ArrayList<User>();
    private boolean isExpectingID = false;


    //TODO: Add your methods here
    public String onMessageReceived(Message message) {
        if (isExpectingID) {
            String content = message.getContent();
            if (content.matches("\\d{8}") && message.isPrivate()) {
                try {
                    File input = new File("seat.csv");
                    Scanner sc = new Scanner(input);
                    while (sc.hasNextLine()) {
                        String v = sc.nextLine();
                        String[] b = v.split(",", 2);
                        if (content.equals(b[0])) {
                            isExpectingID = false; // Reset the state
                            return "Your seat is: " + b[1];
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                isExpectingID = false; // Reset the state
                return "Sorry I cannot find your seat";
            } else {
                isExpectingID = true; // Reset the state
                return null;
            }
        } else {
            if (message.getContent().equals("seat")) {
                if(message.isPrivate()){
                    isExpectingID = true; // Set the state to expect ID
                    return "What is your ID?";
                } else {
                    return "Sorry, I only work in private messages.";
                }
            }
            return  null;
        }
    }
}
