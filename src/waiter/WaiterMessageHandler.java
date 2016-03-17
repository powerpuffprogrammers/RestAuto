package waiter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;


public class WaiterMessageHandler extends Thread {
	
	private long empID;
	private Socket sock;
	private WaiterInterface wi;
	
	public WaiterMessageHandler(Socket listener, long empID, WaiterInterface wI) {
		sock=listener;
		this.empID=empID;
		wi=wI;
	}
	
	public void run(){
		try {
			DataInputStream in = new DataInputStream(sock.getInputStream());
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			//send a message wempID to MC so they sign you in
			String logInToMC = "w"+empID;
			out.writeUTF(logInToMC);
			
			//just keep listening
			while(true){
				//if message = hot food
				//wi.notify("HotFood");
			}
			
			
		}catch (EOFException e) { 
			try {
				sock.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}catch (Exception e) {
			System.out.println("Disconnected from a client.");
			
			e.printStackTrace();
		} 
		
		
	}
	
}
