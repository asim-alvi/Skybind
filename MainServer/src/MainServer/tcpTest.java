/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MainServer;

/**
 *
 * @author kevin
 */
import java.io.*;
import java.net.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;



public class tcpTest {
	
    public static void main(String[] args) throws IOException {

        Socket serverConn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        
        
       serverConn = new Socket(InetAddress.getByName("127.0.0.1"),51412);
       
           // SocketAddress sockaddr = new InetSocketAddress("127.0.0.1", 55555);
            
            out = new PrintWriter(serverConn.getOutputStream(), true);
          //  out.write("test");
            in = new BufferedReader(new InputStreamReader(serverConn.getInputStream()));
   
       

	while(serverConn.isConnected()){
        Scanner scan = new Scanner(System.in);
        String d= scan.next();
		out.println("SERVER:"+d);
		String i = in.readLine();
		
		System.out.println(i);
	}

	
    }
}
