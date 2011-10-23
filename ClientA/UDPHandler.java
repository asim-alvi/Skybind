package ClientA;

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
		action=data[0];
		remoteIP=data[1];
		remotePort=Integer.parseInt(data[2]);
		remoteLocalIP=data[3];
		remoteLocalPort=Integer.parseInt(data[4]);
	}
	
	public void run(){
		//handle requests received from udp clients here
		System.out.println(request);
		InetAddress rIP=null;
		int RPort = 0;
		try {
			System.out.println(remoteIP);
			System.out.println(clientA.user.getpublicIP());
			if(clientA.user.getpublicIP().equals(InetAddress.getByName(remoteIP))){
			//	System.out.println("tesst");
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
			if(initPunchthrough(clientA.UDPSock,rIP,RPort)==true){
				
				
				
				byte dat[] = new byte[3000];
				System.out.println("waiting...");
				System.out.println(clientA.UDPSock.getLocalPort());
				
				DatagramPacket packet = new DatagramPacket(dat,dat.length);
				try {
					clientA.UDPSock.receive(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				if(new String(packet.getData()).equals("punching")){
					try {
						clientA.UDPSock.send(new DatagramPacket("punching".getBytes(),"punching".getBytes().length,packet.getAddress(),packet.getPort()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 packet = new DatagramPacket(dat,dat.length);
					try {
						clientA.UDPSock.receive(packet);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				*/
				System.out.println(new String(packet.getData()));
				
				DatagramSocket newSocket=null;
				try {
					newSocket = new DatagramSocket(Integer.parseInt(new String(packet.getData()).trim()));
				} catch(NumberFormatException e1){
					try {
						clientA.UDPSock.send(new DatagramPacket("punching".getBytes(),"punching".getBytes().length,InetAddress.getByName(remoteIP),remotePort));
						packet = new DatagramPacket(dat,dat.length);
						try {
							clientA.UDPSock.receive(packet);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						newSocket = new DatagramSocket(Integer.parseInt(new String(packet.getData()).trim()));

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				System.out.println("Client A "+newSocket.getLocalPort());
		
			//	if(initPunchthrough(newSocket,rIP,newSocket.getLocalPort()-1)){
					RPort=newSocket.getLocalPort()-1;
					
					//send Screen
					FrameReceiver FR = new FrameReceiver(newSocket,rIP,RPort);
				//	UpdateFrame UFThread = new UpdateFrame(newSocket,rIP,RPort);
					// UDPSockR.close();
					//waittime(10000);
					FR.start();
				//	UFThread.start();
			//	}
				
			}
	//		else{
				
		//	}
		}
	}
	
	public boolean initPunchthrough(DatagramSocket socket,InetAddress IP,int port){
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
