package databaseB;

import java.util.HashMap;

/**
 * This class is used to hold the ingredients of one dish on the menu.
 * @author cms549
 *It will hold the ingredients and the name of the dish
 *A dish will point to this so instead of having multiple copies of one dish 
 *and the ingredients we have each Dish have a DishData field which points here
 */

public class DishData {
	/**
	 * Name of the dish
	 */
	String name;
	/**
	 * Ingredients in the dish
	 */
	HashMap<String, Ingredient> listOfIngredients;
	
	/**
	 * amount of each Ingredient in the dish
	 */
	HashMap<String, Double> amtOfIngredient;
	
	/**
	 * price of dish
	 */
	public double price;
	
	/**
	 * creates a dish data with the name dishName.
	 * Makes an empty list of ingredients
	 * use addIngredient to add to this dish data
	 */
	public DishData(String dishName){
		this.name = dishName;
		this.listOfIngredients= new HashMap<String, Ingredient>();
		this.amtOfIngredient = new HashMap<String, Double>();
	}
	
	/**
	 * adds Ingredient to this dishdata
	 * @param ingredientName- name of ingredient
	 * @param ingredientData - Ingredient obect 
	 * @return true on success, false if that ingredient was already added
	 */
	public boolean addIngredient(Ingredient ingredientData){
		String ingredientName = ingredientData.name;
		if(listOfIngredients.get(ingredientName) ==null){
			listOfIngredients.put(ingredientName,ingredientData);
			return true;
		}
		return false;
	}
	
	/**
	 * gets the amount of ingredient that is used by this dish
	 * @param ing - ingredient name
	 * @return amount the dish uses, -1.0 if that ingredient is not in the dish
	 */
	public double getAmount(String ing){
		Double d= amtOfIngredient.get(ing);
		if(d==null){
			d=-1.0;
		}
		return d;
	}

	/**
	 * Returns the ingredients used in this dish
	 * @return
	 */
	public String[] getListOfIngredients() {
		return listOfIngredients.keySet().toArray(null);
	}
	
	
}
