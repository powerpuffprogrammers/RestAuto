package chef;

import databaseB.Dish;

public class ChefEvent {
	
	/**
	's' = change status
	'b' = back
	'm' = notify manager
	'o' = opening ticket 
	 */
	 char type;
	
	//can be u=unstarted s = started r = ready
	char newStatusOfDish;
	
	//table number of the ticket
	long ticketNumber;
	
	//dish and status
	Dish dish;
	
}
