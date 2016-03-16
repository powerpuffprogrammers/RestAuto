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

	public boolean addTable(int tabNum, int maxOccupancy){
		if(listOfTables.containsKey(tabNum)){
			return false;
		}
		listOfTables.put(tabNum, new Table(tabNum, maxOccupancy));
		return true;
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
