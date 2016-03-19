package interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;

import dataBaseC.Table;
import messageController.Message;
import messageController.SenderInfo;

public class HostInterface {

	long empID;
	String name;
	
	//Hashmap links integer (table #) to its table object - holds all the tables in the restaurant
	HashMap<Integer, Table> allTables; 
	
	//list of all the ready tables in order they will be displayed
	ArrayList< Integer> readyTables; 
	//list of tables that are either seated 
	ArrayList< Integer> seatedTables;
	//list of tables that need to be cleaned
	ArrayList< Integer> paidTables;
	
	


    
	public HostInterface(JFrame frame, long eID, String empName){
		name=empName;
		empID = eID;
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

	/*
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
			//pendingNotifications.remove(0);
		}
		//if event is sending a notification to manager
		else if(e.type == 'm'){
			hostMessageSender(new Message(new SenderInfo('h'), new SenderInfo('m'), "Host Stand needs Assistance."));
		}
		
	}        
*/


	//handles messages that it gets from manager or waiter
	public void hostMessageListener(Message m){	
		if(m.senderInfo.position == 'w'){ //if waiter sent a message
			String content = m.content;
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
		else if(m.senderInfo.position== 'm'){//if manager sent the message = 
		    //makeNotification(m.content);
		}
	}


	private Integer getTableNumberFromMessage(String content) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
}
