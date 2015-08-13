package nl.nickforall.squad.whatsappconvert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbManager {

    private Connection con;

    public DbManager(String pass, String user, String jdbc){
        try {
            this.con = DriverManager.getConnection(jdbc, user, pass);

        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public void addMessage(long time, String msg, String author){
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO messages(sender, msg, time) VALUES(?, ?, ?)");
            pst.setString(1, author);
            pst.setString(2, msg);
            pst.setInt(3, (int)time);
            pst.executeUpdate();

        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public void close(){
        try {
            if (con != null) {
                con.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}
