package databaseB;

/**
 * Hold each ingredient's data
 * @author cms549
 *Only have one object for each ingredient
 */
public class Ingredient {
	
	/**
	 * Amount of ingredient left in inventory
	 */
	Double amountLeftInInventory;
	/**
	 * unit that is used in amount Left in inventory and threshold
	 */
	String unit;
	/**
	 * Name of ingredient
	 */
	String name;
	/**
	 * Amount of ingredient that will cause a low inventory notification
	 */
	Double threshold;
	
	/**
	 * creates new ingredient
	 * @param ingredientName
	 * @param amountLeft
	 * @param unitOfAmount
	 * @param threshold
	 */
	public Ingredient(String ingredientName,Double amountLeft, String unitOfAmount, Double threshold ){
		amountLeftInInventory=amountLeft;
		unit=unitOfAmount;
		name = ingredientName;
		this.threshold=threshold;
	}
	
	/**
	 * Gets the difference of the amount left in the invetory and the threshold
	 * @return the amount until this ingredient reaches its threshold, null on failure
	 */
	public Double checkThreshold(){
		if(amountLeftInInventory==null || threshold==null){
			return null;
		}
		return amountLeftInInventory - threshold;
	}
	
	/**
	 * Decrements this ingredient amount by the amount specified
	 * @param amount to decrement this ingredient by
	 * @return the amount left in the inventory on success, null on failure
	 */
	public Double decrementAmountBy(Double amount){
		if(amount==null){
			return null;
		}
		amountLeftInInventory = amountLeftInInventory - amount;
		return amountLeftInInventory;
	}
	
	
}
