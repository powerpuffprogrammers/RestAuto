package messageController;

public class SenderInfo {

	public char position;
	public long empID;
	
	public SenderInfo(char c, long empID) {
		position=c;
		this.empID=empID;
	}
	
	public SenderInfo(char c) {
		position=c;
		empID=-1;
	}

	public SenderInfo() {
		position=0;
		empID=-1;
	}


}
