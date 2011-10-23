/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author kevin
 */
public class MainServer implements Runnable {

    private Thread main;
    ThreadPoolExecutor threadPool = null;
    private int mainPort = 51414;
    private boolean isStopped = false;
    private SSLServerSocket srvr;
    public static final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
    public static List threads;
	private static int ID=1;

    int poolSize = 25000;
    
    int maxPoolSize = 25000;
 
    long keepAliveTime = 120;
    
    
    public MainServer() {
    //	new GetPassword();
        main = new Thread(this);
        main.start();
        new WebLayerComm().start();
        	
        

    }

    public void run() {
        initServer();
        getTCPRequestLoop();



    }
    public void initServer(){
    	threadPool = new ThreadPoolExecutor(poolSize, maxPoolSize,
                keepAliveTime, TimeUnit.MINUTES, queue);
    	
    	try {
            SSLServerSocketFactory sslserversocketfactory =
                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
                   

            srvr = (SSLServerSocket) sslserversocketfactory.createServerSocket(mainPort);
             final String[] enabledCipherSuites = { "SSL_DH_anon_WITH_RC4_128_MD5" };
srvr.setEnabledCipherSuites(enabledCipherSuites);

        } catch (IOException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
       threads = Collections.synchronizedList(new ArrayList<HandleRequests>());
    }
    

    
    public void getTCPRequestLoop() {
    	SSLSocket clientSocket = null;
    	UDPServerReceive USR= new UDPServerReceive();
    	USR.start(); 
        while (!isStopped) {
            
            try {
                clientSocket = (SSLSocket) srvr.accept();

                
            } catch (IOException e) {
                if (isStopped) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
            
            HandleRequests hr = new HandleRequests(clientSocket,ID);
            ID++;
            threads.add(hr);
            threadPool.execute(hr);

            
        }
        
        try {
			clientSocket.close();
			try {
				main.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			threadPool.shutdown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    

    public static void main(String args[]) {
        MainServer m = new MainServer();
    }
}

