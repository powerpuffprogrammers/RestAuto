package testing;

import javax.swing.JFrame;

import databaseB.Table;
import host.HostInterface;
import messageController.Message;

/**
 * 
 * @author cms549
 */
public class MessageTesting {
	
	
	
	
	public static void main(String args[]){
		String m="h3*w0*1";
		Message mess = Message.fromString(m);
		System.out.println("h == "+mess.senderPosition);
		System.out.println("3 == "+mess.senderEmpID);
		System.out.println("w == "+mess.receiverPosition);
		System.out.println("0 == "+mess.receiverEmpID);
		System.out.println("1 == "+mess.content);

	}
}

