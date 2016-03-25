package databaseA;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import configuration.Configure;

/**
 * Starts the DataBase A. This will handle requests for log in. DB A holds employee info.
 * @author cms549
 *
 */
public class DatabaseAController extends Thread {
	
	/**
	 * Port number that Database A Controller will be listening for log in attempts on.
	 */
	private final static int portNumber = Configure.getPortNumber("DatabaseAController");
	
	/**
	 * Socket to one tablet that DB A will use to log people on.
	 * Each DBAController will have their own as there will be a new DBAController for each time someone tries to log on.
	 */
	private Socket currListener;
	
	/**
	 * This should hold the next ID number for a newly created employee.
	 * This ensures each employee ID is unique.
	 */
	private static long currentID;
	
	/**
	 * Array of Employees, Matches Employee ID (int) with employee
	 */
	private static ArrayList<Employee> employeeList;
	
	/**
	 * Constructor
	 * @param listener - socket to one tablet that database A will use to communicate with a tablet to log it on properly.
	 */
	public DatabaseAController(Socket listener) {
		currListener=listener;
	}

	/**
	 * Adds an employee to the database
	 * @param name - name of employee
	 * @param position - position of employee
	 * 		w = waiter, h = host, c = chef, m = manager, o = owner
	 * @return true on success, false if you did not put in a valid position or if name is null
	 */
	public static boolean addEmployee(String name, char position){
		char[] possiblePositions = {'w','h','c','m','o'};
		if(name==null){
			return false;
		}
		for(int i =0;i<possiblePositions.length;i++){
			if(position == possiblePositions[i]){
				employeeList.add(new Employee(name, currentID, position));
				employeeList.get((int) currentID).loggedIn=false;
				currentID++;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Starts a new thread for the DB A controller so it can communicate with one tablet on one thread.
	 * Reads the socket in and looks for a 'L' (which signifies logging in) or 'O' (logging out)
	 * Next it writes to the socket the position of the employee if they are logging in or '0' if the id is not valid
	 * 	it writes L if the employee is already logged in
	 * on a log out it changes the employee with that id to logged out
	 * This ensures that an employee doesn't log on to two devices.
	 */
	public void run(){
		DataInputStream in;
		DataOutputStream out;
		try {
			in = new DataInputStream(currListener.getInputStream());
			out = new DataOutputStream(currListener.getOutputStream());
			while(true){
				String mess =in.readUTF();
				char first = mess.charAt(0);
				if(first=='L'){ //logging in
					String num = mess.substring(2);
					int number = Integer.parseInt(num);
					//if employee id is invalid return 0
					if(number>=employeeList.size() || number<0){
						out.writeUTF("0");
					}
					else{
						Employee curE = employeeList.get(number);
						String ans = curE.position + curE.name;
						if(curE.loggedIn){
							out.writeUTF("L");//already logged in
						}
						else{
							curE.loggedIn=true;
							out.writeUTF(ans);
						}
					}
				}
				if(first=='O'){ //Logging out
					String num = mess.substring(2);
					int number = Integer.parseInt(num);
					//if employee id is valid log them out
					if(number<employeeList.size() && number>=0){
						Employee curE = employeeList.get(number);
						curE.loggedIn=false;
					}
				}
				in.close();
				out.close();
			}
			
			
		} catch (Exception e) {
			
			System.out.println("Disconnected from a client.");
		} 
	}
	
	/**
	 * Starts the DataBase A. Creates a new thread for each log in request.
	 * @param args
	 */
	public static void main(String[] args){
		//later we will load this from a file
		currentID=0;
		employeeList = new ArrayList<Employee>();
		generateRandomEmployeeList();
		ServerSocket server = null;
		
		try {
			server = new ServerSocket(portNumber);
			while(true){
				Socket listener = server.accept();
				Thread t= new DatabaseAController(listener);
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
	 * Used for testing.
	 */
	public static void generateRandomEmployeeList(){
		addEmployee("Emma Roussos", 'w');
		addEmployee("Athira Haridas",'m');
		addEmployee("Annie Antony",'c');
		addEmployee("Christina Parry",'h');
		
		
	}
	
	
}
