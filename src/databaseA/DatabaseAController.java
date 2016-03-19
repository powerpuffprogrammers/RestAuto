package databaseA;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import configuration.Configure;

/**
 * Starts the DB A - will handle requests for log in- holds employee info
 * also holds menu for server so server will have to get this from DB A
 * @author cms17
 *
 */
public class DatabaseAController extends Thread {
	
	private final static int portNumber = Configure.getPortNumber("DatabaseAController");
	
	private Socket currListener;
	
	//This should hold the next ID number for a newly created employee
	private static long currentID;
	
	//Matches Employee ID with employee
	private static ArrayList<Employee> employeeList;
	

	public DatabaseAController(Socket listener) {
		currListener=listener;
	}

	public static boolean addEmployee(String name, char position){
		char[] possiblePositions = {'w','h','c','m','o'};
		for(int i =0;i<possiblePositions.length;i++){
			if(position == possiblePositions[i]){
				employeeList.add(new Employee(name, currentID, position));
				currentID++;
				return true;
			}
		}
		
		return false;
	}
	
	
	public void run(){
		try {
			DataInputStream in = new DataInputStream(currListener.getInputStream());
			DataOutputStream out = new DataOutputStream(currListener.getOutputStream());
			while(true){
				String mess =in.readUTF();
				char first = mess.charAt(0);
				if(first=='L'){ //logging in
					String num = mess.substring(2);
					int number = Integer.parseInt(num);
					if(number>employeeList.size() || number<0){
						out.writeUTF("0");
					}
					else{
						Employee curE = employeeList.get(number);
						String ans = curE.position + curE.name;
						out.writeUTF(ans);
					}
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("Disconnected from a client.");
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args){
		currentID=0;//or should we fetch this from the file?
		employeeList = new ArrayList<Employee>();
		generateRandomEmployeeList();
		
		
		try {
			ServerSocket server = new ServerSocket(portNumber);
			
			while(true){
				Socket listener = server.accept();
				Thread t= new DatabaseAController(listener);
				t.start();
			}
		} catch (Exception e) {
			System.out.println("ERROR: FAILED TO START SERVER.");
			e.printStackTrace();
		}
		
		
	}
	
	public static void generateRandomEmployeeList(){
		addEmployee("Christina Segerholm", 'w');
		addEmployee("Athira Haridas",'m');
		addEmployee("Annie Antony",'c');
		//addEmployee("Nishtha Sharma",'o');
		addEmployee("Emma Roussos",'w');
		//addEmployee("Christina Parry",'h');
		
		
	}
	
	
}
