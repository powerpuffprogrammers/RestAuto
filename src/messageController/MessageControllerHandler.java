package messageController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MessageControllerHandler  extends Thread{

	private Socket currListener;
	
	public MessageControllerHandler(Socket listener) {
		currListener=listener;
	}
	
	public void run(){
		try {
			DataInputStream in = new DataInputStream(currListener.getInputStream());
			DataOutputStream out = new DataOutputStream(currListener.getOutputStream());
			while(true){
				String mess =in.readUTF();
				char first = mess.charAt(0);
				if(first=='L'){ //handle one message
					MessageController.addWaiterSocket(currListener);
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("Disconnected from a client.");
			e.printStackTrace();
		} 
	}
}
