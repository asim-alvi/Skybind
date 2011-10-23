package MainServer;

import java.io.Serializable;

public class Friend implements Serializable {
	private String Name;
	private String UserName;
	private String privateIP;
	private String publicIP;
	private int privatePort;
	private int publicPort;
	private boolean online;
	//private FileList files;
	
	
	
	public Friend(String name, String userName, 
			String privateIP, String publicIP, int privatePort, int publicPort,
			boolean online) {
		Name = name;
		UserName = userName;
		this.privateIP = privateIP;
		this.publicIP = publicIP;
		this.privatePort = privatePort;
		this.publicPort = publicPort;
		this.online = online;
	}

	public String getName(){
		return Name;
	}
	
	public String getUserName(){
		return UserName;
	}
	
	public String getprivateIP(){
		return privateIP;
	}
	
	public String getpublicIP(){
		return publicIP;
	}
	public void setpublicIP(String publicIP){
		this.publicIP=publicIP;
	}
	
	public boolean isOnline(){
		return online;
	}
	
	public void setOnline(boolean value){
		online=value;
	}
	public int getPort(){
		return publicPort;
	}

}
