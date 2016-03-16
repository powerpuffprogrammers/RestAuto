package messageController;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import configuration.Configure;

/**
 * Starts the Message Controller - will handle ticket sends and message sends between tablets
 * @author cms17
 *
 */
public class MessageController {
	
	private final static int portNumber = Configure.getPortNumber("MessageController");
	

	//Message Controller fields:
	//List of waiters logged in
	static ArrayList<Socket> waiters; //should this be done by id?!?!?!
	//List of Hosts logged in
	static ArrayList<Socket> hosts;
	//List of chefs logged in
	static ArrayList<Socket> chefs;
	//list of managers logged in
	static ArrayList<Socket> managers;
	

	public static void addWaiterSocket(Socket listener){
		waiters.add(listener);
	}
	
	public static void main(String[] args){
		
		try {
			ServerSocket server = new ServerSocket(portNumber);
			
			while(true){
				Socket listener = server.accept();
				Thread t= new MessageControllerHandler(listener);
				t.start();
			}
		} catch (Exception e) {
			System.out.println("ERROR: FAILED TO START SERVER.");
			e.printStackTrace();
		}
		
		
	}
	
	
}
