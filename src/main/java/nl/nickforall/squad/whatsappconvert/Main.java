package nl.nickforall.squad.whatsappconvert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Main {

    public static DbManager database;

    public static void main(String[] args){
        
        //defines the exported logs file from whatsapp
        File file = new File("logs.txt");
        System.out.println("Starting to convert!");
        
        //mysql related variables
        String url = "jdbc:mysql://localhost:3306/squadstats";
        String user = "root";
        String password = "";
        
        //opens an instance of the database
        database = new DbManager(password, user, url);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            
            //while the next line exists
            while ((line = br.readLine()) != null) {
                i++;
                
                //splits the time: name: message format to an array
                String[] msg = line.split(": ");
                if(msg.length > 2){
                    
                    //sets the array to variables so we can push it later
                    long timestamp = convertTimeString(msg[0]);
                    String name = msg[1];
                    String text = msg[2]; //TODO: merge all other strings in the array > 2

                    //debug lines
                    System.out.println(timestamp + " " + name + ": " + text);
                    //push to the database
                    database.addMessage(timestamp, text, name);
                } else if(msg.length == 2 && msg[1].contains("left")){
                    //log a leave thingy
                }
            }
        } catch(Exception ex) {
            if(ex instanceof FileNotFoundException){
                System.out.println("FATAL ERROR: could not find file " + file.getName());
            } else {
                System.out.println("FATAL ERROR: unknown fatal error occured, let's spam you with weird logs! ");
                ex.printStackTrace();
            }

        }

    }
    
    /**
     * Converts a time as in the whatsapp logs to a unix timestamp
     */
     
    public static long convertTimeString(String time){
        try {
            //set the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date parsedDate = dateFormat.parse(time);
            //to seconds
            return (parsedDate.getTime() / 1000L) ;
        } catch(Exception ex){
            ex.printStackTrace();
        }

        return 0;
    }
}
