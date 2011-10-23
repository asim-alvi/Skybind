package ClientB;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class FBUpdate {
	
	public int numberOfRectangles;
	public static int[] Pixels; 
	public static Image out;
	public static BufferedImage tester;
	public static byte[] frame; 
	public FBUpdate(){
		
	}
	
	/*public FBUpdate(int numberOfRectangles){
		this.numberOfRectangles=numberOfRectangles;
		//Object snapshotSemaphor=new Object(); 
	}*/

	public FBUpdate(boolean incremental, int xpos, int ypos, int width, int height) throws HeadlessException, AWTException{
		
		System.out.println("Worked");

		Robot robot= new Robot(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()); 
        BufferedImage image = robot.createScreenCapture( new Rectangle( xpos, ypos, width, height ) );
        int scaledwidth= (int) (width*.7);
        int scaledheight=(int) (height*.7);
        BufferedImage scaledImage= new BufferedImage(scaledwidth,scaledheight, BufferedImage.TYPE_INT_RGB); 
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image,xpos,ypos,1120,630,null); 
        graphics2D.dispose();
        
        tester=scaledImage; 
    
      
          try {  
        	  ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	  BufferedOutputStream bos= new BufferedOutputStream(baos);
        	System.out.println("Knock 1");
			ImageIO.write(tester, "jpg", bos);
			System.out.println("Knock 2");
			baos.flush();
			byte buf[]=baos.toByteArray();	
			baos.close();
			frame=buf;

		baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
			System.out.println("PROBLEM!!!");
			
		}
		System.out.println("The Image is "+frame.length+" bytes big");
		

        
        
        // int pixelsnum[]= new int [width*height];
		//PixelGrabber pg= new PixelGrabber(image, xpos, ypos, width, height, pixelsnum, 0, width);
		//Pixels=(int[]) pg.getPixels();
		//============================================================================================
        //out=getImageFromPixels(width,height); 
    
        
      
		


        
System.out.println("DONE"); 
        
        

	}
	
	
	public Image getImageFromPixels(int width,int height){
		 BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
         WritableRaster raster = (WritableRaster) image.getData();
         raster.setPixels(0,0,width,height,Pixels);
         return image;

		
	}
	
	public void outputImage(JFrame frame){
		frame.setBounds(0, 0, 1600, 900);
		frame.setVisible(true);
		Graphics2D g= (Graphics2D) frame.getRootPane().getGraphics();
		g.drawImage(tester,null,0,0); 
	}
	
	public static void main(String[] args) {
		FBUpdate FBU=null;
		try {
			FBU= new FBUpdate(false,0,0,1600,900);
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			System.exit(0); 
			e.printStackTrace();
		}
	

		
	}

}
