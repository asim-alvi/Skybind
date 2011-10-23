package ClientB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;

public class TCPReceivingThread extends Thread {
	private BufferedReader in;
	private PrintWriter out;
	private SSLSocket socket;
	
	public TCPReceivingThread(SSLSocket socket,BufferedReader in,PrintWriter out){
		this.in=in;
		this.out=out;
		this.socket=socket;
	}
	
	public void run(){
		String request=null;
		while(socket.isConnected()){
	
			try {
				request = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			new clientHandleRequests(socket,request).start();
			
		}
		
	}
}
