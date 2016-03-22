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
		char pos='2';
		Integer empID=0;
		try {
			DataInputStream in = new DataInputStream(currListener.getInputStream());
			//Add this guy to the list of listeners
			String logIn =in.readUTF();
			pos = logIn.charAt(0);
			empID = Integer.parseInt(logIn.substring(1));
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
			while(true){
				String message =in.readUTF();
				Message m = gson.fromJson(message, Message.class);
				//Add the forwarded message to message controller
				sender.pendingMessages.offer(m);
			}
			
			
		}catch (EOFException e) { 
			try {
				currListener.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}catch (Exception e) {
			System.out.println("Disconnected from a client.");
			
			e.printStackTrace();
		} 
		if(pos=='w'){
			MessageController.removeWaiterSocket(empID);
		}
		else if(pos=='c'){
			MessageController.removeChefSocket(empID);
		}
		else if(pos=='h'){
			MessageController.removeHostSocket(empID);
		}
		else if(pos=='m'){
			MessageController.removeManagerSocket(empID);
		}
		
	}
}
