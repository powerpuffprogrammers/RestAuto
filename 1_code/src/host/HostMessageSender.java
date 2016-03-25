package host;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

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
	 * Used to convert java objects to string and vice versa
	 */
	private Gson gson;
	
	/**
	 * Constructor
	 * @param listener - socket to use
	 * @param empID - host's employee id
	 */
	public HostMessageSender(Socket listener, long empID) {
		sock=listener;
		this.empID=empID;
		gson= new Gson();
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
			String mess = gson.toJson(m);
			out.writeUTF(mess);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
