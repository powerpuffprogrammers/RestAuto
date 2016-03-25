package messageController;

/**
 * Data structure used to represent a message. Used for communication between tablets.
 * Consists of sender info which lets the MC know who is sending the message and
 * Receiver info which lets the MC know who to send the message to.
 * @author cms549
 */
public class Message {
	
	/**
	 * Sender info:
	 * position of employee
	 */
	public char senderPosition;
	
	/**
	 * Sender info:
	 * employee's unique id
	 */
	public long senderEmpID;
	
	/**
	 * Receiver info:
	 * position of employee
	 */
	public char receiverPosition;
	/**
	 * Receiver info:
	 * employee's unique id
	 */
	public long receiverEmpID;
	
	/**
	 * Actual message to be forwarded
	 */
	public String content;
	
	/**
	 * Empty Constructor: For GSON to work
	 */
	public Message(){	}
	
	/**
	 *  Creates a new message 
	 * @param recPos - receiver's position
	 * @param recID - receiver's employee id
	 * @param mess
	 */
	public Message(char recPos, long recID, String mess){
		//Load in receiver
		receiverPosition =recPos;
		
		//Load in receiver id
		receiverEmpID = recID;
		
		//Load in actual message
		content = mess;
	}

}
