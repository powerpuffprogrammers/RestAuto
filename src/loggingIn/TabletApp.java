package loggingIn;

import java.awt.Dimension;
import javax.swing.JFrame;
import interfaces.ChefInterface;
import interfaces.HostInterface;
import waiter.WaiterInterface;

public class TabletApp {
	public static void main(String[] args) {
		
		//GUI stuff
		JFrame frame= new JFrame("SWE");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LogInScreen logInPanel= new LogInScreen();
		frame.setContentPane(logInPanel);
		frame.pack();
		frame.setSize(new Dimension(1200,600));
		while(true){
			//calls the interface to set up the screen
			//constructors won't return until the screen closes or they log out
			if(logInPanel.loggedIn=='h'){
				new HostInterface(frame, logInPanel.currIDEntry);
			}
			else if(logInPanel.loggedIn=='c'){
				new ChefInterface(frame,logInPanel.currIDEntry);
			}
			//else if(pan1.loggedIn=='m'){
				//ManagerInterface h = new ManagerInterface(frame,logInPanel.currIDEntry);
			//}
			else if(logInPanel.loggedIn=='w'){
				WaiterInterface w = new WaiterInterface(frame,logInPanel.currIDEntry);
				w.runUntilLogOut();
			}else{
				System.out.print(logInPanel.loggedIn);
				continue;
			}
			//show log in screen again
			logInPanel.loggedIn='0';
			frame.setContentPane(logInPanel);
		}
		
		
	}
}
