package dataBaseC;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
	 * Employee position of employee communicating with this socket
	 */
	private char empPos;
	
	/**
	 * Constructor 
	 * @param listener - socket to listen to
	 * @param sender - MessageControllerSender - this will add messages to this to send it
	 */
	public Listener(Socket listener) {
		currListener=listener;
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
			while(true){
				mess =in.readUTF();
				char first = mess.charAt(0);
				if(first=='A'){ //add ingredinet
					//add the ingredient with the info into the inventory
				}
				else if(first=='R'){//Remove this ingredinet from inventory
					
				}
				else if(first =='d'){//decrement the ingredients for this dish
					
				}
				else if(first=='M'){//Waiter needs menu when loggin in- hang up after you give them the menu
					String jmenu = jsonConverter.toJson(menu);
					out.writeUTF(jmenu);
					in.close();
					out.close();
					currListener.close();
					break;
				}
				else if(first == 'T'){//waiter is sending you a paid ticket so you can save it - hang up after you read it
					String ticket = mess.substring(1);
					//add the ticket to the file that holds all tickets for today
					in.close();
					out.close();
					currListener.close();
					break;
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("Before error Read in: "+ mess);
			e.printStackTrace();
		} 
	}
	
	
}
