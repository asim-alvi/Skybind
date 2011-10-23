package MainServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServerReceive extends Thread {
	
	private DatagramSocket socket;
	public static String STATE="STOP";
	public UDPServerReceive(){
		try {
			socket =  new DatagramSocket(64000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void run(){
		STATE="RUNNING";
		while(STATE.equals("RUNNING")){
			byte[] bytesR = new byte[100];
			DatagramPacket packetR= new DatagramPacket(bytesR,bytesR.length);
			try {
				socket.receive(packetR);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new UDPServerSend(socket,packetR).start();
			
		}
	}
	public static void main(String args[]){
		UDPServerReceive udpServer = new UDPServerReceive();
		udpServer.start();
	}
}
