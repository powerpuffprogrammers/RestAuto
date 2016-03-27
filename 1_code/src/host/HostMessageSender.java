package host;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


import messageController.Message;

public class HostMessageSender {

	/**
	 * Host's unique employee id
	 */
	private long empID;
	
	/**
	 * Socket to listen on
	 */
	private Socket sock;

	
	/**
	 * Constructor
	 * @param listener - socket to use
	 * @param empID - host's employee id
	 */
	public HostMessageSender(Socket listener, long empID) {
		sock=listener;
		this.empID=empID;
	}

	/**
	 * Sends a message to message controller to be forwarded.
	 * Automatically fills in sender info with host position and employee id.
	 * @param m
	 */
	public void sendMessage(Message m) {
		try {
			m.senderPosition='h';
			m.senderEmpID=empID;
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			String mess = m.toString();
			out.writeUTF(mess);
			out.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
	}

}
