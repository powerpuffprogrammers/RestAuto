//LAST CODED BY: CHRISTINA SEGERHOLM ON 2/24

import java.io.IOException;

//Starts the Server Application
public class Application {

	public static void main(String[] args) {
		
		Restaurant resty = new Restaurant();
		
		//Keep running until someone presses q
		char userInput;
		try {
			userInput = (char) System.in.read();
			while(userInput!='q'){
				userInput = (char)System.in.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		};
	}

}
