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
<<<<<<< HEAD
	
	
	private static void updateScreen(LogInScreen logInPanel, JFrame frame){
		logInPanel.logOut(logInPanel.currIDEntry);
		//show log in screen again
		logInPanel.loggedIn='0';
		logInPanel.currIDEntry=0;
		frame.setContentPane(logInPanel);
		frame.revalidate();
	}
=======
>>>>>>> 1533b2efae091bb850331c5136faf388f3f9aa30
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
		frame.setSize(new Dimension(1200,650));
		while(true){
<<<<<<< HEAD
			System.out.print(logInPanel.loggedIn);
			//calls the interface to set up the screen
			//constructors won't return until the screen closes or they log out
			if(logInPanel.loggedIn=='h'){
				long id = logInPanel.currIDEntry;
				HostInterface h = new HostInterface(frame, id, logInPanel.empName);
				h.runUntilLogOut();
				logInPanel.logOut(id);
				updateScreen(logInPanel, frame);
			}
			else if(logInPanel.loggedIn=='c'){
				long id = logInPanel.currIDEntry;
				ChefInterface c = new ChefInterface(frame,id,logInPanel.empName);
				c.runUntilLogOut();
				logInPanel.logOut(id);
				updateScreen(logInPanel, frame);
			}
			else if(logInPanel.loggedIn=='m'){
				long id = logInPanel.currIDEntry;
				ManagerInterface m = new ManagerInterface(frame,id, logInPanel.empName);
				m.runUntilLogOut();
				logInPanel.logOut(id);
				updateScreen(logInPanel, frame);
			}
			else if(logInPanel.loggedIn=='w'){
				long id = logInPanel.currIDEntry;
				WaiterInterface w = new WaiterInterface(frame,id,logInPanel.empName);
				w.runUntilLogOut();
				logInPanel.logOut(id);
				updateScreen(logInPanel, frame);	
			}
=======
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
>>>>>>> 1533b2efae091bb850331c5136faf388f3f9aa30
		}
		
		
	}
}
