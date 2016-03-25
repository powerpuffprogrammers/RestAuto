package messageController;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import configuration.Configure;

/**
 * Starts the Message Controller - will handle ticket sends and message sends between tablets
 * @author cms549
 */
public class MessageController {
	
	private final static int portNumber = Configure.getPortNumber("MessageController");
	
	/**
	 * List of waiters logged in with employee id as key
	 */
	static HashMap<Long,Socket> waiters; 
	/**
	 * List of Hosts logged in
	 */
	static HashMap<Long,Socket> hosts;
	/**
	 * List of chefs logged in
	 */
	static HashMap<Long,Socket> chefs;
	/**
	 * list of managers logged in
	 */
	static HashMap<Long,Socket> managers;
	
	/**
	 * Adds a waiter to the list of sockets
	 * @param id - employee id 
	 * @param listener - socket associated with this employee
	 */
	public static void addWaiterSocket(long id,Socket listener){
		waiters.put(id,listener);
	}
	/**
	 * Adds a host to the list of sockets
	 * @param id - employee id 
	 * @param listener - socket associated with this employee
	 */
	public static void addHostSocket(long id,Socket listener){
		hosts.put(id,listener);
	}
	/**
	 * Adds a chef to the list of sockets
	 * @param id - employee id 
	 * @param listener - socket associated with this employee
	 */
	public static void addChefSocket(long id,Socket listener){
		chefs.put(id,listener);
	}
	/**
	 * Adds a manager to the list of sockets
	 * @param id - employee id 
	 * @param listener - socket associated with this employee
	 */
	public static void addManagerSocket(long id,Socket listener){
		managers.put(id,listener);
	}
	/**
	 * Removes a waiter from the list of sockets
	 * @param id - employee id 
	 */
	public static void removeWaiterSocket(long id){
		waiters.remove(id);
	}
	/**
	 * Removes a host from the list of sockets
	 * @param id - employee id 
	 */
	public static void removeHostSocket(long id){
		hosts.remove(id);
	}
	/**
	 * Removes a chef from the list of sockets
	 * @param id - employee id 
	 */
	public static void removeChefSocket(long id){
		chefs.remove(id);
	}
	/**
	 * Removes a manager from the list of sockets
	 * @param id - employee id 
	 */
	public static void removeManagerSocket(long id){
		managers.remove(id);
	}
	
	
	public static void main(String[] args){
		waiters= new HashMap<Long,Socket>();
		hosts= new HashMap<Long,Socket>();
		chefs=new HashMap<Long,Socket>();
		managers= new HashMap<Long,Socket>();
		Thread sender= new MessageControllerSender();
		sender.start();
		try {
			ServerSocket server = new ServerSocket(portNumber);
			
			while(true){
				Socket listener = server.accept();
				Thread t= new MessageControllerListener(listener,(MessageControllerSender)sender);
				t.start();
			}
		} catch (Exception e) {
			System.out.println("ERROR: FAILED TO START SERVER.");
			e.printStackTrace();
		}
		
		
	}
	
	
}
