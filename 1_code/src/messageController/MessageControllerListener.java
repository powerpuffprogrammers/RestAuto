package messageController;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

/**
 * Listens to messages from all tablets.
 * @author cms549
 */
public class MessageControllerListener  extends Thread{

	/**
	 * Converts java objects to string and vice versa
	 */
	private Gson gson;
	/**
	 * Socket this controller will listen to
	 */
	private Socket currListener;
	
	private MessageControllerSender sender;
	
	/**
	 * Constructor 
	 * @param listener - socket to listen to
	 * @param sender - MessageControllerSender - this will add messages to this to send it
	 */
	public MessageControllerListener(Socket listener, MessageControllerSender sender) {
		gson = new Gson();
		currListener=listener;
		this.sender=sender;
	}
	
	/**
	 * Starts listening to messages 
	 */
	public void run(){
		try {
			DataInputStream in = new DataInputStream(currListener.getInputStream());
			
			String message =in.readUTF();
			System.out.println("Message = "+ message);
			Message m = gson.fromJson(message, Message.class);
			char pos = m.receiverPosition;
			if(pos=='L'){//logging in
				pos = m.senderPosition;
				long empID = m.senderEmpID;
				if(pos=='w'){
					MessageController.addWaiterSocket(empID, currListener);
				}
				else if(pos=='c'){
					MessageController.addChefSocket(empID, currListener);
				}
				else if(pos=='h'){
					MessageController.addHostSocket(empID, currListener);
				}
				else if(pos=='m'){
					MessageController.addManagerSocket(empID, currListener);
				}
				else{
					currListener.close();
					return;
				}
			}
			
			
			else{
				//Add the forwarded message to message controller
				sender.pendingMessages.offer(m);
			}
			
			
		}catch (EOFException e) { 
			try {
				currListener.close();
			} catch (IOException e1) {
			}
			
		}catch (Exception e) {
			System.out.println("Disconnected from a client.");
			
			e.printStackTrace();
		} 
		
	}
}
