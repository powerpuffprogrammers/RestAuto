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
	
	public Ticket(int tableNum, String waiterName, ArrayList<Dish> listOfDishes){
		this.tableNumber=tableNum;
		this.waiter= waiterName;
		this.status='u';
		this.listOfDishes= listOfDishes;
	}
	
	public void updateStatus(){
		//update the status of the ticket using the statuses of all the dishes
	}
	
	//GETTERS
}