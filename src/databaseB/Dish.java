package databaseB;

//This guy will take care of calling decrementing
public class Dish {
	
	//Name of the dish
	public String name;
	
	//Name of the dish
	public double price;
	
	//Status of dish = u=unstarted, s=started, f=finished
	private char status;
	
	public String typeOfDish;
	
	public Dish(String name, double price, String typeOfDish){
		status='u';
		this.name=name;
		this.price = price;
		this.typeOfDish=typeOfDish;
	}
	
	/**
	 * Makes a copy of the dish given
	 * @return a copy of the dish d
	 */
	public Dish makeCopyOfDish(){
		return new Dish(this.name, this.price, this.typeOfDish);
	}
	
	/**
	 * Changes the status of the dish
	 * @param newStatus
	 * @return 0 on success -2 on failure
	 */
	public int changeStatus(char newStatus){
		if(status == 'u' && newStatus=='s'){
			status = newStatus;
			return 0;
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

	
	public char getStatus(){
		return status;
	}
}
