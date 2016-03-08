package databaseB;

//LAST CODED BY: CHRISTINA SEGERHOLM ON 2/24

//This guy will take care of calling decrementing
public class Dish {
	//Status of dish = u=unstarted, s=started, f=finished
	private char status;
	//Points to the dish data object that holds the name of the object and ingredients of that dish
	private DishData dishData;
	
	public Dish(DishData dishData){
		status='u';
		this.dishData= dishData;
	}
	
	//returns -2 if failed to change status, returns 0 on success, 1 if low inventory
	//whoever calls me must update the head chefs screen after
	public int changeStatus(char newStatus){
		if(status == 'u' && newStatus=='s'){
			status = newStatus;
			int k=dishData.decrementInventory();
			//decrement each ingredient
			return k;
			//whoever called me should check on the threshold next
		}
		if(status == 's' && newStatus=='u'){
			status = newStatus;
			//increment each ingredient
			return 0;
		}
		if(status == 's' && newStatus=='f'){
			status = newStatus;
			return 0;
		}
		
		return -2;
	}
	
	
}