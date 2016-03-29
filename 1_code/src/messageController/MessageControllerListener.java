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
	 * Socket this controller will listen to
	 */
	private Socket currListener;
	
	/**
	 * Constructor 
	 * @param listener - socket to listen to
	 * @param sender - MessageControllerSender - this will add messages to this to send it
	 */
	public MessageControllerListener(Socket listener) {
		currListener=listener;
	}
	
	/**
	 * Starts listening to messages 
	 */
	public void run(){
		try {
			DataInputStream in = new DataInputStream(currListener.getInputStream());
			
			String message =in.readUTF();
			String second = in.readUTF();
			System.out.println("Message = "+ message+second);
			Message m = Message.fromString(message+second);
			char pos = m.receiverPosition;
			if(pos=='L'){//logging in
				pos = m.senderPosition;
				long empID = m.senderEmpID;
				if(pos=='w'){
					MessageController.addWaiterSocket(empID, currListener, in);
				}
				else if(pos=='c'){
					MessageController.addChefSocket(empID, currListener,in);
				}
				else if(pos=='h'){
					MessageController.addHostSocket(empID, currListener,in);
				}
				else if(pos=='m'){
					MessageController.addManagerSocket(empID, currListener, in);
				}
				else{
					currListener.close();
					return;
				}
			}
			
			
			else{
				//Add the forwarded message to message controller
				sender = MessageController.getSocket(pos, senderid);
				sender.pendingMessages.offer(m);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
