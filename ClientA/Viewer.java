package ClientA;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Viewer extends JPanel {
	static JFrame f=new JFrame();
	private static long lastPressProcessed=0;
	private static DatagramSocket socket;
	private static InetAddress IP;
	private static int port;
	
	
    public static void getNewFrame(DatagramSocket socket,InetAddress IP,int port){  
    	f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	Viewer.socket=socket;
    	Viewer.IP=IP;
    	Viewer.port=port;
    	
    	/* FBUpdate FBU=null;
  		try {
  			FBU= new FBUpdate(false,0,0,1600,900);
  		} catch (HeadlessException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		} catch (AWTException e) {
  			System.exit(0); 
  			e.printStackTrace();
  		}*/
  		
 		byte[] frame = FrameReceiver.imagedata;
 		BufferedImage image=null;
 		try {
			
			image= ImageIO.read(new ByteArrayInputStream(frame));
		
			if(frame==null){
				System.out.println("frame null");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//if(image==null){
		//	FrameReceiver.AU.interrupt(); 
			// ActiveUpdate.FR.interrupt(); 
			
		//}
    	//if(!(image==null)){
		NewFrame content=new NewFrame(image);
         content.addMouseListener(new updateFrame(socket,IP,port)); 
         content.addMouseMotionListener(new motionDetector()); 

         f.setFocusable(true); 
         f.addKeyListener(new keyUpdate()); 
         f.requestFocus();
         f.setContentPane(content);
          f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          
        //  f.setSize(image.getWidth(),image.getHeight()+40);
          f.pack();
          f.setVisible(true);
    	//}
          
         
          
    }

    public static void main(String[] args) throws IOException {
   
 		
 	//	 getNewFrame(); 
        
       
    }
   private static class updateFrame implements MouseListener{
	 
	   private DatagramSocket socket;
	   private InetAddress IP;
	   private int port;
	   
	   public updateFrame(DatagramSocket socket, InetAddress IP, int port){
		   this.socket=socket;
		   this.IP=IP;
		   this.port=port;
	   }
	   
	@Override
	public void mouseClicked(java.awt.event.MouseEvent arg0) {

		 /*FrameReceiver.AU.interrupt(); 
		 ActiveUpdate.FR.interrupt(); 
		 
		 try {
				ActiveUpdate.FR.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

		System.out.println("Mouse Clicked"); 
		int x= arg0.getX();
		int y=arg0.getY();
		int count=arg0.getClickCount();
		System.out.println("Points are "+x+" "+y);
		ClickEvent CE= new ClickEvent(x,y,count,socket,IP,port);
		CE.run();		
		//FrameReceiver FR= new FrameReceiver(socket,IP,port);
		//FR.run(); 
		
	}

	
	public void mouseEntered(java.awt.event.MouseEvent arg0) {
		System.out.println("Entered");
		
	}

	
	public void mouseExited(java.awt.event.MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent arg0) {
		System.out.println("Released");
		
	}
   

 
}
   public static class keyUpdate implements KeyListener{
	   public void keyTyped ( KeyEvent e ){  
		   
		
		   
		  }  
		   public void keyPressed ( KeyEvent e){  
			   if(System.currentTimeMillis()-lastPressProcessed>.0000000000001){
				  /* FrameReceiver.AU.interrupt(); 
					 ActiveUpdate.FR.interrupt(); 
					 try {
						ActiveUpdate.FR.join();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}*/ 

					 int keyvalue=e.getKeyCode(); 
			   System.out.println("Key value "+keyvalue); 
			   KeyboardEvent KE= new KeyboardEvent(keyvalue,3000,0,socket,IP,port);
				KE.run();			   
				//FrameReceiver FR= new FrameReceiver(socket,IP,port);
				//FR.run(); 
			   }
			   lastPressProcessed= System.currentTimeMillis(); 
			   
			   
		   }  
		   public void keyReleased ( KeyEvent e ){ 
			   
			   
		   }
   }
   
    private static class motionDetector implements MouseMotionListener{

	
	public void mouseDragged(MouseEvent arg0) {
		/*System.out.println("Mouse Dragged"); 
		FrameReceiver.AU.interrupt(); 
		 ActiveUpdate.FR.interrupt(); 
		 
		 try {
				ActiveUpdate.FR.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		int x= arg0.getX();
		int y=arg0.getY();
		
		ClickEvent CE= new ClickEvent(x,y,9,socket,IP,port);
		CE.run();		
		FrameReceiver FR= new FrameReceiver(socket,IP,port);
		FR.run();*/

	}

	@Override
	public void mouseMoved(java.awt.event.MouseEvent arg0){
		/*System.out.println("Mouse moved"); 
		FrameReceiver.AU.interrupt(); 
		 ActiveUpdate.FR.interrupt(); 
		 
		 try {
				ActiveUpdate.FR.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		int x= arg0.getX();
		int y=arg0.getY();
		
		ClickEvent CE= new ClickEvent(x,y,8,socket,IP,port);
		CE.run();		
		FrameReceiver FR= new FrameReceiver(socket,IP,port);
		FR.run();*/
		
	}

	
	   
  }

	
}
