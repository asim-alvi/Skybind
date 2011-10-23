package ClientA;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class NewFrame extends JPanel {
    BufferedImage image;
    Dimension size = new Dimension();

    public NewFrame(BufferedImage image) {
        this.image = image;
        if(image==null){
        	System.out.println("Image is nul!!");
        	return;
        }
        
        size.setSize(image.getWidth(), image.getHeight());
    }

   
    protected void paintComponent(Graphics g) {
        // Center image in this component.
    	// Call this method when you need to update the image
        int x = (getWidth() - size.width)/2;
        int y = (getHeight() - size.height)/2;
        g.drawImage(image, x, y, this);
    }

    public Dimension getPreferredSize() { return size; }
    
    

    public static void main(String[] args) throws IOException {
     
        
    }
    
   

 
}
