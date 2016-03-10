package interfaces;

import dataBaseC.Table;

public class WaiterInterface {

	//current table open
	Table currTable;
	
	public void waiterEventListener ( WaiterEvent e ){
		//An event should be generated each time the waiter adds or removes a item from the ticket
		    if(e.type =='a' ){ //if you are adding a dish
		            double p = getPrice(e.dish, currTab);
		            if(p==-1){
		                    handleError();
		            }
		        currTicket.price = currTicket.price + p;
		    }
		    else if(event.type== "removeItemButtonPressed"){
		        double p = getPrice(event.dish, event.dishType );
		        if(p==-1){
		            handleError();
		        }
		        currTicket.price = currTicket.price - p;
		    }
		    
		}

}
