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
    
    //Array list of notifications that the host screen still has to display
    ArrayList<Notification> pendingNotifications;
    
	public HostInterface(){
			//Code about logging in�.
			pendingNotifications = new ArrayList<Notification>();
			//allTables = getMapofTablesFromDataBase();
			readyTables = new HashMap<Integer, Table>();
			notReadyTables =  new HashMap<Integer, Table>();
			Iterator<Integer> it = allTables.keySet().iterator();
			while( it.hasNext()){
			        Integer key= it.next();
			        Table table = allTables.get(key);
			        table.setStatus('r');
			        readyTables .put(key, table);
	
			}
	}
	
//handles all the host actions (like pushing buttons) and updates the screen and list of tables correctly
	public void hostEventListenter(Event e){
		//if event is a change status button
		
		//if event is closing a notification
		
		
		
		/*
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
		*/
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
					if(currTab.getStatus() == 's'){
						//change status to on check
						currTab.setStatus('o');
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
