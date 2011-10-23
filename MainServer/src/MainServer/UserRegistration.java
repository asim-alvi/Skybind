/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainServer;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class UserRegistration {

    private String request;
    private String Username;
    private String name;
    private String password;
    private int i = 0;
    private int e = 0;
    Connection con;
    public boolean invalidUN = false;
    Statement stmt;
            ResultSet rs;
    public UserRegistration(String s) {
        try {
            request = s;
            getInfo();
            checkUsername();
            if (invalidUN == true) {
                return;
            }
            addUsername();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getInfo() {
        String[] input = request.split(":");
        Username=input[1];
        password=input[2];
        name=input[3];
    }

    public void checkUsername() {
        if (mySQLDatabase() == false) {
            invalidUN = true;
            return;
        }

    }

    public boolean mySQLDatabase() {
        try {
            


            Class.forName("com.mysql.jdbc.Driver");

            String url =
                    "jdbc:mysql://localhost:3306/FileFriends";

            con =
                    (Connection) DriverManager.getConnection(
                    url, "root", "skybindroot");
            stmt = (Statement) con.createStatement();

            rs = stmt.executeQuery("SELECT UserName "
                    + "from Users WHERE UserName='"+Username+"'");
           // if (Username.length() < 6 || Username.length() > 13) {
             //   return false;
           // }
            
            while (rs.next()) {
                String s = rs.getString("UserName");
                
                  
               
                if (Username.equals(s)) {
                   return false;
                }

            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;//end catch
    }
    public void addUsername(){
        try {
            stmt.executeUpdate("INSERT INTO Users(UserName,Password,Name,IPAddress,Port,Online)" +
                    " VALUES("+"'"+Username+"'"+","+"'"+password+"'"+","+"'"+name+"'"+",'0.0.0.0',5555,0)");
        } catch (SQLException ex) {
            Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //end main
}
