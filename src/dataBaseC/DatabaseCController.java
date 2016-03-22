package dataBaseC;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.google.gson.Gson;

import configuration.Configure;

/**
 * Starts the DB C 
 * Host will use this to grab the list of tables 
 * @author cms549
 *
 */
public class DatabaseCController extends Thread {
	
	/**
	 * port number DB B will be listening on
	 */
	private final static int portNumber = Configure.getPortNumber("DatabaseCController");
	
	/**
	 * used to convert java objects to JSON format and vice versa.
	 */
	public static Gson jsonConverter = new Gson();
	
	/**
	 * Listens to one tablet.
	 * Each DBCController thread gets one.
	 */
	private Socket currListener;
	
	/**
	 * Holds the list of tables. 
	 * See TableList.java
	 */
	public static TableList listOfTables;

	/**
	 * Constructor
	 * @param listener
	 */
	public DatabaseCController(Socket listener) {
		currListener=listener;
	}

	/**
	 * Adds table to the list of tables
	 * @param tabNum - table number you wish to add
	 * @param maxOccupancy - max amount of people that can fit at the table
	 * @return true on success, false on failure
	 */
	public static boolean addTable(int tabNum, int maxOccupancy){
		if(listOfTables.hm.containsKey(tabNum)){
			return false;
		}
		listOfTables.hm.put(tabNum, new Table(tabNum, maxOccupancy));
		return true;
	}
	
	
	/**
	 * Each request will get its own thread. This will be used to send the host the list of tables
	 */
	public void run(){
		try {
			DataInputStream in = new DataInputStream(currListener.getInputStream());
			DataOutputStream out = new DataOutputStream(currListener.getOutputStream());
			while(true){
				String mess =in.readUTF();
				char first = mess.charAt(0);
				if(first=='T'){ //send the host a list of tables
					out.writeUTF(jsonConverter.toJson(listOfTables));
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("Disconnected from a client.");
		} 
	}
	
	/**
	 * Starts base thread for DB B
	 * @param args
	 */
	public static void main(String[] args){
		listOfTables = new TableList();
		generateTables();
		ServerSocket server = null;
		try {
			server = new ServerSocket(portNumber);
			
			while(true){
				Socket listener = server.accept();
				Thread t= new DatabaseCController(listener);
				t.start();
			}
		} catch (Exception e) {
			if(server!=null){
				try {
					server.close();
				} catch (IOException e1) {}
			}
			System.out.println("ERROR: FAILED TO START SERVER.");
		}
		
		
	}
	
	
	/**
	 * Used for testing
	 */
	public static void generateTables(){
		addTable(1,4);
		addTable(2,4);
		addTable(3,4);
		addTable(4,6);
		addTable(5,6);
		addTable(6,2);
		addTable(7,2);
		addTable(8,4);
		addTable(9,4);
		addTable(10,4);
		addTable(11,6);
		addTable(12,6);
		addTable(13,6);
		addTable(14,2);
		addTable(15,2);
		addTable(16,2);
	}	
}
