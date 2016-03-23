package chef;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import messageController.Message;

public class ChefMessageSender {

	private long empID;
	private Socket sock;
	private Gson gson;
	
	public ChefMessageSender(Socket listener, long empID) {
		sock=listener;
		this.empID=empID;
		gson= new Gson();
	}

	public void sendMessage(Message m) {
		try {
			m.senderInfo.position='c';
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
