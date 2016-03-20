package loggingIn;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import configuration.Configure;

public class LogInScreen extends JPanel{

	public final String dataBaseAServerName = Configure.getDomainName("databaseacontroller");
	public final int dataBaseAPortNumber = Configure.getPortNumber("databaseacontroller");
	
	/**
	 * Disables the keypad when log in has been pressed
	 */
	public boolean keypadLock;
	
	/**
	 * loggedIn Tells you who you are logged in as.
	 * '0' = no one
	 * 'h' = host
	 * 'm' = manager
	 * 'w'= waiter
	 * 'c' = chef
	 */
	public char loggedIn;
	
	public String empName;

	/**
	 * Holds what user is currently typing
	 */
	public long currIDEntry;
	
	private JTextField textField;
	private JTextField txtPleaseEnterEmployee;
	private JTextField failedAttempt;
	
	/**
	 * Creates the log in screen panel
	 */
	public LogInScreen(){
		loggedIn='0';
		
		//Set color to blue
		setBackground(new Color(51, 153, 255));
		//Array layout where you pick coordinates of each component
		setLayout(null);
		
		makeHeaderText();
		makeIDTextField();
		makeKeypad();
	}
	
	/**
	 * When logged in button is pressed, this should be called
	 * @param empID
	 */
	private void logInToDBA(long empID){
		//set up socket (as client)
		Socket client;
		try {
			client = new Socket(dataBaseAServerName, dataBaseAPortNumber);
			OutputStream outStream = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outStream);
			InputStream inStream = client.getInputStream();
			DataInputStream in = new DataInputStream(inStream);
			
			//send request 
			String message = "L:"+empID;
			out.writeUTF(message);
			String ans =in.readUTF();
			loggedIn=ans.charAt(0);
			if(loggedIn!=0){
				empName=ans.substring(1);
			}
			client.close();
			
		} catch (Exception e) {
			System.out.println("ERROR: CLIENT CAN'T CONNECT.");
			loggedIn='0';
			return;
		}
	}
	
	/**
	 * Sets up the ID Text- display the current number as a JTextField that is non editable
	 */
	private void makeIDTextField(){
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setEditable(false);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(550, 80, 100, 25);
		textField.setBackground(Color.LIGHT_GRAY);
		add(textField);
		textField.setColumns(10);
	}
	
	private void makeKeypad(){
		
		failedAttempt = new JTextField();
		failedAttempt.setText("Employee ID not recognized.");
		failedAttempt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		failedAttempt.setEditable(false);
		failedAttempt.setHorizontalAlignment(SwingConstants.CENTER);
		failedAttempt.setBounds(450, 520, 300, 25);
		failedAttempt.setForeground(Color.RED);
		add(failedAttempt);
		failedAttempt.setColumns(10);
		failedAttempt.setVisible(false);
		
		
		JButton logInButton = new JButton("Log In");
		logInButton.setForeground(Color.WHITE);
		logInButton.setBackground(new Color(0, 128, 0));
		logInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				failedAttempt.setVisible(false);
				while(keypadLock){}
				keypadLock=true;
				logInToDBA(currIDEntry);
				textField.setText("");
				if(loggedIn=='0'){
					currIDEntry=0;
					failedAttempt.setVisible(true);
				}
				keypadLock=false;
			}
		});
		logInButton.setBounds(725, 150, 90, 30);
		add(logInButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setForeground(Color.WHITE);
		deleteButton.setBackground(new Color(255, 0, 0));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				failedAttempt.setVisible(false);
				while(keypadLock){}
				keypadLock=true;
				textField.setText("");
				currIDEntry=0;
				keypadLock=false;
			}
		});
		deleteButton.setBounds(725, 270, 90, 30);
		add(deleteButton);
		
		JButton[] keypad = new JButton[10];
		int xRow1 = 500;
		int row=0;
		int cnt =0;
		for(int i = 1; i<10; i++){
			if(cnt>2){
				cnt=0;
				row++;
			}
			int d=i;
			keypad[i] = new JButton(""+i);
			keypad[i].setBounds(xRow1+ 75*cnt, 150+row*60, 50, 30);
			keypad[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					while(keypadLock){}
					keypadLock=true;
					textField.setText(textField.getText()+d);
					currIDEntry=currIDEntry*10+d;
					keypadLock=false;
				}
			});
			add(keypad[i]);
			cnt++;
		}
		
		
		keypad[0] = new JButton("0");
		keypad[0].setBounds(575, 150+180, 50, 30);
		keypad[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while(keypadLock){}
				keypadLock=true;
				textField.setText(textField.getText()+"0");
				currIDEntry=currIDEntry*10;
				keypadLock=false;
			}
		});
		add(keypad[0]);
	}
	
	/**
	 * Sets up the Header Text "Please Enter Employee ID:" As a JTextField that is non editable
	 */
	private void makeHeaderText(){
		txtPleaseEnterEmployee = new JTextField();
		txtPleaseEnterEmployee.setEditable(false);
		txtPleaseEnterEmployee.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPleaseEnterEmployee.setHorizontalAlignment(SwingConstants.CENTER);
		txtPleaseEnterEmployee.setText("Please Enter Employee ID:");
		txtPleaseEnterEmployee.setBounds(500, 20, 200, 30);
		add(txtPleaseEnterEmployee);
		txtPleaseEnterEmployee.setColumns(10);
	}
	
}
