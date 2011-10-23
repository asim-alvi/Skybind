/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainServer;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class UserLogin {

    private String request = null;
    private String Username;
    private String publicIPAddress=null;
    private String password;
    public String cookie;
    private int port = 0;
    private int i = 0;
    private int e = 0;
    public boolean loginFailed = false;
    Socket client;
    Statement stmt;
    ResultSet rs;

    public UserLogin(String r, Socket s) {
        request = r;
        client = s;
      
        getInfo();

        if (testInfo() == false) {
            loginFailed = true;

            return;
        }
      //  login();

    }

    public UserLogin(String r) {
        request = r;

        //  client=s;
        getInfo();
        if (testInfo() == false) {
            loginFailed = true;
            return;
        }
        login();

    }

    public void getInfo() {
        publicIPAddress = client.getInetAddress().getHostAddress().toString();
        String data[] = request.split(":");
        if(data.length!=3){
            Username="";
            password="";
        	return;
        }
        else{
        	Username=data[1];
        	password=data[2];
        }
        

    }

    public boolean testInfo() {
        try {



            Class.forName("com.mysql.jdbc.Driver");

            String url =
                    "jdbc:mysql://localhost:3306/FileFriends";

            Connection con =
                    (Connection) DriverManager.getConnection(
                    url, "root", "skybindroot");
            stmt = (Statement) con.createStatement();

            rs = stmt.executeQuery("SELECT Password "
                    + "from Users WHERE UserName='" + Username + "'");
            // if (Username.length() < 6 || Username.length() > 13) {
            //   return false;
            // }

            while (rs.next()) {
                String s = rs.getString("Password");



                if (password.equals(s)) {
                	String random = Double.toString(Math.random());
                	MD5Encryption md5 = new MD5Encryption(Username+password+random);
                	cookie=md5.getMD5();
                	try {
                        stmt.executeUpdate("UPDATE Users SET Cookie='"+cookie+"' where UserName=" + "'" + Username + "'");
                    } catch (SQLException ex) {
                        Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return true;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;//end catch

    }

    public void login() {
        StringBuffer sb = new StringBuffer("UPDATE Users SET IPAddress='',Port=,Online=1 where UserName=" + "'" + Username + "'");
     //   sb.insert(28, IPAddress);
      //  System.out.println(IPAddress);

        sb.insert(sb.indexOf("=", 28) + 1, Integer.toString(port));
        
    }
}
