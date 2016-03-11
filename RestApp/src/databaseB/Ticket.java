package databaseB;

//LAST CODED BY: CHRISTINA SEGERHOLM ON 2/24

import java.util.ArrayList;

public class Ticket {
	//table number this order belongs to
	private int tableNumber;
	//name of waiter this ticket is under
	private String waiter;
	//status of ticket: u=unstarted, s=semi started, S=started, f=finished
	private char status;
	//List of dishes on the ticket
	private ArrayList<Dish> listOfDishes;
	
	//unique id of ticket
	public long ticketNumber;
	
	//total price of ticket
	public double price;
	
	//Counters used to figure out the status of the ticket
	public int amountOfDishesUnstarted;
	public int amountOfDishesStarted;
	public int amountOfDishesFinished;
	public int amountOfDishes;
	
	public Ticket(int tableNum, String waiterName, ArrayList<Dish> listOfDishes){
		this.tableNumber=tableNum;
		this.waiter= waiterName;
		this.status='u';
		this.listOfDishes= listOfDishes;
		amountOfDishes = listOfDishes.size();
		amountOfDishesUnstarted = listOfDishes.size();
		amountOfDishesStarted = 0;
		amountOfDishesFinished = 0;
		this.price=0;
	}
	
	/**
	 * Looks through the dishes of the ticket and updates the status of the ticket accordingly
	 * @return old status of ticket
	 */
	public char updateStatus(){
		//update the status of the ticket using the statuses of all the dishes
		char oldstatus = status;
		//change status here
		
		
		return oldstatus;
	}

	public char getStatus() {
		// TODO Auto-generated method stub
		return status;
	}
	
	//GETTERS
}
