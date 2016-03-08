package interfaces;

import java.awt.Event;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import dataBaseC.Table;
import messageController.Message;

public class HostInterface {

	//Hashmaps link integer (table #) to its table object
	HashMap<Integer, Table> allTables;
    HashMap<Integer, Table> readyTables;
    HashMap<Integer, Table> notReadyTables;
    
    ArrayList<Notification> pendingNotifications;
    
	public HostInterface(){
			//stuff about logging in�.
			pendingNotifications = new ArrayList<Notification>();
			//allTables = getMapofTablesFromDataBase();
			readyTables = new HashMap<Integer, Table>();
			notReadyTables =  new HashMap<Integer, Table>();
			Iterator it = allTables.keySet().iterator();
			while( it.hasNext()){
			        Map.Entry<Integer,Table> entry= (Entry<Integer, Table>) it.next();
			        Table table = entry.getValue();
			        table.setStatus('r');
			        readyTables .put(entry.getKey(), table);
	
			}
	}
	
//handles all the host actions (like pushing buttons) and updates the screen and list of tables correctly
	public void hostEventListenter(Event e){
		//if event is a change status button
		
		//if event is closing a notification
		
		
		
		
		currTable = allTable[e.tableNumber];     // edit this to HashMap
		char oldStatus = currTable.status;
	    if(oldStatus==e.newstatus){
	        return;
	    }
	    if(oldStatus == ready){ //if the table was ready and is being changed
		    readyTables.remove(currTable);
		    notReadyTables.add(currTable);
	    }
		else{
			readyTables.remove(currTable);
		    notReadyTables.add(currTable);
		}
	    currTable.status = e.newtablestaus;        
	   //update the screen
		redrawHostScreen();
	}        


//handles messages that it gets from manager or waiter
	public void hostMessageListener(Message m){
		
		if(m.getSenderPosition() == 'w'){ //if waiter sent a message
			
		}            
		else if(m.getSenderPosition()== 'm'){//if manager sent the message = 
			    makeNotification(m.getContent());
		}
	
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
