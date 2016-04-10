package databaseB;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.google.gson.Gson;

import configuration.Configure;

/**
 * Starts the DB B.
 * Host will use this to grab the list of tables 
 * @author cms549
 *
 */
public class DatabaseBController extends Thread {
	
	/**
	 * port number DB B will be listening on
	 */
	private final static int portNumber = Configure.getPortNumber("DatabaseBController");
	
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
	public DatabaseBController(Socket listener) {
		currListener=listener;
	}

	/**
	 * Adds table to the list of tables
	 * @param tabNum - table number you wish to add
	 * @param maxOccupancy - max amount of people that can fit at the table
	 * @return true on success, false on failure
	 */
	public static boolean addTable(int tabNum, int maxOccupancy, char type){
		if(listOfTables.hm.containsKey(tabNum)){
			return false;
		}
		listOfTables.hm.put(tabNum, new Table(tabNum, maxOccupancy,type));
		return true;
	}
	
	
	/**
	 * Each request will get its own thread. This will be used to send the host the list of tables.
	 * Socket hangs up after sending the table list.
	 */
	public void run(){
		String mess = "";
		try(DataInputStream in = new DataInputStream(currListener.getInputStream());
				DataOutputStream out = new DataOutputStream(currListener.getOutputStream());) {
			mess=in.readUTF();
			char first = mess.charAt(0);
			if(first=='T'){ //send the host a list of tables
				System.out.println(jsonConverter.toJson(listOfTables));
				out.writeUTF(jsonConverter.toJson(listOfTables));
			}
			in.close();
			out.close();
			currListener.close();
		} catch (Exception e) {
			System.out.println("Before Error: Read in: "+ mess);
			e.printStackTrace();
		} 
	}
	
	/**
	 * Starts base thread for DB B
	 * @param args
	 */
	public static void main(String[] args){
		listOfTables = new TableList();
		generateTables();
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (true) {
               new DatabaseBController(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("ERROR: DB B failed to start. Port " + portNumber+" is in use.");
            System.exit(-1);
        }	
	}
	
	
	/**
	 * Used for testing
	 */
	public static void generateTables(){
		addTable(1,4,'t');
		addTable(2,4,'t');
		addTable(3,4,'t');
		addTable(4,6,'b');
		addTable(5,6,'b');
		addTable(6,2,'t');
		addTable(7,2,'t');
		/*
		addTable(8,4);
		addTable(9,4);
		addTable(10,4);
		addTable(11,6);
		addTable(12,6);
		addTable(13,6);
		addTable(14,2);
		addTable(15,2);
		addTable(16,2);
		*/
	}	
}
