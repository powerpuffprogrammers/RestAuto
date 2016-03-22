package waiter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import databaseB.Ticket;
import messageController.Message;


public class WaiterMessageListener extends Thread {
	
	private long empID;
	private Socket sock;
	private WaiterInterface wi;
	private Gson gson;
	/**
	 * Constructor
	 * @param listener
	 * @param empID
	 * @param wI
	 */
	public WaiterMessageListener(Socket listener, long empID, WaiterInterface wI) {
		sock=listener;
		this.empID=empID;
		wi=wI;
		gson= new Gson();
	}
	/**
	 * Listens for messages sent from the MC
	 */
	public void run(){
		DataInputStream in = null;
		try {
			in = new DataInputStream(sock.getInputStream());
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
			}
		}catch (Exception e) {
			System.out.println("Waiter Listener disconnected from MC.");
		} 
		
		
	}

	/**
	 * If manager sent message it must be a notification
	 * if host sent message it must be a recently sat -> the content is the table number
	 * if chef sent message it must be hot food -> the content is the table number
	 * if none of above then it must be low inventory -> content is list of dish names separated by commas	
	 * @param m
	 */
	private void decodeMessage(Message m) {
		char senderPos = m.senderInfo.position;
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
		else{
			//LOW INVENTORY
			String arrListOfDishes = m.content;
			String[] dishes= arrListOfDishes.split(",");
			wi.removeLowInventoryDishes(dishes);
		}
		
	}
	
}
