package dataBaseC;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Sends messages to a particular tablet connected to the message controller.
 * @author cms549
 */
public class Sender extends Thread {
	
	/**
	 * DataOutputStream this controller will send to
	 */
	private DataOutputStream currSender;
	
	/**
	 * list of messages to send
	 */
	ConcurrentLinkedQueue<String> pendingMessages;
	
	/**
	 * Employee position of employee communicating with this socket
	 */
	private char empPos;
	
	
	/**
	 * Constructor
	 * @param oneTablet - socket sender will be sending messages on
	 * @param empId - unique id of employee this socket sends messages to
	 * @param empPos - position of employee this socket sends messages to
	 */
	public Sender(DataOutputStream oneTablet, char empPos){
		pendingMessages = new ConcurrentLinkedQueue<String>();
		currSender = oneTablet;
		this.empPos = empPos;
	}
	
	/**
	 * Starts sending messages in pendingMessage.
	 */
	public void run(){
		while(true){
			Message m =pendingMessages.poll();
			if(m!=null){
				try {
					out.writeUTF(m.toString());
				} catch (IOException e) {
					System.out.println("Messsage Controller for Pos = "+ empPos+" shutting down.");
					return;
				}
				
			}
		}
	}
}
