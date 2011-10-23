package ClientA;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import ClientB.clientB;


public class ActiveUpdate extends Thread {
	private static boolean interrupt=false;
	public  static boolean sent1;
	public static boolean sent2;
	public volatile static Thread FR; 
	private DatagramSocket socket;
	private InetAddress IP;
	private int port;
	
	
	public ActiveUpdate(DatagramSocket socket, InetAddress IP, int port){
		//this.start();
		this.socket=socket;
		this.IP=IP;
		this.port=port;
		
	}
	public void run(){
		/*try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		String dat= Integer.toString(5000)+":"+Integer.toString(5000)+":"+Integer.toString(5000);
	//	byte[] x=intToByteArray(5000);  
	//	byte[] y=intToByteArray(5000);
	//	byte[] z=intToByteArray(5000);
		String click="click";
		DatagramPacket packetX= new DatagramPacket(dat.getBytes(),dat.getBytes().length, IP, port);
	//	DatagramPacket packetY= new DatagramPacket(y,y.length, IP, port);
		//DatagramPacket packetZ= new DatagramPacket(z,z.length, IP, port);
		//clientA.waittime(500);
		try {
			socket.send(packetX);
			sent1=true;

		//	socket.send(packetY);
		//	sent2=true; 

		//	socket.send(packetZ);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		sent1=false;
		sent2=false;
		if(isInterrupted()==true){
			System.out.println("AU is interrupted");
		FR= new FrameReceiver(socket,IP,port);
		FR.start(); 		
		FR.interrupt();

		}
		else {
		
			FR= new FrameReceiver(socket,IP,port);

			FR.run();
			
		}
		System.out.println("INTERUPTION STATUS: "+isInterrupted());
		
		
		
	}
	
	public static final byte[] intToByteArray(int value) {
		return new byte[]{
		(byte)(value >>> 24), (byte)(value >> 16 & 0xff), (byte)(value >> 8 & 0xff), (byte)(value & 0xff) };
		}

		
	
}
