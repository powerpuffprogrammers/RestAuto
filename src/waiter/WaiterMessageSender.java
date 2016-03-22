package waiter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import messageController.Message;

public class WaiterMessageSender extends Thread {

	private long empID;
	private Socket sock;
	private Gson gson;
	
	public WaiterMessageSender(Socket listener, long empID) {
		sock=listener;
		this.empID=empID;
		gson= new Gson();
	}

	public void sendMessage(Message m){
		
		try {
			m.senderInfo.position='w';
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
