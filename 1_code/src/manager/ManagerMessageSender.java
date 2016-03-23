package manager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import messageController.Message;

public class ManagerMessageSender extends Thread {

	private long empID;
	private Socket sock;
	private Gson gson;
	
	public ManagerMessageSender(Socket listener, long empID) {
		sock=listener;
		this.empID=empID;
		gson= new Gson();
	}

	public void sendMessage(Message m){
		
		try {
			m.senderInfo.position='m';
			m.senderInfo.empID=empID;
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			String mess = gson.toJson(m);
			out.writeUTF(mess);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
