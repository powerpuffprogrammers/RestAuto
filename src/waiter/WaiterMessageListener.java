package waiter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import messageController.Message;


public class WaiterMessageListener extends Thread {
	
	private long empID;
	private Socket sock;
	private WaiterInterface wi;
	private Gson gson;
	
	public WaiterMessageListener(Socket listener, long empID, WaiterInterface wI) {
		sock=listener;
		this.empID=empID;
		wi=wI;
		gson= new Gson();
	}
	
	public void run(){
		try {
			DataInputStream in = new DataInputStream(sock.getInputStream());
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			//send a message wempID to MC so they sign you in
			String logInToMC = "w"+empID;
			out.writeUTF(logInToMC);
			out.close();
			//just keep listening
			while(true){
				String mes = in.readUTF();
				Message m = gson.fromJson(mes,Message.class);
				decodeMessage(m);
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

	private void decodeMessage(Message m) {
		
		
	}
	
}
