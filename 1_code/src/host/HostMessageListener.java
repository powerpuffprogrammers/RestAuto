package host;

import java.io.DataInputStream;
import java.net.Socket;

import messageController.Message;

/**
 * Listens for and decodes the messages from the Message Controller to the host.
 * @author cms549
 */
public class HostMessageListener extends Thread {

	
	/**
	 * Socket to listen on
	 */
	private Socket sock;
	/**
	 * Pointer back to host interface
	 */
	private HostInterface hi;

	
	/**
	 * Constructor
	 * @param listener - socket this will be listening to
	 * @param hI - host interface
	 */
	public HostMessageListener(Socket listener, HostInterface hI) {
		sock=listener;
		hi=hI;
	}

	/**
	 * Listens for messages sent from the MC
	 */
	public void run(){
		DataInputStream in = null;
		try {
			in = new DataInputStream(sock.getInputStream());
			//just keep listening
			while(true){
				String mes = in.readUTF();
				if(mes.length()==2){
					String second = in.readUTF();
					mes = mes +second;
				}
				Message m = Message.fromString(mes);
				decodeMessage(m);
			}
			
			
		}catch (Exception e) {
			System.out.println("Host Message Listener disconnected from MC.");
		} 
		
	}

	/**
	 * Decodes message
	 * If manager sent message it must be a notification
	 * If server sent message it must be a paid message
	 * @param m - message to decode
	 */
	private void decodeMessage(Message m) {
		char senderPos = m.senderPosition;
		if(senderPos=='m'){
			//NOTIFY
			hi.addNotification(m.content);
		}
		else if(senderPos=='w'){
			//PAID
			String tNumStr = m.content;
			int tableNumber = Integer.parseInt(tNumStr);
			hi.paid(tableNumber);
			
		}
		
	}
	
}
