package databaseA;

import java.util.ArrayList;
import java.util.HashMap;

//Starts the Server Application
public class DatabaseA {
	
	//Matches Employee ID with employee
	private ArrayList<Employee> employeeList;
	//First String maps category (Drink, App, Entree, Dessert) to a map
	//Inner map maps dish name to dish
	private HashMap<String, HashMap<String,Dish>> menu;
	
	
	private HashMap<String, Ingredient> inventory;
	
	public DatabaseA(){
		employeeList = new ArrayList<Employee>();
		menu = new HashMap<String, HashMap<String,Dish>>();
	}
	
}