package MainServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class WebLayerComm extends Thread{
	private int webCommPort=51412;
    private ServerSocket webComm;
    private boolean isStopped = false;

    
	public WebLayerComm(){
        initWebComm();
	}
	
    public void initWebComm(){
    	try {
			webComm = new ServerSocket(51412);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    public void getTCPRequestLoop() {
    	Socket clientSocket = null;
    	Thread handle = null;
        while (!isStopped) {
            
            try {
                clientSocket = webComm.accept();

                
            } catch (IOException e) {
                if (isStopped) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }

           handle= new Thread(new HandleRequests(clientSocket,0));
           handle.start();

            
        }
        
        try {
			clientSocket.close();
			handle.join();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void run(){
    	getTCPRequestLoop();
    }
}
