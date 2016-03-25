package dataBaseC;

import java.util.ArrayList;

/**
 * Data structure to hold the ticket and the data associated with it.
 * Used by waiter and chef.
 * @author cms549
 */
public class Ticket {
	/**
	 * table number this order belongs to
	 */
	public int tableNumber;
	/**
	 * name of waiter this ticket is under
	 */
	public String waiterName;
	/**
	 * id of waiter this ticket belongs to
	 */
	public long waiterID;
	/**
	 * status of ticket: u=unstarted, s=semi started, S=started, f=finished
	 */
	public char status;
	/**
	 * List of dishes on the ticket
	 */
	public ArrayList<Dish> listOfDishes;
	
	/**
	 * unique id of ticket
	 */
	public long ticketNumber;
	
	/**
	 * total price of ticket
	 */
	public double price;
	
	//Counters used to figure out the status of the ticket
	public int amountOfDishesUnstarted;
	public int amountOfDishesStarted;
	public int amountOfDishesFinished;
	public int amountOfDishes;
	
	/**
	 * Used to keep track of recently sat tables for waiters
	 */
	public boolean recentlySat;
	
	/**
	 * Used to keep track of hot food tables for waiters
	 */
	public boolean hotFood;
	
	/**
	 * Creates a new empty ticket with the following
	 * @param waiterName - name of waiter for this ticket
	 * @param tableNum - table number the ticket is under
	 * @param waiterID - id of the waiter
	 */
	public Ticket(String waiterName,int tableNum, long waiterID){
		this.waiterName = waiterName;
		this.tableNumber=tableNum;
		this.waiterID= waiterID;
		this.status='u';
		this.listOfDishes= new ArrayList<Dish>();
		amountOfDishes = 0;
		amountOfDishesUnstarted = 0;
		this.price=0;
		amountOfDishesStarted = 0;
		amountOfDishesFinished = 0;
	}
	
	/**
	 * Adds dish to ticket and also updates price and status of ticket
	 * @param d
	 */
	public void addDishToTicket(Dish d){
		listOfDishes.add(d);
		amountOfDishesUnstarted= amountOfDishesUnstarted+1;
		amountOfDishes=amountOfDishes+1;
		this.price=this.price + d.price;
		updateStatusOfTicket();
		
	}
	
	/**
	 * Removes the dish at index i from the ticket and decrements the price
	 * @param indexOfDishInTickList
	 * @return
	 */
	public boolean removeDishFromTicket(int indexOfDishInTickList){
		if(indexOfDishInTickList<0 || indexOfDishInTickList>amountOfDishes){
			return false;
		}
		
		Dish del =listOfDishes.get(indexOfDishInTickList);
		if(del==null){
			return false;
		}
		this.price=this.price - del.price;
		amountOfDishes= amountOfDishes-1;
		char s = del.getStatus();
		if(s=='u'){
			amountOfDishesUnstarted= amountOfDishesUnstarted-1;
		}
		else if(s=='s'){
			amountOfDishesStarted= 	amountOfDishesStarted-1;
		}
		else{
			amountOfDishesFinished=amountOfDishesFinished-1;
		}
		listOfDishes.remove(indexOfDishInTickList);
		updateStatusOfTicket();
		return true;
	}
	
	/**
	 * Looks through the dishes of the ticket and updates the status of the ticket accordingly
	 * @return old status of ticket
	 */
	public char updateStatusOfTicket(){
		//update the status of the ticket using the statuses of all the dishes
		char oldstatus = status;
		//change status here
		if(amountOfDishesUnstarted == amountOfDishes){
			status='u';//unstarted
		}
		else if(amountOfDishesUnstarted>0){
			status='s';//semi started
		}
		else if(amountOfDishesFinished==amountOfDishes){
			status='f';//finished
		}
		else{
			status='S';//started
		}
		return oldstatus;
	}

}
