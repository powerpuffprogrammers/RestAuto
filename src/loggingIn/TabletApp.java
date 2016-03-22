package loggingIn;

import java.awt.Dimension;
import javax.swing.JFrame;

import chef.ChefInterface;
import host.HostInterface;
import manager.ManagerInterface;
import waiter.WaiterInterface;

/**
 * This will be the base process running on all tablets. 
 * It handles logging in and switching to the appropriate screen once logged in.
 * @author cms549
 */
public class TabletApp {
	/**
	 * Starts the log in GUI 
	 * @param args
	 */
	public static void main(String[] args) {
		
		//GUI stuff
		JFrame frame= new JFrame("SWE");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LogInScreen logInPanel= new LogInScreen();
		frame.setContentPane(logInPanel);
		frame.pack();
		frame.setSize(new Dimension(1200,640));
		while(true){
			long id = logInPanel.currIDEntry;
			//calls the interface to set up the screen
			//constructors won't return until the screen closes or they log out
			if(logInPanel.loggedIn=='h'){
				HostInterface h = new HostInterface(frame, id, logInPanel.empName);
				h.runUntilLogOut();
				logInPanel.logOut(id);
			}
			else if(logInPanel.loggedIn=='c'){
				ChefInterface c = new ChefInterface(frame,id,logInPanel.empName);
				c.runUntilLogOut();
				logInPanel.logOut(id);
			}
			else if(logInPanel.loggedIn=='m'){
				ManagerInterface m = new ManagerInterface(frame,id, logInPanel.empName);
				m.runUntilLogOut();
				logInPanel.logOut(id);
			}
			else if(logInPanel.loggedIn=='w'){
				WaiterInterface w = new WaiterInterface(frame,id,logInPanel.empName);
				w.runUntilLogOut();
				logInPanel.logOut(id);
				
			}
			//show log in screen again
			logInPanel.loggedIn='0';
			logInPanel.currIDEntry=0;
			frame.setContentPane(logInPanel);
			frame.revalidate();
		}
		
		
	}
}
