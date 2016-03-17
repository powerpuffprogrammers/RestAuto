package databaseB;

//LAST CODED BY: CHRISTINA SEGERHOLM ON 3/16

import java.util.ArrayList;

public class Ticket {
	//table number this order belongs to
	public int tableNumber;
	//name of waiter this ticket is under
	public long waiterID;
	//status of ticket: u=unstarted, s=semi started, S=started, f=finished
	public char status;
	//List of dishes on the ticket
	public ArrayList<Dish> listOfDishes;
	
	//unique id of ticket
	public long ticketNumber;
	
	//total price of ticket
	public double price;
	
	//Counters used to figure out the status of the ticket
	public int amountOfDishesUnstarted;
	public int amountOfDishesStarted;
	public int amountOfDishesFinished;
	public int amountOfDishes;
	
	public Ticket(int tableNum, long waiterID, ArrayList<Dish> listOfDishes){
		this.tableNumber=tableNum;
		this.waiterID= waiterID;
		this.status='u';
		this.listOfDishes= listOfDishes;
		amountOfDishes = listOfDishes.size();
		amountOfDishesUnstarted = listOfDishes.size();
		amountOfDishesStarted = 0;
		amountOfDishesFinished = 0;
		this.price=0;
	}
	
	public void addDishToTicket(Dish d){
		amountOfDishesUnstarted= amountOfDishesUnstarted+1;
		amountOfDishes=amountOfDishes+1;
		this.price=this.price + d.price;
		
	}
	
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
		return true;
	}
	
	
	
	/**
	 * Looks through the dishes of the ticket and updates the status of the ticket accordingly
	 * @return old status of ticket
	 */
	public char updateStatus(){
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
