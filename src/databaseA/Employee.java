package databaseA;

//LAST CODED BY: CHRISTINA SEGERHOLM ON 2/24
public class Employee {
	
	//Name of employee
	public String name;
	//Unique ID of employee
	public Long id;
	//Char to describe employees position: w=waiter, h=host, c=chef, m=manager, o=owner
	public char position;
	
	//Create a new employee
	public Employee(String name, long id, char position){
		this.name= name;
		this.id=id;
		this.position=position;
	}

	
/*
	//SETTERS- CHANGERS
	public boolean changeName(String newName){
		if(newName!=null){
			
			return true;
		}
		return false;
	}
	
	public boolean changePosition(char newPosition){
		char[] possiblePositions = {'w','h','c','m','o'};
		for(int i =0;i<possiblePositions.length;i++){
			if(newPosition == possiblePositions[i]){
				this.position=newPosition;
				//updateSQLDatabase();
				return true;
			}
		}
		return false;
	}
  
	//GETTERS
	public String getName(){
		return this.name;
	}
	
	public Long getID(){
		return this.id;
	}
	
	public char getPosition(){
		return this.position;
	}
*/
}