package testing;

import dataBaseC.Dish;
import dataBaseC.Ticket;

/**
 * 
 * @author cms549
 */
public class TicketToMessageConversion {
	
	
	
	/**
	 * Make a new ticket and send it into string then get it back from string
	 * Make sure the names, comments, waiter names, priority, table number, waiter ids all match
	 * @param args
	 */
	public static void main(String args[]){
		//to do
		Ticket t = new Ticket("Bob", 13, 4);
		t.priority = true;
		t.addDishToTicket(new Dish("Chicken", 10, null));
		t.listOfDishes.get(0).comments.add("To Go");
		String st = t.toStringForChef();
		System.out.println(st);
		
		Ticket t2 = Ticket.fromString(st);
		if(t.waiterID == t2.waiterID){//and everything else matches
			System.out.println("Ticket Passed");
		}

	}
}

