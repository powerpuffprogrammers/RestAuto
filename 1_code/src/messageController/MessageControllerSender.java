package messageController;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.Gson;

/**
 * Sends messages to all tablets connected to the message controller.
 * @author cms549
 */
public class MessageControllerSender extends Thread {
	
	/**
	 * Converts java objects to string and vice versa
	 */
	private Gson gson;
	
	/**
	 * list of messages to send
	 */
	ConcurrentLinkedQueue<Message> pendingMessages;
	
	/**
	 * Constructor
	 */
	public MessageControllerSender(){
		pendingMessages = new ConcurrentLinkedQueue<Message>();
		gson = new Gson();
	}
	
	/**
	 * Starts sending messages in pendingMessage.
	 */
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
				else if(pos=='X'){//logging out
					pos = m.senderInfo.position;
					long empID = m.senderInfo.empID;
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
