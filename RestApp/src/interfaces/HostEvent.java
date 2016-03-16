package interfaces;

public class HostEvent {
	
	//Gives the type of event that host initiated
	//Changing status of table to ready = r
	//closing notification is = n
	//seating a table = s
	//notifying manager = m
	char type;
	
	//this is the id of the table or notification host is trying to modify
	int idOfTableNotification;
	
	//Waiter name is optional and is used for seating table events
	String waiterName;
	
	
	public HostEvent(char type, int id, String waiter) {
		this.type=type;
		this.idOfTableNotification = id;
		this.waiterName=waiter;
	}

}