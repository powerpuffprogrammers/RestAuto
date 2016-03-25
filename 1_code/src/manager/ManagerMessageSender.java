package manager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import messageController.Message;

/**
 * Used to Send messages to the message controller to be forwarded to the correct employee.
 * @author cms549
 */
public class ManagerMessageSender extends Thread {

	/**
	 * Manager's employee id
	 */
	private long empID;
	
	/**
	 * Socket that this waiter will connect to.
	 */
	private Socket sock;
	
	/**
	 * Used to convert java objects to string and vice versa
	 */
	private Gson gson;
	
	/**
	 * Constructor
	 * @param listener - socket to be used
	 * @param empID - manager's employee id
	 */
	public ManagerMessageSender(Socket listener, long empID) {
		sock=listener;
		this.empID=empID;
		gson= new Gson();
	}

	/**
	 * Sends message out to Message controller
	 * Automatically will fill in senderInfo with manager's position and id.
	 * @param m - message to send
	 */
	public void sendMessage(Message m){
		
		try {
			m.senderPosition='m';
			m.senderEmpID=empID;
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			String mess = gson.toJson(m);
			out.writeUTF(mess);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
