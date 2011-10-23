package ClientA;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class KeyboardEvent extends Thread {
public Integer Key=null;
public Integer ID=null;
public Integer Count=null;
private DatagramSocket socket;
private InetAddress IP;
private int port;

	public KeyboardEvent(int key, int id,int count,DatagramSocket socket,InetAddress IP,int port){
		Key=key;
		ID=id;
		Count=count;
		this.socket=socket;
		this.IP=IP;
		this.port=port;
	}
	
	public void run(){
		byte[] buf=new byte[5];
		
		String dat = Integer.toString(Key)+":"+Integer.toString(ID)+":"+Integer.toString(Count);
		if(dat.length()!=14){
			for(int x=dat.length();x<14;x++){
				dat+=":";
			}
		}
		DatagramPacket packetx= new DatagramPacket(dat.getBytes(),dat.getBytes().length, IP, port);	

		/*byte[] x=intToByteArray(Key);  
		byte[] y=intToByteArray(ID);
		byte[] count=intToByteArray(Count);
		String click="click";
		buf=click.getBytes();
		DatagramPacket packetx= new DatagramPacket(x,x.length, IP, port);	
		DatagramPacket packety= new DatagramPacket(y,y.length, IP, port);
		DatagramPacket packettype= new DatagramPacket(count,count.length, IP, port);
		*/

		try {
			socket.send(packetx);
			//socket.send(packety);
			//socket.send(packettype);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
	public static final byte[] intToByteArray(int value) {
		return new byte[]{
		(byte)(value >>> 24), (byte)(value >> 16 & 0xff), (byte)(value >> 8 & 0xff), (byte)(value & 0xff) };
		}
}
