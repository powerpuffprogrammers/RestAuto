package host;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import messageController.Message;

/**
 * Listens for and decodes the messages from the Message Controller to the host.
 * @author cms549
 */
public class HostMessageListener extends Thread {
	/**
	 * Host's unique employee id
	 */
	private long empID;
	
	/**
	 * Socket to listen on
	 */
	private Socket sock;
	/**
	 * Pointer back to host interface
	 */
	private HostInterface hi;
	/**
	 * Used to convert java objects to string and vice versa
	 */
	private Gson gson;
	
	/**
	 * Constructor
	 * @param listener - socket this will be listening to
	 * @param empID - id of the host
	 * @param hI - host interface
	 */
	public HostMessageListener(Socket listener, long empID, HostInterface hI) {
		sock=listener;
		this.empID=empID;
		hi=hI;
		gson = new Gson();
	}

	/**
	 * Listens for messages sent from the MC
	 */
	public void run(){
		DataInputStream in = null;
		try {
			in = new DataInputStream(sock.getInputStream());
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			//send a message hempID to MC so they sign you in
			String logInToMC = "h"+empID;
			out.writeUTF(logInToMC);
			out.close();
			//just keep listening
			while(true){
				String mes = in.readUTF();
				Message m = gson.fromJson(mes,Message.class);
				decodeMessage(m);
			}
			
			
		}catch (Exception e) {
			System.out.println("Host Listener disconnected from MC.");
			try {
				sock.close();
			} catch (IOException e1) {			}
		} 
		
		
	}

	/**
	 * Decodes message
	 * If manager sent message it must be a notification
	 * If server sent message it must be a log in, log out, or paid message
	 * @param m - message to decode
	 */
	private void decodeMessage(Message m) {
		char senderPos = m.senderPosition;
		if(senderPos=='m'){
			//NOTIFY
			hi.addNotification(m.content);
		}
		else if(senderPos=='w'){
			if(m.content.charAt(0)=='L'){
				//logging in
				long waiterID = m.senderEmpID;
				String name = m.content.substring(1); 
				//add to list of servers
			}
			if(m.content.charAt(0)=='O'){
				//logging out
				long waiterID = m.senderEmpID;
				String name = m.content.substring(1); 
				//remove from list of servers
			}
			else{
				//PAID
				String tNumStr = m.content;
				int tableNumber = Integer.parseInt(tNumStr);
				hi.paid(tableNumber);
			}
		}
		
	}
	
}
