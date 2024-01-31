package bot;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import tools.*;

/**
 * This bot handle the command '/score'.
 * When a user ask about his score, this bot will check the score of the user and return the score.
 * To know which student ID that the user has been registered, this bot will ask the UserManagementBot.
 * Prior to using this bot, you need to register this bot to the UserManagementBot using the command '/registration'.
 *
 */
public class ScoreBot extends CommandBot {
    public static final String DEFAULT_FILE = "dictation.csv";
    //TODO: Add your private data member here
    private UserManagementBot userManagementBot;
    private String filename;
    private String nameofUser;




    /**
     * The constructor of the bot, require a UserManagementBot object.
     * Since the filename of the score is not given, the default file name is used.
     */
    public ScoreBot(UserManagementBot r) {
        //TODO
        this.userManagementBot = r;
        this.filename = DEFAULT_FILE;

    }
    /**
     * The constructor of the bot, require a UserManagementBot object and the filename of the score.
     */
    public ScoreBot(UserManagementBot r, String filename) {
        //TODO
        this.userManagementBot = r;
        this.filename = filename;
    }
    /**
     * Which score that the object is listening to.
     */
    @Override
    public String getCommand() {
        //TODO
        return "score";
    }
    /**
     * The short description for this command.
     */
    @Override
    public String getCommandHelp() {
        //TODO
        return "Displays the user's scores";
    }
    /**
     * This method is used to respond to a command.
     */
    @Override
    public String respond(Command command) {
        //TODO
        try {
            String bn = "";
            File input = new File(DEFAULT_FILE);
            Scanner sc1 = new Scanner(input);
            nameofUser = command.getName();
            while (sc1.hasNextLine()) {
                String token = sc1.nextLine();
                String[] m = token.split(",");
                if (userManagementBot.getStudentID(command.getSenderID()).equals(m[0])) {
                    double sum = 0;
                    for (int i = 1; i < m.length; i++) {
                        bn += m[i] + ", ";
                        if (!m[i].equalsIgnoreCase("-")) {
                            sum += Double.valueOf(m[i]);
                        }
                    }
                    double avr_sum = sum / (m.length - 1);
                    return "Your scores are: " + bn + " and your average score is: " + avr_sum;

                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IDNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "You have not registered yet";
    }
    /**
     * Print the last user who queried the score bot service to console.
     */
    @Override
    public void actionPerform() {
        //TODO
        System.out.println(nameofUser);


    }
}
