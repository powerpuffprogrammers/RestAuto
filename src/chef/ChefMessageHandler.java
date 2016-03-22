package chef;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import configuration.Configure;


public class ChefMessageHandler extends Thread {
	
	private long empID;
	private Socket sock;
	private ChefInterface ci;
	
	public ChefMessageHandler(Socket listener, long empID, ChefInterface wI) {
		sock=listener;
		this.empID=empID;
		ci=wI;
	}
	
	public void run(){
		try {
			DataInputStream in = new DataInputStream(sock.getInputStream());
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			//send a message cempID to MC so they sign you in
			String logInToMC = "c"+empID;
			out.writeUTF(logInToMC);
			
			//just keep listening
			while(true){
				//if message = ticket
				//ci.addTicketToQ();
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
