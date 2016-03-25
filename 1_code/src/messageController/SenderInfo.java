package messageController;

/**
 * Data structure used to represent the info of the employee 
 * who is sending the message. It is also used to represent 
 * the info of the employee who should receive it.
 * @author cms549
 *
 */
public class SenderInfo {

	/**
	 * position of employee
	 */
	public char position;
	/**
	 * employee's unique id
	 */
	public long empID;
	/**
	 * Constructor
	 * @param c - position of employee
	 * @param empID - employee id
	 */
	public SenderInfo(char c, long empID) {
		position=c;
		this.empID=empID;
	}
	
	/**
	 * Constructor that fills in employee ID to -1 
	 * @param c
	 */
	public SenderInfo(char c) {
		position=c;
		empID=-1;
	}

	/**
	 * Constructor that fills in employee ID to -1 and position to 0
	 */
	public SenderInfo() {
		position=0;
		empID=-1;
	}


}
