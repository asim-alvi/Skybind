package ClientB;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


public class clientB {

	SSLSocket serverConn = null;
	PrintWriter out = null;
	BufferedReader in = null;
	public static User user;
	public InetAddress LocalIPB = null;
	public static DatagramSocket UDPSock = null;


	public clientB(String username) {
		try {
			try {

				SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
						.getDefault();
				serverConn = (SSLSocket) sslsocketfactory.createSocket(
						"192.168.1.33", 51414);
				final String[] enabledCipherSuites = { "SSL_DH_anon_WITH_RC4_128_MD5" };
				serverConn.setReuseAddress(true);

				serverConn.setEnabledCipherSuites(enabledCipherSuites);
			} catch (UnknownHostException e) {
				System.err.println("Don't know about host: Server ");
				// System.exit(1);
			} catch (IOException e) {
				System.err.println("Couldn't get I/O for "
						+ "the connection to: Server .");
				// System.exit(1);
			}

			// SocketAddress sockaddr = new InetSocketAddress("127.0.0.1",
			// 55555);

			out = new PrintWriter(serverConn.getOutputStream(), true);
			// out.write("test");
			in = new BufferedReader(new InputStreamReader(serverConn
					.getInputStream()));
		} catch (IOException ex) {
			Logger.getLogger(clientB.class.getName()).log(Level.SEVERE, null,
					ex);
		}

		new TCPReceivingThread(serverConn, in, out).start();
		
		System.out.println("Hello");
		System.out.println("Working...");
		InetAddress addr = null;
		
		
		try {
			UDPSock = new DatagramSocket(50000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		user = new User(username,"none");
		user.setPrivatePort(50000);
		UDPServerConnect();
		// System.out.println(LocalIPA.toString());
	
		// System.out.println(localport);
		System.out.println(user.getprivateIP());

		//waittime(1000);

		//UpdateFrame UFThread = new UpdateFrame();
		// UDPSockR.close();
		//waittime(10000);
		//UFThread.start();

	}
	public synchronized void UDPServerConnect() {
		
		String IPandPort = null;
		try {

			Enumeration<NetworkInterface> nets = NetworkInterface
					.getNetworkInterfaces();
			for (NetworkInterface netint : Collections.list(nets)) {
				Enumeration<InetAddress> inetAddresses = netint
						.getInetAddresses();
				for (InetAddress inetAddress : Collections.list(inetAddresses)) {
					if ((inetAddress.getHostAddress().startsWith("192.168"))
							|| (inetAddress.getHostAddress().startsWith("10.0"))
							|| (inetAddress.getHostAddress()
									.startsWith("172.16"))) {
						user.setprivateIP(inetAddress.getHostAddress().replace("/", ""));
						
					}
				}
			}

			IPandPort = user.getprivateIP() + ":"
					+ user.getPrivatePort();

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		byte[] data = (true+ ":" + IPandPort+":"+user.getUserName()).getBytes();
		DatagramPacket packet = null;
		try {
			packet = new DatagramPacket(data, data.length, InetAddress.getByName("192.168.1.33"),64000);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			UDPSock.send(packet);

			byte receiveData[] = new byte[100];
			DatagramPacket receive = new DatagramPacket(receiveData,
					receiveData.length);
			UDPSock.receive(receive);
			String rData[] = new String(receive.getData()).trim().split(":");
			if (rData.length == 2) {
				user.setpublicIP(rData[0]);
				user.setPublicPort(Integer.parseInt(rData[1]));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	

	

	

	

	public static void waittime(int n) {
		long t0, t1;
		t0 = System.currentTimeMillis();
		do {
			t1 = System.currentTimeMillis();
		} while (t1 - t0 < n);
	}

	public static void main(String[] args) {
		clientB client = new clientB("jed");

	}

}
