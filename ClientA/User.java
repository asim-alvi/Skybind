package ClientA;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class User implements Serializable {
	private String Name;
	private String UserName;
	private String cookie;
	private InetAddress privateIP;
	private InetAddress publicIP;
	private int privatePort;
	private int publicPort;
	public int width;
	public int height;
	//private Settings settings;
	private boolean online;
	//private ArrayList<Friend> friends;
	
	public User(String username,String name){
		this.UserName=username;
		this.Name=name;
	//	settings=new Settings();
		
	}
	
	
	
	public String getCookie() {
		return cookie;
	}



	public void setCookie(String cookie) {
		this.cookie = cookie;
	}



	public String getName(){
		return Name;
	}
	
	public String getUserName(){
		return UserName;
	}
	
	public InetAddress getprivateIP(){
		return privateIP;
	}
	
	public void setPublicPort(int port){
		this.publicPort=port;
	}
	
	public int getPrivatePort(){
		return this.privatePort;
	}
	
	public void setPrivatePort(int port){
		this.privatePort=port;
	}
	
	public InetAddress getpublicIP(){
		return publicIP;
	}
	public void setpublicIP(String publicIP){
		try {
			this.publicIP=InetAddress.getByName(publicIP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean getOnline(){
		return online;
	}
	
	public void setOnline(boolean value){
		online=value;
	}
	
	public void setprivateIP(String privateIP){
		try {
			this.privateIP=InetAddress.getByName(privateIP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	public void setFriends(ArrayList<Friend> friends){
		this.friends=friends;
	}
	public Settings getSettings(){
		return settings;
		
	}
	
*/
}
