package ClientB;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class UpdateFrame extends Thread {
	public static byte[] imagedata=null;
	public volatile static Thread EL;
	private DatagramSocket socket;
	private InetAddress IP;
	private int port;
	
	

	public UpdateFrame(DatagramSocket socket,InetAddress IP,int port){

		this.socket=socket;
		this.IP=IP;
		this.port=port;
		EL= new EventListener(socket,IP,port);
		EL.start();
	}
	


@SuppressWarnings("null")
public void run(){
	System.out.println("Knock 3");
	 FBUpdate FBU=null;
		try {
			FBU= new FBUpdate(false,0,0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			System.exit(0); 
			e.printStackTrace();
		}
		System.out.println("Knock 4");
		System.out.println("Knock 5");
		byte[] buf=FBUpdate.frame; 
		System.out.println("The size of the frame is "+FBUpdate.frame.length);
		DatagramPacket packet= new DatagramPacket(buf,buf.length, IP, port); //irrelevent too big
		int c=0;        
		byte[] buffer1=new byte[(buf.length/4)];
		for(int x=0;x<((buf.length)/4);x++){
			
			buffer1[c]=buf[x];
			c++;
		}
	//	buffer1[0]=1;
//		buffer1[buffer1.length-1]=1;
		c=0;
		byte[] buffer2=new byte[(2*((buf.length)/4)-(((buf.length)/4)))];
		for(int x=((buf.length)/4);x<2*((buf.length)/4);x++){
			
			buffer2[c]=buf[x];
			c++;
		}
	//	buffer2[buffer2.length-1]=2;
		c=0;
		byte[] buffer3=new byte[((3*((buf.length)/4))-((2*((buf.length)/4))))];
		for(int x=(2*((buf.length)/4));x<3*((buf.length)/4);x++){
			
			buffer3[c]=buf[x];
			c++;
		}
		//buffer3[buffer3.length-1]=3;
		c=0;
		byte[] buffer4=new byte[((buf.length)-((3*((buf.length)/4))))];
		for(int x=(3*((buf.length)/4));x<(buf.length);x++){
			
			buffer4[c]=buf[x];
			c++; 
		}
		
	//	buffer4[buffer4.length-1]=4;
		System.out.println(" "+((buf.length)/4)+" "+(((buf.length)/4)+1)+" "+" "+2*((buf.length)/4)+" "+((2*((buf.length)/4))+1)+" ");
		System.out.println(" "+3*((buf.length)/4)+" "+((3*((buf.length)/4))+1)+" "+((buf.length)-1));
		//String l= Integer.toString(buf.length);
		String l="hello"; 
		System.out.println("The size is"+buffer1.length+buffer2.length+buffer3.length+buffer4.length);

		byte[] imgsize=l.getBytes();
		
		/*imagedata= new byte[buffer1.length+buffer2.length+buffer3.length+buffer4.length];
        int ct=0;
        for(int x=0;x<buffer1.length;x++){
            imagedata[ct]=buffer1[x];
            ct++;
        }
        for(int x=0;x<buffer2.length;x++){
            imagedata[ct]=buffer2[x];
            ct++;
        }
        for(int x=0;x<buffer3.length;x++){
            imagedata[ct]=buffer3[x];
            ct++;
        }
        for(int x=0;x<buffer4.length;x++){
            imagedata[ct]=buffer4[x];
            ct++;
        }
        System.out.println(imagedata.length); 
        BufferedWriter t=null; 
        try {
			t= new BufferedWriter(new FileWriter("T.txt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        for(int x=0;x<imagedata.length;x++){
        	try {
				t.write(Byte.valueOf(imagedata[x]).toString());        	
				t.newLine(); 

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        	
        }
        try {
			t.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		Viewer.getNewFrame(); */
		
		DatagramPacket packet1= new DatagramPacket(buffer1,buffer1.length,IP,port); 
		DatagramPacket packet2= new DatagramPacket(buffer2,buffer2.length,IP, port); 
		DatagramPacket packet3= new DatagramPacket(buffer3,buffer3.length,IP, port); 
		DatagramPacket packet4= new DatagramPacket(buffer4,buffer4.length,IP, port); 



		System.out.println(IP+" is the IP "+port+"is the port");
		

		
		//----------------------------------------------------------------------------
		
		//clientB.waittime(500);
		try {
			socket.send(packet1);
			socket.send(packet2);
			socket.send(packet3);
			socket.send(packet4);
			
			System.out.println("Sent Image Packets"); 
			BufferedWriter o =null;
			o=new BufferedWriter(new FileWriter("B.txt"));
			for(int x=0;x<FBUpdate.frame.length;x++){
			o.write(Byte.valueOf(FBUpdate.frame[x]).toString());
			}
			o.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Packet with Image not sent"); 
		}
		
		 
	
}


}

