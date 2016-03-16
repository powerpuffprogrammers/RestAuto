package interfaces;

import java.util.HashMap;

import javax.swing.JFrame;

import databaseB.DishData;
import databaseB.Ticket;

public class WaiterInterface {

	public boolean loggedOut;
	/**
	 * current ticket open, null if none is open
	 */
	Ticket currTicket;
	
	//Appetizer, Drink, Entree, Desert
	String currDishType;
	
	HashMap<Integer, Ticket> listOfTickets;
	
	HashMap<String,HashMap<String,DishData>>menu;
	
	public WaiterInterface(JFrame frame) {
		loggedOut=false;
		
		//create waiter panel
		WaiterPanel waiterPanel = new WaiterPanel();
		//set the screen to the waiter panel
		frame.setContentPane(waiterPanel);
		
		//Don't return until i logged out
		while(!loggedOut){}
	}

	//An event should be generated each time the waiter clicks something on his screen
	public void waiterEventListener ( WaiterEvent e ){
		
		if(e.type =='m' ){
			//notify manager problem with table
		}
		else if(e.type=='b'){
			//go back to previous screen
		}
		else if(e.type=='p'){
			//send ticket to printer
		}
		else if(e.type=='s'){
			//send ticket to chef
		}
		else if(e.type=='c'){
			//send message to host that you are paid
		}
		else if(e.type =='a' ){ //if you are adding a dish
	            double p = getPriceOfDish(e.dishName, currDishType);
	            if(p>=0){
	            	 currTicket.price = currTicket.price + p;
	            	 addDishToTicket(e.dishName, currDishType);
	            }
	    }
	    else if(e.type== 'r'){
	        double p = getPriceOfDish(e.dishName, e.dishType);
	        if(p>=0){
	        	 currTicket.price = currTicket.price -p;
	        	 removeDishFromTicket(e.dishName, e.dishType);
	        }
	    }
		redrawScreen();
	}

	private void redrawScreen() {
		// TODO Auto-generated method stub
		
	}

	private void addDishToTicket(String dishName, String currDishType2) {
		// TODO Auto-generated method stub
		
	}

	private void removeDishFromTicket(String dishName, String dishType) {
		// TODO Auto-generated method stub
		
	}

	private double getPriceOfDish(String dish, String dishType) {
		DishData d =menu.get(dishType).get(dish);
		
		return d.price;
	}

}