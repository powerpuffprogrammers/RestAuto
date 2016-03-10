package databaseA;

//LAST CODED BY: CHRISTINA SEGERHOLM ON 2/24

import java.util.ArrayList;
import java.util.HashMap;

import databaseB.Dish;

//Starts the DB A
public class DatabaseA {
	
	//This should hold the next ID number for a newly created employee
	private short currentID;
	
	//Matches Employee ID with employee
	private ArrayList<Employee> employeeList;
	
	//First String maps category (Drink, App, Entree, Dessert) to a map
	//Inner map maps dish name to dish
	private HashMap<String, HashMap<String,Dish>> menu;
	
	public DatabaseA(){
		
		currentID=0;//or should we fetch this from the file?
		employeeList = new ArrayList<Employee>();
		menu = new HashMap<String, HashMap<String,Dish>>();
	}

	public boolean addEmployee(String name, char position){
		char[] possiblePositions = {'w','h','c','m','o'};
		for(int i =0;i<possiblePositions.length;i++){
			if(position == possiblePositions[i]){
				employeeList.add(new Employee(name, currentID, position));
				currentID++;
				return true;
			}
		}
		
		return false;
	}
	public char logIn(short employeeID){
		Employee emp = employeeList.get(employeeID);
		if(emp==null){
			return 0;
		}
		return emp.getPosition();
		
	}

	public HashMap<String, HashMap<String,Dish>> logInAsWaiter(){
		return menu;
	}
}
