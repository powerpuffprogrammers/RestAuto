package loggingIn;

import java.awt.Dimension;
import javax.swing.JFrame;

import chef.ChefInterface;
import interfaces.HostInterface;
import manager.ManagerInterface;
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
		frame.setSize(new Dimension(1240,650));
		while(true){
			//calls the interface to set up the screen
			//constructors won't return until the screen closes or they log out
			if(logInPanel.loggedIn=='h'){
				new HostInterface(frame, logInPanel.currIDEntry, logInPanel.empName);
			}
			else if(logInPanel.loggedIn=='c'){
				ChefInterface c = new ChefInterface(frame,logInPanel.currIDEntry,logInPanel.empName);
				c.runUntilLogOut();
			}
			else if(logInPanel.loggedIn=='m'){
				ManagerInterface m = new ManagerInterface(frame,logInPanel.currIDEntry, logInPanel.empName);
				m.runUntilLogOut();
			}
			else if(logInPanel.loggedIn=='w'){
				WaiterInterface w = new WaiterInterface(frame,logInPanel.currIDEntry,logInPanel.empName);
				System.out.println("After waiter constructor");
				w.runUntilLogOut();
			}else{
				System.out.print(logInPanel.loggedIn);
				continue;
			}
			//show log in screen again
			System.out.println("Going Back to log in");
			logInPanel.loggedIn='0';
			logInPanel.currIDEntry=0;
			frame.setContentPane(logInPanel);
			frame.revalidate();
		}
		
		
	}
}
