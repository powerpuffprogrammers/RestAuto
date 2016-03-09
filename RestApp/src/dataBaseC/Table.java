package dataBaseC;

//LAST CODED BY: CHRISTINA SEGERHOLM ON 2/24

import java.util.Date;

public class Table {
	
	//time table was last sat
	Date timeLastSat;
	//table number
	int tableNumber;
	//status of table: r=ready, o=on check, s=seated
	char status;
	//waiter who has this table
	String waiter;
	//how many people can sit at this table
	int maxOccupancy;
	
	public Table(int tableNumber, int maxOccupancy){
		this.tableNumber=tableNumber;
		status='r';
		this.maxOccupancy=maxOccupancy;
	}
	
	public boolean seat(String waiter){
		if(status!='r'){
			return false;
		}
		this.waiter=waiter;
		timeLastSat= new Date();
		this.status='s';
		return true;
	}

	//returns false if invalid status
	public boolean changeStatus(char status){
		if(status=='r'||status=='o'||status=='s'){
			this.status=status;
			return true;
		}
		return false;
	}
	
	
	//GETTERS
	public Date getTimeLastSeated(){
		return timeLastSat;
	}
	
	public int getTableNumber(){
		return tableNumber;
	}
	
	public char getStatus(){
		return status;
	}
	
	public String getWaiter(){
		return waiter;
	}
	
	public int getMaxOccupancy(){
		return maxOccupancy;
	}

	public void setStatus(char c) {
		this.status=c;
		return;
		
	}
}
