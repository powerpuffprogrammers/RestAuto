package waiter;

import java.io.DataInputStream;
import java.net.Socket;

import dataBaseC.Ticket;
import messageController.Message;

/**
 * Used to listen from messages from the message controller and decode them.
 * @author cms549
 */
public class WaiterMessageListener extends Thread {
	
	/**
	 * Socket that this waiter will connect to.
	 */
	private Socket sock;
	
	/**
	 * Pointer back to its waiter interface
	 */
	private WaiterInterface wi;

	/**
	 * Constructor
	 * @param listener - socket to listen to
	 * @param empID - waiter's employee id
	 * @param wI - waiter interface
	 */
	public WaiterMessageListener(Socket listener, WaiterInterface wI) {
		sock=listener;
		wi=wI;
	}
	
	/**
	 * Listens for messages sent from the MC
	 */
	public void run(){
		DataInputStream in = null;
		try {
			in = new DataInputStream(sock.getInputStream());
			//just keep listening
			while(true){
				String mes = in.readUTF();
				if(mes.length()==2){
					String second = in.readUTF();
					mes = mes +second;
				}
				Message m = Message.fromString(mes);
				decodeMessage(m);
			}
			
			
		}catch (Exception e) {
			System.out.println("Waiter Message Listener disconnected from MC.");
		} 
		
	}

	/**
	 * If manager sent message it must be a notification
	 * if host sent message it must be a recently sat -> the content is the table number
	 * if chef sent message it must be hot food -> the content is the table number
	 * @param m
	 */
	private void decodeMessage(Message m) {
		char senderPos = m.senderPosition;
		if(senderPos=='m'){
			//NOTIFY
			wi.addNotification(m.content);
		}
		else if(senderPos=='h'){
			//RECENTLY SAT
			String tNumStr = m.content;
			int tableNumber = Integer.parseInt(tNumStr);
			Ticket t =wi.listOfTickets.get(tableNumber);
			t.recentlySat=true;
			wi.updateScreen();
		}
		else if(senderPos=='c'){
			//HOT FOOD
			String tNumStr = m.content;
			int tableNumber = Integer.parseInt(tNumStr);
			Ticket t =wi.listOfTickets.get(tableNumber);
			t.hotFood=true;
			wi.updateScreen();
		}
	}
	
}
