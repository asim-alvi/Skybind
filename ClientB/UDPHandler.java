package ClientB;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;



public class UDPHandler extends Thread {
	private String request;
	private String remoteIP;
	private int remotePort;
	private String remoteLocalIP;
	private int remoteLocalPort;
	private String action;
	private int timeout=30000;
	
	
	public UDPHandler(String request){
		this.request=request;
		String data[] = request.split(":");
		if(data.length!=5){
			return;
		}
		System.out.println(request);
		action=data[0];
		remoteIP=data[1];
		remotePort=Integer.parseInt(data[2]);
		remoteLocalIP=data[3];
		remoteLocalPort=Integer.parseInt(data[4]);
	}
	
	public void run(){
		//handle requests received from udp clients here
		InetAddress rIP=null;
		int RPort = 0;
		try {
			if(clientB.user.getpublicIP().equals(InetAddress.getByName(remoteIP))){
				rIP=InetAddress.getByName(remoteLocalIP);
				RPort=remoteLocalPort;
			}
			else{
				rIP=InetAddress.getByName(remoteIP);
				RPort=remotePort;
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		if(action.equals("ScreenShare")){
			if(initPunchthrough(clientB.UDPSock,rIP,RPort)==true){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				DatagramSocket newSocket=null;
				try {
					newSocket = new DatagramSocket();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(newSocket.getLocalPort());
				System.out.println(rIP+":"+RPort);
				String port = Integer.toString(newSocket.getLocalPort()+1);
				DatagramPacket packet=null;
				
					packet = new DatagramPacket(port.getBytes(),port.getBytes().length,rIP,RPort);
				try {
					for(int x=0;x<1;x++){
						clientB.UDPSock.send(packet);
						try{
						Thread.sleep(100);
						}catch(InterruptedException f){
							f.printStackTrace();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Client B "+newSocket.getLocalPort());

			//	if(initPunchthrough(newSocket,rIP,newSocket.getLocalPort()+1)){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//send Screen
					UpdateFrame UFThread = new UpdateFrame(newSocket,rIP,newSocket.getLocalPort()+1);
					// UDPSockR.close();
					//waittime(10000);
					UFThread.start();
		//		}
				
			}
		//	else{
				
		//	}
		}
	}
	
	public boolean initPunchthrough(DatagramSocket socket, InetAddress IP,int port){
		UDPReceivingThread punch=new UDPReceivingThread(socket);
		punch.start();
		byte[] data = "punching".getBytes();
		long endTimeMillis = System.currentTimeMillis() + timeout;
		DatagramPacket packet=null;
		packet = new DatagramPacket(data,data.length,IP,port);
		while(punch.punched==false&&System.currentTimeMillis()<=endTimeMillis){
			packet.setData(data);
			try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				this.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(punch.punched==true){
			System.out.println("punched");
			return true;
		}
		if(System.currentTimeMillis()>=endTimeMillis){
			System.out.println("failed");
			return false;
		}
		return true;
		
	}
}
