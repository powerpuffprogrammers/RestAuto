package manager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import messageController.Message;

/**
 * Used to listen from messages from the message controller and decode them.
 * @author cms549
 */
public class ManagerMessageListener extends Thread {
	
	/**
	 * Waiter's employee id
	 */
	private long empID;
	
	/**
	 * Socket that this waiter will connect to.
	 */
	private Socket sock;
	
	/**
	 * Pointer back to its manager interface
	 */
	private ManagerInterface mi;

	
	/**
	 * Constructor
	 * @param listener - socket to listen to
	 * @param empID - manager's employee id
	 * @param mI - manager interface
	 */
	public ManagerMessageListener(Socket listener, long empID, ManagerInterface mI) {
		sock=listener;
		this.empID=empID;
		mi=mI;
	}
	
	/**
	 * Listens for messages sent from the MC
	 */
	public void run(){
		try {
			DataInputStream in = new DataInputStream(sock.getInputStream());
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			//send a message wempID to MC so they sign you in
			String logInToMC = "m"+empID;
			out.writeUTF(logInToMC);
			out.close();
			//just keep listening
			while(true){
				String mes = in.readUTF();
				Message m =Message.fromString(mes);
				decodeMessage(m);
			}
			
			
		}catch (EOFException e) { 
			try {
				sock.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}catch (Exception e) {
			System.out.println("Disconnected from a client.");
			
			e.printStackTrace();
		} 
		
		
	}

	/**
	 * Add any messages to the list so they can ve displayed
	 * @param m - message to add
	 */
	private void decodeMessage(Message m) {
		mi.addMessageToList(m);
		
	}
	
}
