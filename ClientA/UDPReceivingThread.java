package ClientA;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class UDPReceivingThread extends Thread {
	
	public boolean interrupt=false;
	DatagramSocket socket;
	public boolean punched;
	
	public UDPReceivingThread(DatagramSocket socket){
		this.socket=socket;
	}
	public void run(){
		while(!interrupt){
			byte[] buf = new byte[512];
			DatagramPacket packet =  new DatagramPacket(buf,buf.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String data =  new String(packet.getData()).trim();
			if(data.equals("punching")){
				punched=true;
		
				interrupt=true;
			}
			//new UDPHandler(data).start();
			
		}
	}
	
}
