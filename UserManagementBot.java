package bot;


import java.io.*;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import tools.*;

/**
 * This bot is to manage the registration of the user with the command '/registration'.
 * The user will be asked to provide a registration code. If the registration code is
 * correct, the user will be registered to the system.
 *
 * The user cannot register again if the user has already registered (One discord ID
 * to one student ID).
 *
 * We assume the registration code will be sent to students via email in advanced.
 * The registration information is given in a file (please check users.csv)
 *
 * The file format of users.csv is as follows:
 * Each row may have three columns or five columns.
 * Three Columns, e.g.:
 *      20100001,g8xa9s,Bruce Lee
 * That represents the student ID is 20100001, the registration code is g8xa9s, and the name is Bruce Lee.
 * Five Columns, e.g.:
 *      20100002,-,Chan Tai Man,1004553330619580487,Kevin Wang
 * That represents the student ID is 20100002, the registration code is empty (registered already),
 * the student name is Chan Tai Man, the discord ID is 1004553330619580487, and the discord name is Kevin Wang.
 */
public class UserManagementBot extends CommandBot {
    //Add your data member here
    private boolean isChecked = false;
    private String reg_code;
    private ArrayList<User> user_list = new ArrayList<User>();


    //Constructor
    public UserManagementBot(String filename) {
        //TODO
        addOption("regcode", "Reg code", true);
        try {
            File input = new File(filename);
            Scanner file_read = new Scanner(input);
            while (file_read.hasNextLine()) {
                String file_data = file_read.nextLine();
                String[] sim = file_data.split(",");
                User v = new User(sim[0], sim[1]);
                v.setStudentName(sim[2]);
                if(v.getRegistrationCode().equals("-")){
                    v.setID(sim[3]);
                    v.setUsername(sim[4]);
                }
                user_list.add(v);
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Written for you. No need to change it
     */
    @Override
    public String getCommand() {
        return "registration";
    }

    /**
     * Written for you. No need to change it
     */
    @Override
    public String getCommandHelp() {
        return "Registers the user";
    }


    /**
     * to check if a user has been registered
     */
    boolean isRegistered(String id) {
        //TODO
        for(int i = 0; i < user_list.size(); i++){
            if(user_list.get(i).getID() != null && user_list.get(i).getID().equals(id)){
                return true;
            }
        }
        return false;
    }


    /**
     * To respond to the command '/registration'.
     *
     * If the user has already registered, return "You are already registered!"
     * If the registration code is correct, register the user and return "You are registered!".
     * If the registration code is incorrect, return "Invalid registration code!"
     *
     * To avoid data lost, remember to save the data to the file after each user's registration.
     */
    @Override
    public String respond(Command command) {
        //TODO
        try {
            if (isRegistered(command.getSenderID())) {
                return "You are already registered!";
            } else {
                for (int i = 0; i < user_list.size(); i++) {
                    if (user_list.get(i).getRegistrationCode().equals(command.getOption())) {
                        user_list.get(i).setRegistrationCode("-");
                        user_list.get(i).setID(command.getId());
                        user_list.get(i).setUsername(command.getName());
                        PrintWriter smth = new PrintWriter("users.csv");
                        for (int j = 0; j < user_list.size(); j++) {
                            smth.print(user_list.get(j).getStudentID() + "," + user_list.get(j).getRegistrationCode() + "," + user_list.get(j).getStudentName() + ",");
                            if (user_list.get(j).getID() != null) {
                                smth.print(user_list.get(j).getID() + "," + user_list.get(j).getUsername());
                            }
                            smth.println();
                        }
                        smth.flush();
                        return "You are registered!";
                    }
                }
            }
            return "Invalid registration code!";
        }
            catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * return the student ID of the user with the given discord ID
     *
     * throws an IDNotFoundException if the discord ID cannot be found
     */
    public String getStudentID(String id) throws IDNotFoundException {
        //TODO
        for(int i = 0; i < user_list.size(); i++){
            if(user_list.get(i).getID() != null && user_list.get(i).getID().equals(id)){
                return user_list.get(i).getStudentID();
            }
        }
        throw new IDNotFoundException("You are not registered yet!");
    }

    /**
     * return the user object with the given discord ID
     *
     * throws an IDNotFoundException if the discord ID cannot be found
     */
    public User getStudent(String id) throws IDNotFoundException {
        //TODO
        for(int i = 0; i < user_list.size(); i++){
            if(user_list.get(i).getID() != null && user_list.get(i).getID().equals(id)){
                return user_list.get(i);
            }
        }
        throw new IDNotFoundException("You are not registered yet!");

    }


    /**
     * Output how many number of users have registered.
     */
    @Override
    public void actionPerform() {
        int num = 0;
        for(int i = 0; i < user_list.size(); i++){
            if(user_list.get(i).getRegistrationCode().equals("-")){
                num +=1;
            }
        }
        System.out.println("Registration bot: \nThere are " + num + "/" + user_list.size() +" users has registered the system");
    }
}
