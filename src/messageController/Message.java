package messageController;

public class Message {
	
	
	//Info about sender
	public SenderInfo senderInfo;
	
	//Info about receiver
	public SenderInfo receiverInfo;
	
	
	//Actual message
	public String content;
	
	public Message(SenderInfo s, SenderInfo r, String mess){
		//Load in sender info
		senderInfo =s;
		
		//Load in receiver info
		receiverInfo = r;
		
		//Load in actual message
		content = mess;
	}

}
