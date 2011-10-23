package MainServer;

public class BadInputException extends Exception {
	private String input;
	public BadInputException(String input){
		this.input=input;
		
	}
	
	public String getMessage(){
		return "Invalid Message: "+input;
	}
}
