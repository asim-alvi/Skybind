package MainServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class UDPServerSend extends Thread {
	private DatagramSocket socket;
	public static String STATE="STOP";
	private DatagramPacket packetR;
	public UDPServerSend(DatagramSocket socket,DatagramPacket receive){
		this.packetR=receive;
		this.socket=socket;

	}
	
	
	public void run(){
		String data[] = new String(packetR.getData()).trim().split(":");
	    Statement stmt = null;
	    ResultSet rs;
		if(data.length!=3){
			
			//return;
		}
		String cookie=data[0];
		String privateIP=data[1];
		String privatePort=data[2];
		/*
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

        String url =
                "jdbc:mysql://localhost:3306/FileFriends";

        Connection con = null;
		try {
			con = (Connection) DriverManager.getConnection(
			url, "root", "skybindroot");
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        try {
			stmt = (Statement) con.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

        try {
			stmt.execute("UPDATE Users SET privateIP='"+privateIP+"',privatePort="+privatePort+",Online=1 where Cookie=" + "'" + cookie + "'");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		System.out.println(new String(packetR.getData()).trim());
		byte[] bytesS = (packetR.getAddress().getHostAddress()+":"+packetR.getPort()).getBytes();
		DatagramPacket packetS= new DatagramPacket(bytesS,bytesS.length,packetR.getAddress(),packetR.getPort());
		try {
			socket.send(packetS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
