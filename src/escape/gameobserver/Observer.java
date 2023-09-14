package escape.gameobserver;

public class Observer implements GameObserver {

	private String message = "";
	
	public Observer() {}
	
	/**
     * Receive a message from the game
     * @param message
     */
	public void notify(String message){
    	this.message = message;
    }
	
	/**
     * Receive a message with the cause
     * @param message
     * @param cause usually the exception that caused the state indicated
     * 	by the message
     */
    public void notify(String message, Throwable cause) {
    	this.message = message + ": " + cause.getMessage();
    }
    
    public String getMessage() {
    	return message;
    }
	
}
