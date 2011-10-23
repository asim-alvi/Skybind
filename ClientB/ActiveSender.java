package ClientB;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class ActiveSender extends Thread{

	private DatagramSocket socket;
	private InetAddress IP;
	private int port;
	
	public ActiveSender(DatagramSocket socket,InetAddress IP, int port){
		this.socket=socket;
		this.IP=IP;
		this.port=port;
	}
	
	public void run(){
		while(true){
			UpdateFrame UFThread= new UpdateFrame(socket,IP,port);
			UFThread.run();
			try {
				this.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
