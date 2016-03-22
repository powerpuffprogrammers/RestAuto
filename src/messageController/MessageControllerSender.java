package messageController;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.Gson;

public class MessageControllerSender extends Thread {
	
	private Gson gson;
	
	//list of messages to send
	ConcurrentLinkedQueue<Message> pendingMessages;
	
	public MessageControllerSender(){
		pendingMessages = new ConcurrentLinkedQueue<Message>();
	}
	
	public void run(){
		while(true){
			Message m =pendingMessages.poll();
			if(m!=null){
				char pos= m.receiverInfo.position;
				long id = m.receiverInfo.empID;
				Socket sock=null;
				if(pos=='h'){
					sock=MessageController.hosts.values().iterator().next();
				}
				else if(pos=='m'){
					sock=MessageController.managers.values().iterator().next();
				}
				else if(pos=='c'){
					sock=MessageController.chefs.values().iterator().next();
				}
				else if(pos=='w'){
					//send to all waiters
					if(id==-1){
						Iterator<Socket> it = MessageController.waiters.values().iterator();
						while(it.hasNext()){
							sock = it.next();
							try {
								DataOutputStream out = new DataOutputStream(sock.getOutputStream());
								out.writeUTF(gson.toJson(m));
								out.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						continue;
					}
					else{
						sock=MessageController.waiters.get(id);
					}
				}
				if(sock==null){
					continue;
				}
				try {
					DataOutputStream out = new DataOutputStream(sock.getOutputStream());
					out.writeUTF(gson.toJson(m));
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
}
