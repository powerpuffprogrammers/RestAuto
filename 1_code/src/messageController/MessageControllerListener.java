package messageController;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

public class MessageControllerListener  extends Thread{

	private Gson gson;
	private Socket currListener;
	private MessageControllerSender sender;
	
	public MessageControllerListener(Socket listener, MessageControllerSender sender) {
		gson = new Gson();
		currListener=listener;
		this.sender=sender;
	}
	
	public void run(){
		try {
			DataInputStream in = new DataInputStream(currListener.getInputStream());
			
			String message =in.readUTF();
			Message m = gson.fromJson(message, Message.class);
			char pos = m.receiverInfo.position;
			if(pos=='L'){//logging in
				pos = m.senderInfo.position;
				long empID = m.senderInfo.empID;
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
