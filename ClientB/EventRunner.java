package ClientB;

import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.event.InputEvent;


public class EventRunner {
	
	public EventRunner(){
		
	}
	
	public void clickLocation(int x, int y) throws HeadlessException, AWTException{
		Robot robot= new Robot(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()); 
		int xloc=(int) (x/.7); 
		int yloc=(int) (y/.7); 
		robot.mouseMove(xloc, yloc); 
		robot.mousePress(InputEvent.BUTTON1_MASK); 
		robot.mouseRelease(InputEvent.BUTTON1_MASK); 
		
	}
	
	public void doubleClickLocation(int x, int y) throws HeadlessException, AWTException{
		Robot robot= new Robot(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()); 
		int xloc=(int) (x/.7); 
		int yloc=(int) (y/.7); 
		robot.mouseMove(xloc, yloc); 
		robot.mousePress(InputEvent.BUTTON1_MASK); 
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.mousePress(InputEvent.BUTTON1_MASK); 
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
	}
	
	public void keyAgent(int keyval) throws HeadlessException, AWTException{
		Robot robot= new Robot(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()); 
		System.out.println("KeyValue "+keyval); 
	robot.keyPress(keyval);
	robot.keyRelease(keyval); 
		
	}
	
	public void moveLocation(int x, int y) throws HeadlessException, AWTException{
		Robot robot= new Robot(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()); 
		int xloc=(int) (x/.7); 
		int yloc=(int) (y/.7); 
		robot.mouseMove(xloc, yloc); 
		
		
	}
	
	public void dragLocation(int x, int y) throws HeadlessException, AWTException{
		Robot robot= new Robot(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()); 
		int xloc=(int) (x/.7); 
		int yloc=(int) (y/.7); 
		robot.mousePress(InputEvent.BUTTON1_MASK); 
		robot.mouseMove(xloc, yloc); 
		robot.mouseRelease(InputEvent.BUTTON1_MASK); 
		
	}
	
	

}
