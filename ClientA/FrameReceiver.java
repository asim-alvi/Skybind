package ClientA;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;


public class FrameReceiver extends Thread {
	public static BufferedImage frame=null;
	public static byte[] imagedata=null;
	private static boolean called=false;
	public volatile static Thread AU; 
	private DatagramSocket socket;
	private InetAddress IP;
	private int port;
	
	public FrameReceiver(DatagramSocket socket,InetAddress IP,int port){
		this.socket=socket;
		this.IP=IP;
		this.port=port;
	}
	public void run(){
		/*DatagramSocket socket=null;
		try {
			socket= new DatagramSocket(62000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Packet Not Received");
		}*/
		byte buf1[]=new byte[60000];
		byte buf2[]=new byte[60000];
		byte buf3[]=new byte[60000];
		byte buf4[]=new byte[60000];
		byte check[]= new byte[5]; 
		
		
		DatagramPacket packet1= new DatagramPacket(buf1,buf1.length);
		DatagramPacket packet2= new DatagramPacket(buf2,buf2.length);
		DatagramPacket packet3= new DatagramPacket(buf3,buf3.length);
		DatagramPacket packet4= new DatagramPacket(buf4,buf4.length);
		System.out.println(socket.isBound());
		if(called==false){
		try {
			socket.setSoTimeout(20000);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		called=true;
	}
	else{
		if(called==true){
			try {
				socket.setSoTimeout(1000);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		byte[] byteset1=null;
		byte[] byteset2=null;
		byte[] byteset3=null;
		byte[] byteset4=null;
		ArrayList<byte[]> bytes = new ArrayList<byte[]>();
		try {
			System.out.println("Getting Image");
			socket.receive(packet1);
	//		clientA.waittime(5);
			System.out.println("got packet 1");
			byteset1=packet1.getData();
			bytes.add(byteset1);
			socket.receive(packet2);
		//	clientA.waittime(5);

			System.out.println("got packet 2");

			byteset2=packet2.getData();
			bytes.add(byteset2);
			socket.receive(packet3);
			//clientA.waittime(5);

			System.out.println("got packet 3");

			byteset3=packet3.getData();
			bytes.add(byteset3);
			socket.receive(packet4);
			//clientA.waittime(5);

			System.out.println("got packet 4");
			
			byteset4=packet4.getData();
			bytes.add(byteset4);
			
			System.out.println("Got Image"); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AU= new ActiveUpdate(socket,IP,port);
			AU.start();
			this.interrupt(); 
			System.out.println("Did not get Image"); 
		}
	//	for(byte[] b:)
		String one=new String(packet1.getData()).trim();
		String t=new String(packet2.getData()).trim();
		String th=new String(packet3.getData()).trim();
		String f=new String(packet4.getData()).trim();
		if(one.equals("punching")){
			System.out.println(one);
		}
		if(t.equals("punching")){
			System.out.println(t);
		}
		if(th.equals("punching")){
			System.out.println(th);
		}
		if(f.equals("punching")){
			System.out.println(f);
		}


		
		
		
		
		int b1=0;
		for(int x=60000-1;x>0;x--){
			if(byteset1[x]=='\u0000'){
				b1++;
			}
			else{
				break;
			}
		}
		int b2=0;
		for(int x=60000-1;x>0;x--){
			if(byteset2[x]=='\u0000'){
				b2++;
			}
			else{
				break;
			}
		}
		int b3=0;
		for(int x=60000-1;x>0;x--){
			if(byteset3[x]=='\u0000'){
				b3++;
			}
			else{
				break;
			}
		}
		int b4=0;
		for(int x=60000-1;x>0;x--){
			if(byteset4[x]=='\u0000'){
				b4++;
			}
			else{
				break;
			}
		}
		
		 

		
		b1=60000-b1;
		b2=60000-b2;
		b3=60000-b3;
		b4=60000-b4;
		
		byte[] bytes1temp= new byte[b1]; 
		byte[] bytes2temp= new byte[b2]; 
		byte[] bytes3temp= new byte[b3]; 
		byte[] bytes4temp= new byte[b4];
		
		
		System.out.println(b1+b2+b3+b4); 
		byte[] bytes1= new byte[b1]; 
		for(int x=0;x<b1;x++){
			bytes1[x]=byteset1[x];
			bytes1temp[x]=byteset1[x];
		}
		byte[] bytes2= new byte[b2]; 
		for(int x=0;x<b2;x++){
			bytes2[x]=byteset2[x];
			bytes2temp[x]=byteset2[x];
		}
		byte[] bytes3= new byte[b3]; 
		for(int x=0;x<b3;x++){
			bytes3[x]=byteset3[x];
			bytes3temp[x]=byteset3[x];
		}
		byte[] bytes4= new byte[b4]; 
		for(int x=0;x<b4;x++){
			bytes4[x]=byteset4[x];
			bytes4temp[x]=byteset4[x];
		}
		
		/*if(bytes1.length<bytes2.length){
			bytes1=bytes2temp;
			bytes2=bytes3temp;
			bytes3=bytes4temp;
			bytes4=bytes1temp; 
			
		}
		String b1s=null;
		String b2s=null;
		String b3s=null;
		String b4s=null;

	

		for(int x=0;x<bytes1.length;x++){
			b1s=b1s+Byte.valueOf(bytes1[x]).toString();
		}
		for(int x=0;x<bytes2.length;x++){
			b2s=b2s+Byte.valueOf(bytes2[x]).toString();
		}
		for(int x=0;x<bytes3.length;x++){
			b3s=b3s+Byte.valueOf(bytes3[x]).toString();
		}
		for(int x=0;x<bytes4.length;x++){
			b4s=b4s+Byte.valueOf(bytes4[x]).toString();
		}

		 if(b2s.startsWith("-1-40-1-32016")){
			 bytes1=bytes2temp;
			 bytes2=bytes1temp;
			
		}
		 else if(b3s.contains("-1-40-1-32016")){
			 bytes1=bytes3temp;
			 bytes3=bytes1temp;
			 System.out.println("Fail safe working");
		 }
		 else if(b4s.contains("-1-40-1-32016")){
			 bytes1=bytes4temp;
			 bytes4=bytes1temp; *
			 System.out.println("Fail safe working");
		 }
		 
		/*byte[] imagetemp1= new byte[bytes1.length+bytes2.length];
	
		System.arraycopy(bytes1, 0, imagetemp1, 0, bytes1.length);
		System.arraycopy(bytes2, 0, imagetemp1,bytes1.length,bytes2.length);
		byte[] imagetemp2= new byte[imagetemp1.length+bytes3.length];
		System.arraycopy(imagetemp1, 0, imagetemp2, 0, imagetemp1.length);
		System.arraycopy(bytes3, 0, imagetemp2, imagetemp1.length,imagetemp2.length-1);
		byte[] imagedata= new byte[(imagetemp2.length+bytes4.length)];

		System.arraycopy(imagetemp2, 0, imagedata, 0, imagetemp2.length);
		System.arraycopy(bytes4, 0, imagedata,imagetemp2.length,bytes4.length+imagetemp2.length);

		//System.arraycopy(bytes3, 0, imagedata,bytes2.length,bytes3.length);
		//System.arraycopy(bytes4, 0, imagedata,bytes3.length,bytes4.length);
		for(int x=0; x<imagedata.length;x++){
			System.out.println(imagedata[x]);
		}*/
		imagedata= new byte[bytes1.length+bytes2.length+bytes3.length+bytes4.length];
		int c=0;
		for(int x=0;x<bytes1.length;x++){
			imagedata[c]=bytes1[x];
			c++;
		}
		for(int x=0;x<bytes2.length;x++){
			imagedata[c]=bytes2[x];
			c++;
		}
		for(int x=0;x<bytes3.length;x++){
			imagedata[c]=bytes3[x];
			c++;
		}
		for(int x=0;x<bytes4.length;x++){
			imagedata[c]=bytes4[x];
			c++;
		}
		
		BufferedWriter o=null;
		try {
			o = new BufferedWriter(new FileWriter("A.txt"));
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int x=0; x<imagedata.length;x++){
			try {
				o.write(Byte.valueOf(imagedata[x]).toString());
				//o.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println(imagedata[x]);
		}
		try {
			o.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		System.out.println("imagedatas length is "+imagedata.length);
	
		System.out.println(imagedata.length+" bytes");
		//frame= new BufferedImage(720,540,BufferedImage.TYPE_INT_RGB); 
		
		Viewer.getNewFrame(socket,IP,port);
		System.out.println(imagedata.toString()); 
		
		/*File file= new File("test.jpg");
		try {
			ImageIO.write(frame, "jpg", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		if(Thread.currentThread().isInterrupted()){
		System.out.println("FR IS INTERUPPTED");

		}
		if(!Thread.currentThread().isInterrupted()){
		AU= new ActiveUpdate(socket,IP,port);
		AU.start();
		}
		

		
	}
	
	


}
