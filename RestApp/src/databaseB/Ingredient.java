package databaseB;

//LAST CODED BY: CHRISTINA SEGERHOLM ON 2/24

//Hold each ingredient's data
//Only have one object for each ingredient
public class Ingredient {
	private Double amountLeftInInventory;
	private String unit;
	private String name;
	private Double threshold;
	
	//creates new ingredient
	public Ingredient(String ingredientName,Double amountLeft, String unitOfAmount, Double threshold ){
		amountLeftInInventory=amountLeft;
		unit=unitOfAmount;
		name = ingredientName;
		this.threshold=threshold;
	}
	
	//Returns null if one of the fields was not specified yet ow returns amtLeft-threshold
	public Double checkThreshold(){
		if(amountLeftInInventory==null || threshold==null){
			return null;
		}
		return amountLeftInInventory - threshold;
	}
	
	//Returns null if input is null ow returns the amountLeftInTheInventory
	public Double decrementAmountBy(Double amount){
		if(amount==null){
			return null;
		}
		amountLeftInInventory = amountLeftInInventory - amount;
		return amountLeftInInventory;
	}
	
	//Getters
	public Double getAmountLeftInInventory(){
		return amountLeftInInventory;
	}
	
	public Double getThreshold(){
		return threshold;
	}

	public String getUnit(){
		return unit;
	}
	
	public String getName(){
		return name;
	}

}
