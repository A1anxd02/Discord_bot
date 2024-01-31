package bot;

import tools.*;
import tools.Command;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Music_helper extends CommandBot {
    private String filename;

    public Music_helper(String filename){
        addOption("musicgenre", "Music genre", true);
        this.filename = filename;
    }


    @Override
    public void actionPerform() {
        System.out.println("Music Genre working! ");
    }

    @Override
    protected String respond(Command command) {
        try{
            String output = "Song recommendations: ";
            File input = new File(filename);
            Scanner sc1 = new Scanner(input);
            while (sc1.hasNextLine()){
                String[] sim = sc1.nextLine().split(",");
                if(sim[0].equalsIgnoreCase(command.getOption())){
                    for(int i = 1; i < sim.length; i++){
                        output += sim[i] + ", ";
                    }
                }
            }
            output += "I hope you like it! ";
            return output;

        }catch (FileNotFoundException e){
            return "File not found";
        }
    }

    @Override
    protected String getCommand() {
        return "musicgenre";
    }

    @Override
    protected String getCommandHelp() {
        return "Helps to choose the music";
    }

}
