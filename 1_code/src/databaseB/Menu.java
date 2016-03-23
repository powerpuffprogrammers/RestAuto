package databaseB;

import java.util.HashMap;

/**
 * Holds the menu of dishes
 * @author cms549
 *
 */
public class Menu {
	/**
	 * First String maps category (Drink, App, Entree, Dessert) to a map
	 * Inner map maps dish name to dish
	 */
	public HashMap<String, HashMap<String,Dish>> menu;
	
	/**
	 * Constructor for empty menu
	 */
	public Menu(){
		menu = new HashMap<String, HashMap<String,Dish>>();
	}
}