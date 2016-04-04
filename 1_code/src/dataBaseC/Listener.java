package dataBaseC;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

/**
 * Listens to messages from one chef or waiter.
 * @author cms549
 */
public class Listener  extends Thread{

	/**
	 * Socket this listener will listen on
	 */
	private Socket currListener;
	
	/**
	 * Used to convert java objects to JSON format and vice versa.
	 */
	public Gson jsonConverter;
	
	
	/**
	 * Constructor 
	 * @param listener - socket to listen to
	 * @param sender - MessageControllerSender - this will add messages to this to send it
	 */
	public Listener(Socket listener) {
		currListener=listener;
		jsonConverter = new Gson();
	}
	

	/**
	 * Starts a new thread for the DB C controller so it can communicate with one tablet on one thread.
	 * Reads the socket in to determine the request and responds accordingly.
	 * Socket will hang up on waiters after sending them menu and after reading a ticket
	 * Socket won't hang up on chef since chef will be sending information about each dish that was started.
	 */
	public void run(){
		//MUST FIRST ADD WAITER OR CHEF SENDER TO THE LIST
		//then start listening 
		
		String mess = "";
		try(DataInputStream in = new DataInputStream(currListener.getInputStream()); 
				DataOutputStream out = new DataOutputStream(currListener.getOutputStream())) {
			mess =in.readUTF();
			char first = mess.charAt(0);
			if(first =='c'){
				//set up chef interface
				DatabaseCController.chefSender = new Sender(out, 'c');
				while(true){
					mess =in.readUTF();
					if(mess.length()<3){
						mess = mess+ in.readUTF();
					}
					first = mess.charAt(0);
					
					if(first=='A'){ //add ingredinet
						//add the ingredient with the info into the inventory
					}
					else if(first=='R'){//Remove this ingredinet from inventory
						
					}
					else if(first =='d'){//decrement the ingredients for this dish
						
						//if low inventory tell all waiters and chef
						//DataBaseCController.lowInventory();
					}
				}
				
				
			}
			else if(first=='M'){//Waiter needs menu when loggin in- hang up after you give them the menu
				DatabaseCController.waiterSenders.add(new Sender(out, 'w'));
				String jmenu = jsonConverter.toJson(DatabaseCController.menu);
				out.writeUTF(jmenu);
				while(true){
					mess =in.readUTF();
					if(mess.length()<3){
						mess = mess+ in.readUTF();
					}
					first = mess.charAt(0);
					if(first == 'T'){//waiter is sending you a paid ticket so you can save it - hang up after you read it
						String tick = mess.substring(1);
						//add the ticket to the file that holds all tickets for today
						DatabaseCController.recordTicket(tick);
					}
				}
			}
			
			
		} catch (Exception e) {
			try {
				//NEED A WAY TO REMOVE HIM FROM THE LIST!!!!!!!!!!!!
				currListener.close();
			} catch (IOException e1) {			}
			System.out.println("DBC Listener Closing: Before closing Read in: "+ mess);
			e.printStackTrace();
		} 
	}
	
	
}
