package interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;

import dataBaseC.Table;
import messageController.Message;
import messageController.RecieverInfo;
import messageController.SenderInfo;

public class HostInterface {

	//Hashmap links integer (table #) to its table object - holds all the tables in the restaurant
	HashMap<Integer, Table> allTables; 
	
	//list of all the ready tables in order they will be displayed
	ArrayList< Integer> readyTables; 
	//list of tables that are either seated 
	ArrayList< Integer> seatedTables;
	//list of tables that need to be cleaned
	ArrayList< Integer> paidTables;
	
	
	//Array list of notifications that the host screen still has to display
	 ArrayList<Notification> pendingNotifications;

    
	public HostInterface(JFrame frame){
		//Code about logging in�.
		pendingNotifications = new ArrayList<Notification>();
		//allTables = getMapofTablesFromDataBase();
		readyTables = new ArrayList<Integer>();
		seatedTables = new ArrayList<Integer>();
		paidTables = new ArrayList<Integer>();
		
		Iterator<Integer> it = allTables.keySet().iterator();
		while( it.hasNext()){
			Integer key= it.next();
			Table table = allTables.get(key);
			table.changeStatus('r');
			readyTables.add(key);
		}
	}
	
	//handles all the host actions (like pushing buttons) and updates the screen and list of tables correctly
	public void hostEventListenter(HostEvent e){
		//if event is a seating a table
		if(e.type == 's'){
			Table t = allTables.get(e.idOfTableNotification);
			if(t==null){
				return;
			}
			t.seat(e.waiterName);
			readyTables.remove(readyTables.indexOf(e.idOfTableNotification));
			seatedTables.add(e.idOfTableNotification);
		}
		//changing status to ready
		else if(e.type == 'r'){
			Table t = allTables.get(e.idOfTableNotification);
			if(t==null){
				return;
			}
			t.changeStatus('r');
			paidTables.remove(paidTables.indexOf(e.idOfTableNotification));
			readyTables.add(e.idOfTableNotification);
		}
		else if(e.type == 'n'){//if event is closing a notification
			pendingNotifications.remove(0);
		}
		//if event is sending a notification to manager
		else if(e.type == 'm'){
			hostMessageSender(new Message(new SenderInfo('h'), new RecieverInfo('m'), "Host Stand needs Assistance."));
		}
		
		redrawHostScreen();
		
	}        

	//sends message to message sender
	private void hostMessageSender(Message message) {
		// TODO Auto-generated method stub
		
	}

	//handles messages that it gets from manager or waiter
	public void hostMessageListener(Message m){	
		if(m.getSenderPosition() == 'w'){ //if waiter sent a message
			String content = m.getContent();
			//looks through the content of the message to get the table number
			Integer tableNum = getTableNumberFromMessage(content);
			if(tableNum!=null){
				Table currTab =allTables.get(tableNum);
				if(currTab!=null){
					//if that table is seated
					if(currTab.status == 's'){
						//change status to paid
						currTab.changeStatus('p');
						seatedTables.remove(seatedTables.indexOf(tableNum));
						paidTables.add(tableNum);
						
					}
				}		
			}
		}            
		else if(m.getSenderPosition()== 'm'){//if manager sent the message = 
		    makeNotification(m.getContent());
		}
		redrawHostScreen();
	}


	private Integer getTableNumberFromMessage(String content) {
		// TODO Auto-generated method stub
		return null;
	}

	//create a notification on the hosts screen with this content
	private void makeNotification(String content) {
		pendingNotifications.add(new Notification(content));
	}
	

	//Draws the screen using current lists and notifications
	private void redrawHostScreen() {
		// TODO Auto-generated method stub
		
	}
	
	
}
