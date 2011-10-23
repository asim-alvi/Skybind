/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * 
 * @author kevin
 */
public class HandleRequests implements Runnable {

	InputStream input;
	OutputStream output;
	Socket clientSocket = null;
	String request = null;
	String privateKey = null;
	BufferedReader in;
	boolean end;
	PrintWriter out;
	private int ID;
	private ArrayBlockingQueue<String> queue;
	private static boolean conn;
	
	HandleRequests(Socket c,int ID) {
		clientSocket = c;
		queue =  new ArrayBlockingQueue<String>(100);
		this.ID=ID;
	}
	HandleRequests(SSLSocket c,int ID) {
		clientSocket = c;
		queue =  new ArrayBlockingQueue<String>(100);
		this.ID=ID;
	}
	
	public void run() {
		try {
			
			// SSLSocketFactory sslsocketfactory = (SSLSocketFactory)
			// SSLSocketFactory.getDefault();
			// clientSocket= (SSLSocket)
			// sslsocketfactory.createSocket("localhost", 9999);

			input = clientSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(input));
			output = clientSocket.getOutputStream();
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			getRequest();

			output.close();
			input.close();
			clientSocket.close();
		} catch (IOException e) {
			// report exception somewhere.
			e.printStackTrace();
		}

	}
	public int getID(){
		return ID;
	}
	public void forwardRequest(String request){
		queue.add(request);
	}
	public void getRequest() {

		// handleEncryption();
		while (clientSocket.isConnected()) {
			
			readRequest();
			if (end != true) {
				handleRequests();
			} else {
				break;
			}
			if(queue.size()>0){
				request=queue.poll();
				handleRequests();
				
			}
		}
	}

	public void readRequest() {
		
		try {
			request = in.readLine();

		} catch (IOException ex) {
			try {
				output.close();
				input.close();
				end=true;
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Logger.getLogger(HandleRequests.class.getName()).log(Level.SEVERE,
			// null, ex);
			return;
		}
		// System.out.println("r "+request);
		if (request == null) {
			end = true;
			return;
		}
	}

	public void handleRequests() {

		String REGISTER = "REGISTER:";
		String Login = "LOGIN:";

		if (request == null || request.length() > 150) {
			return;
		}

		if (request.contains(";") || request.contains("'")) {
			out.println("Failure");
		}
		if (request.startsWith(REGISTER)) {
			handleRegister();
			return;
		}
		if (request.startsWith(Login)) {
			handleLogin();
			return;
		}
		if (request.startsWith("GETUSERINFO:")) {
			handleGetUserInfo();
			return;
		}
		if(clientSocket.getInetAddress().getHostAddress().equals("127.0.0.1")&&request.startsWith("SERVER:")){
			//System.out.println(MainServer.queue.)
			//do SERVER stuff
			
			System.out.println((((HandleRequests) MainServer.threads.get(0)).getID()));
			((HandleRequests) MainServer.threads.get(0)).out.println("ScreenShare:192.168.1.42:50001:192.168.1.42:50001");
			((HandleRequests) MainServer.threads.get(1)).out.println("ScreenShare:192.168.1.33:50000:192.168.1.33:50000");
			out.println("server");
		}
	}

	public void handleRegister() {
		UserRegistration ur = new UserRegistration(request);
		if (ur.invalidUN == true) {

			out.println("REGISTER:Failed");

		} else {

			out.println("REGISTER:Complete");

		}

	}

	public void handleLogin() {
		UserLogin UL = new UserLogin(request, clientSocket);

		if (UL.loginFailed == true) {

			out.println("LOGIN:FAILED");

		} else {

			out.println("LOGIN:SUCCESS:" + UL.cookie);
		}
	}

	public void handleGetUserInfo() {
		try {
			new GetUserInfo(request, (SSLSocket)clientSocket);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
