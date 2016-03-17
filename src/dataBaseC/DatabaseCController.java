package dataBaseC;

//LAST CODED BY: CHRISTINA SEGERHOLM ON 3/16

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import configuration.Configure;

/**
 * Starts the DB C - host will use this to grab the list of tables 
 * @author cms17
 *
 */
public class DatabaseCController extends Thread {
	
	private final static int portNumber = Configure.getPortNumber("DatabaseCController");
	
	private Socket currListener;
	
	//Table Number to tableinfo
	public static HashMap<Integer, Table> listOfTables;

	public DatabaseCController(Socket listener) {
		currListener=listener;
	}

	public static boolean addTable(int tabNum, int maxOccupancy){
		if(listOfTables.containsKey(tabNum)){
			return false;
		}
		listOfTables.put(tabNum, new Table(tabNum, maxOccupancy));
		return true;
	}
	
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
	
	public void run(){
		try {
			DataInputStream in = new DataInputStream(currListener.getInputStream());
			DataOutputStream out = new DataOutputStream(currListener.getOutputStream());
			while(true){
				String mess =in.readUTF();
				char first = mess.charAt(0);
				if(first=='T'){ //send the host a list of tables
					
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("Disconnected from a client.");
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args){
		listOfTables = new HashMap<Integer,Table>();
		generateTables();
		try {
			ServerSocket server = new ServerSocket(portNumber);
			
			while(true){
				Socket listener = server.accept();
				Thread t= new DatabaseCController(listener);
				t.start();
			}
		} catch (Exception e) {
			System.out.println("ERROR: FAILED TO START SERVER.");
			e.printStackTrace();
		}
		
		
	}
	
	
}
