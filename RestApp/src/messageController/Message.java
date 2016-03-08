package messageController;

public class Message {
	
	
	//Info about sender
	char senderPosition;
	
	//Info about reciever
	char recieverPosition;
	
	
	//Actual message
	String content;
	
	public Message(SenderInfo s, RecieverInfo r, String mess){
		//Load in sender info
		senderPosition =s.senderPosition;
		
		//Load in reciever info
		recieverPosition = r.recieverPosition;
		
		//Load in actual message
		content = mess;
	}

	public char getSenderPosition() {
		
		return senderPosition;
	}

	public String getContent() {
		// TODO Auto-generated method stub
		return content;
	}
}
