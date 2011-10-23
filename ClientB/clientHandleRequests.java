package ClientB;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.net.ssl.SSLSocket;

public class clientHandleRequests extends Thread{
	
	private String request;
	
	public clientHandleRequests(SSLSocket socket,String request){
		this.request=request;
	}
	
	
	public void run(){
		//handle requests received from server here
		if(request.startsWith("ScreenShare:")){
			new UDPHandler(request).start();
		}
		
	}
	
}
