package MainServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class GetUserInfo {

	private ArrayList<Friend> users;

	public GetUserInfo(String request, SSLSocket socket) throws SQLException,
			IOException {
		String[] data = request.split(":");
		if (data.length != 3) {
			return;
		}
		String username = data[2];
		users = new ArrayList<Friend>();
		String query = "select * from Users,Friends where Users.UserName =  "
				+ "Friends.Friends and Friends.UserName = '" + username + "';";
		// and Users.Cookie = '"+data[1]+"';";
		String url = "jdbc:mysql://localhost:3306/FileFriends";
		Connection con = null;
		try {
			con = (Connection) DriverManager.getConnection(url, "root",
					"skybindroot");
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		System.out.println(query);

		Statement stmt = (Statement) con.createStatement();

		ResultSet rs = stmt.executeQuery(query);

		while (rs.next()) {
			String name = rs.getString("Name");
			String UserName = rs.getString("UserName");
			String privateIP = rs.getString("privateIP");
			String publicIP = rs.getString("IPAddress");
			int privatePort = Integer.parseInt(rs.getString("privatePort"));
			int publicPort = Integer.parseInt(rs.getString("Port"));
			boolean Online = Boolean.parseBoolean(rs.getString("Online"));
			users.add(new Friend(name, UserName, privateIP,
					publicIP, privatePort, publicPort, Online));
		}
		OutputStream os = socket.getOutputStream();

		ObjectOutputStream oos = new ObjectOutputStream(os);
		System.out.println("Sending object to " + socket.getPort());
		oos.writeObject(users);
		oos.flush();
		oos.close();
		os.close();
		socket.close();
	}

}
