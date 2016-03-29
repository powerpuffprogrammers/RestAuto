package messageController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
	 * List of sockets of waiters logged in with employee id as key
	 */
	static HashMap<Long,Socket> waiterSockets; 
	
	/**
	 * List of sockets of host logged in with employee id as key
	 */
	static HashMap<Long,Socket> hostSockets;
	
	/**
	 * List of sockets of chefs logged in with employee id as key
	 */
	static HashMap<Long,Socket> chefSockets;
	
	/**
	 * List of sockets of managers logged in with employee id as key
	 */
	static HashMap<Long,Socket> managerSockets;
	
	/**
	 * List of input streams(listeners) of waiters logged in with employee id as key
	 */
	static HashMap<Long,DataInputStream> waiterIn; 
	
	/**
	 * List of input streams(listeners) of hosts logged in with employee id as key
	 */
	static HashMap<Long,DataInputStream> hostIn;
	
	/**
	 * List of input streams(listeners) of chefs logged in with employee id as key
	 */
	static HashMap<Long,DataInputStream> chefIn;
	
	/**
	 * List of input streams(listeners) of manager logged in with employee id as key
	 */
	static HashMap<Long,DataInputStream> managerIn;
	
	/**
	 * List of output streams(senders) of waiters logged in with employee id as key
	 */
	static HashMap<Long,DataOutputStream> waiterOut; 
	
	/**
	 * List of output streams(senders) of hosts logged in with employee id as key
	 */
	static HashMap<Long,DataOutputStream> hostOut;
	
	/**
	 * List of output streams(senders) of chefs logged in with employee id as key
	 */
	static HashMap<Long,DataOutputStream> chefOut;
	
	/**
	 * List of output streams(senders) of managers logged in with employee id as key
	 */
	static HashMap<Long,DataOutputStream> managerOut;
	
	
	/**
	 * Adds a waiter to the list of sockets
	 * @param id - employee id 
	 * @param sock - socket associated with this employee
	 * @param listener - input stream for this employee
	 */
	public static void addWaiterSocket(long id,Socket sock, DataInputStream listener){
		waiterSockets.put(id,sock);
		waiterIn.put(id,listener);
	}
	/**
	 * Adds a host to the list of sockets
	 * @param id - employee id 
	 * @param sock - socket associated with this employee
	 * @param listener - input stream for this employee
	 */
	public static void addHostSocket(long id,Socket sock, DataInputStream listener){
		hostSockets.put(id,sock);
		hostIn.put(id,listener);
	}
	
	/**
	 * Adds a chef to the list of sockets
	 * @param id - employee id 
	 * @param sock - socket associated with this employee
	 * @param listener - input stream for this employee
	 */
	public static void addChefSocket(long id,Socket sock, DataInputStream listener){
		chefSockets.put(id,sock);
		chefIn.put(id,listener);
	}
	
	/**
	 * Adds a manager to the list of sockets
	 * @param id - employee id 
	 * @param sock - socket associated with this employee
	 * @param listener - input stream for this employee
	 */
	public static void addManagerSocket(long id,Socket sock, DataInputStream listener){
		managerSockets.put(id,sock);
		managerIn.put(id,listener);
	}
	
	/**
	 * Adds a waiter to the list of output stream
	 * @param id - employee id 
	 * @param listener - input stream for this employee
	 */
	public static void addWaiterSender(long id,DataOutputStream sender){
		waiterOut.put(id,sender);
	}
	/**
	 * Adds a host to the list of output stream
	 * @param id - employee id 
	 * @param listener - input stream for this employee
	 */
	public static void addHostSender(long id,DataOutputStream sender){
		hostOut.put(id,sender);
	}
	/**
	 * Adds a chef to the list of output stream
	 * @param id - employee id 
	 * @param listener - input stream for this employee
	 */
	public static void addChefSender(long id,DataOutputStream sender){
		chefOut.put(id,sender);
	}
	
	/**
	 * Adds a manager to the list of output stream
	 * @param id - employee id 
	 * @param listener - input stream for this employee
	 */
	public static void addManagerSender(long id,DataOutputStream sender){
		managerOut.put(id,sender);
	}
	
	
	
	/**
	 * Removes a waiter from the list of sockets, input stream, and output stream
	 * @param id - employee id 
	 */
	public static void removeWaiterSocket(long id){
		waiterSockets.remove(id);
		waiterIn.remove(id);
		waiterOut.remove(id);
	}
	/**
	 * Removes a host from the list of sockets, input stream, and output stream
	 * @param id - employee id 
	 */
	public static void removeHostSocket(long id){
		hostSockets.remove(id);
		hostIn.remove(id);
		hostOut.remove(id);
	}
	/**
	 * Removes a chef from the list of sockets, input stream, and output stream
	 * @param id - employee id 
	 */
	public static void removeChefSocket(long id){
		chefSockets.remove(id);
		chefIn.remove(id);
		chefOut.remove(id);
	}
	/**
	 * Removes a manager from the list of sockets, input stream, and output stream
	 * @param id - employee id 
	 */
	public static void removeManagerSocket(long id){
		managerSockets.remove(id);
		managerIn.remove(id);
		managerOut.remove(id);
	}
	
	/**
	 * Each tablet that connects with the server should have a listener and sender associated with it.
	 * @param args
	 */
	public static void main(String[] args){
		waiterSockets= new HashMap<Long,Socket>();
		hostSockets = new HashMap<Long,Socket>();
		chefSockets=new HashMap<Long,Socket>();
		managerSockets= new HashMap<Long,Socket>();
		
		waiterIn= new HashMap<Long,DataInputStream>();
		hostIn= new HashMap<Long,DataInputStream>();
		chefIn= new HashMap<Long,DataInputStream>();
		managerIn= new HashMap<Long,DataInputStream>();
		
		
		waiterOut= new HashMap<Long,DataOutputStream>();
		hostOut= new HashMap<Long,DataOutputStream>();
		chefOut= new HashMap<Long,DataOutputStream>();
		managerOut= new HashMap<Long,DataOutputStream>();
		
		
		
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (true) {
            	Socket oneTablet = serverSocket.accept();
				new MessageControllerListener(oneTablet).start();
				new MessageControllerSender(oneTablet).start();
               
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }	
	}
	
	
}
