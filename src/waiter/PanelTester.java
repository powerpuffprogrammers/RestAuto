package waiter;

import java.awt.Dimension;

import javax.swing.JFrame;

import loggingIn.LogInScreen;

public class PanelTester {
	public static void main(String[] args) {
			
			//GUI stuff
			JFrame frame= new JFrame("SWE");
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			WaiterTickListScreen logInPanel= new WaiterTickListScreen();
			frame.setContentPane(logInPanel);
			frame.pack();
			frame.setSize(new Dimension(1200,600));
	}
}
