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
        File file = new File("logs.txt");
        System.out.println("Starting to convert!");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;

            //stop after the first 5 loops because we're debugging
            while ((line = br.readLine()) != null && i < 5) {
                //do something useful this time
                i++;
                String[] msg = line.split(": ");
                if(msg.length > 2){
                    long timestamp = convertTimeString(msg[0]);
                    String name = msg[1];
                    String text = msg[2]; //TODO: merge all other strings in the array > 2

                    //debug lines
                    System.out.println(timestamp + " " + name + ": " + text);
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

    public static long convertTimeString(String time){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date parsedDate = dateFormat.parse(time);
            return (parsedDate.getTime() / 1000L) ;
        } catch(Exception ex){
            ex.printStackTrace();
        }

        return 0;
    }
}
