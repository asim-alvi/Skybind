package ClientB;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class EventListener extends Thread{
	
	private DatagramSocket socket;
	private InetAddress IP;
	private int port;
	public boolean Activate=false;
	
	
	public EventListener(DatagramSocket socket,InetAddress IP,int port){
		this.socket=socket;
		this.IP=IP;
		this.port=port;
	}
	
	public void run(){
		byte[] buf1= new byte[14];
		DatagramPacket packetx= new DatagramPacket(buf1,buf1.length);
		byte[] buf2= new byte[5];
		DatagramPacket packety= new DatagramPacket(buf2,buf2.length);
		byte[] buf3= new byte[255];
		DatagramPacket packettype= new DatagramPacket(buf3,buf3.length);


		try {
			socket.setSoTimeout(0);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			socket.receive(packetx);
		//	socket.receive(packety);
		//	socket.receive(packettype);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String data = new String(packetx.getData());
		String dat[] = data.split(":");
		System.out.println(data);
		int x=0;
		int y=0;
		int type=0;
		try{
		x = Integer.parseInt(dat[0]);
	    y = Integer.parseInt(dat[1]);
		type = Integer.parseInt(dat[2]);
		}catch(Exception e){
			x=-1;
			y=-1;
			type=-1;
		}
		//String Event= new String(packet.getData(),0, packet.getData().length); 
	//	byte[] X=packetx.getData();
		//byte[] Y=packety.getData();
		//byte[] Type=packettype.getData();
		//int x= byteArrayToInt(X);
		//int y= byteArrayToInt(Y);
		//int type=byteArrayToInt(Type);
		System.out.println(x+" "+y+" "+type); 
		EventRunner ER= new EventRunner();
		
		//****************************************************************
		if(y==3000){
			try {
				ER.keyAgent(x);
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 Activate=false;
			
		}
		//****************************************************************
		else if(type==2){
			try {
				ER.doubleClickLocation(x, y);
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Activate=true;
			
		}
		
		else if(type==8){
			try {
				ER.moveLocation(x, y);
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Activate=true;
			
		}
		
		else if(type==9){
			try {
				ER.dragLocation(x, y);
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Activate=true;
			
		}
		//****************************************************************
		else if((x==5000) && (y==5000) && (type==5000)){
			Activate=true;
			
			
		}
		
		
		else{
		
		
		try {
			ER.clickLocation(x, y);
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	Activate=true;
		}
		waittime(200); 
		if(Activate==true){
		UpdateFrame UFThread= new UpdateFrame(socket,IP,port);
		//if(Event.equals("click")){
			UFThread.run(); 
		}
		
		else{
			EventListener EL= new EventListener(socket,IP,port);
			EL.start();

			
		}
			
		//}
		
	}
	public static final int byteArrayToInt(byte [] b) {
        return (b[0] << 24)
                + ((b[1] & 0xFF) << 16)
                + ((b[2] & 0xFF) << 8)
                + (b[3] & 0xFF);
}
	
	 public static void waittime (int n){
         long t0,t1;
         t0=System.currentTimeMillis();
     do{
             t1=System.currentTimeMillis();
         }
         while (t1-t0<n);
 }


}
