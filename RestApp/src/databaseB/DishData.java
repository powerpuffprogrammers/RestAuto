package databaseB;

//LAST CODED BY: CHRISTINA SEGERHOLM ON 2/24

import java.util.HashMap;

//This class is used to hold the information of one dish on the menu
//It will hold the ingredients and the name of the dish
//A dish will point to this so instead of having multiple copies of one dish and the ingredients we have each Dish 
//have a DishData field which points here
public class DishData {
	//Name of the dish
	private String name;
	//Ingredients in the dish
	private HashMap<String, Ingredient> listOfIngredients;
	
	//amount of each Ingredient in the dish
	private HashMap<String, Double> amtOfIngredient;
	
	public DishData(String dishName){
		this.name = dishName;
		this.listOfIngredients= new HashMap<String, Ingredient>();
	}
	
	//returns false if that ingredient was already added
	public boolean addIngredient(String ingredientName, Ingredient ingredientData){
		if(listOfIngredients.get(ingredientName) ==null){
			listOfIngredients.put(ingredientName,ingredientData);
			return true;
		}
		return false;
	}
	
	public int decrementInventory(){
		//call decrement on each ingredient in list of ingredinets
		//check if threshold is reached
		//if it is return -1
		return 0;
	}
	
}
