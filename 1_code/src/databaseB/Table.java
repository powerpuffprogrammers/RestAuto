package databaseB;

import java.util.Date;

/**
 * Table is a data structure that is used to represent a table.
 * Holds the information for the table, including the maximum occupancy, 
 * the time the table was last sat, the status of the table, and the name of the waiter 
 * who is serving it.
 * @author cms549
 *
 */
public class Table {
	
	/**
	 * Time table was last sat.
	 */
	public Date timeLastSat;
	/**
	 * Table number
	 */
	public int tableNumber;
	/**
	 * Status of table: r=ready, p=paid, s=seated
	 */
	public char status;
	/**
	 * Waiter who has this table
	 */
	public String waiter;
	/**
	 * How many people can sit at this table
	 */
	public int maxOccupancy;
	
	/**
	 * Constructor - automatically initializes the status to ready
	 * @param tableNumber = table number of table to be created
	 * @param maxOccupancy = the amount of guests taht can sit at this table at once
	 */
	public Table(int tableNumber, int maxOccupancy){
		this.tableNumber=tableNumber;
		status='r';
		this.maxOccupancy=maxOccupancy;
	}
	
	/**
	 * Seats the table with the waiter specified.
	 * @param waiter - name of the waiter who will get this table
	 * @return true on success, false if the table is not ready to be sat
	 */
	public boolean seat(String waiter){
		if(status!='r'){
			return false;
		}
		this.waiter=waiter;
		timeLastSat= new Date();
		this.status='s';
		return true;
	}

	/**
	 * Changes the tables status to the specified status
	 * @param status
	 * @return true on success, false if invalid status entered
	 */
	public boolean changeStatus(char status){
		if(status=='r'||status=='p'||status=='s'){
			this.status=status;
			return true;
		}
		return false;
	}
	

}
