package messageController;

/**
 * Data structure used to represent a message. Used for communication between tablets.
 * @author cms549
 *
 */
public class Message {
	
	
	/**
	 * Info about sender
	 */
	public SenderInfo senderInfo;
	
	/**
	 * Info about receiver
	 */
	public SenderInfo receiverInfo;
	
	/**
	 * Actual message
	 */
	public String content;
	
	/**
	 * Creates a new message 
	 * @param s - sender info
	 * @param r - receiver info
	 * @param mess - message to send
	 */
	public Message(SenderInfo s, SenderInfo r, String mess){
		//Load in sender info
		senderInfo =s;
		
		//Load in receiver info
		receiverInfo = r;
		
		//Load in actual message
		content = mess;
	}

}
