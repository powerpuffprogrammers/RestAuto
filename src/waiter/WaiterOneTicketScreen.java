package waiter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dataBaseC.Dish;
import dataBaseC.Ticket;

public class WaiterOneTicketScreen extends JPanel {

	public WaiterInterface wi;
	/**
	 * current ticket open, null if none is open
	 */
	public Ticket currTicket;
	
	/**
	 * Can be Apps, Entrees, Drinks, Desserts
	 */
	public String currTabOpen;
	
	/**
	 * Remembers the last dish selected on the ticket so it knows what to remove
	 */
	private int lastDishSelected;
	
	public WaiterOneTicketScreen(WaiterInterface waiterInterface) {
		currTabOpen=null;
		lastDishSelected =-1;
		wi = waiterInterface;
		//Set color to blue
		setBackground(new Color(51, 153, 255));
		//Array layout where you pick coordinates of each component
		setLayout(null);
	}

	private void makeMenuChoices() {
		//make the categories
		int i =0; 
		Iterator<String> it = wi.menu.menu.keySet().iterator();
		while(it.hasNext() && i<4){
			String cat =it.next();
			JButton oneCategoryButton = new JButton(cat);
			oneCategoryButton.setForeground(Color.BLACK);
			oneCategoryButton.setBackground(Color.WHITE);
			oneCategoryButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					currTabOpen = cat;
					updateScreen();
				}
			});
			oneCategoryButton.setBounds(350+i*150, 10, 150, 20);
			add(oneCategoryButton);
			i++;
		}
		//list items in openTab
		if (currTabOpen!=null){
			HashMap<String, Dish> menu = wi.menu.menu.get(currTabOpen);
			int j =0; 
			Iterator<String> it1 =menu.keySet().iterator();
			while(it1.hasNext() && j<80){
				String dish =it1.next();
				JButton oneDishButton = new JButton(dish);
				oneDishButton.setForeground(Color.BLACK);
				oneDishButton.setBackground(Color.WHITE);
				oneDishButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						wi.addDishToTicket(menu.get(dish));
						updateScreen();
					}
				});
				oneDishButton.setBounds(350+i%4*200, 50+(i/4)*25, 200, 25);
				add(oneDishButton);
				i++;
			}
		}
	}

	private void makeTicketOnLeft() {
		JTextField whiteBox;
		whiteBox = new JTextField();
		whiteBox.setEditable(false);
		whiteBox.setBounds(0, 30, 300, 500);
		add(whiteBox);
		
		//Write Table Number
		JTextField tableNum;
		tableNum = new JTextField("Table #: "+ currTicket.tableNumber);
		tableNum.setEditable(false);
		tableNum.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tableNum.setHorizontalAlignment(SwingConstants.CENTER);
		tableNum.setBounds(0, 0, 300, 30);
		whiteBox.add(tableNum);
		
		//make a row for each item on the ticket (or up to 
		ArrayList<Dish> listOfDishes = currTicket.listOfDishes;
		int row = 3, i =0;
		while(row<19 && i<listOfDishes.size()){
			int index =i;
			Dish dish = listOfDishes.get(i);
			String name = dish.name;
			double price = dish.price;
			//write the name then price
			JButton oneDishButton = new JButton(name + "\t$"+price);
			oneDishButton.setForeground(Color.BLACK);
			oneDishButton.setBackground(Color.WHITE);
			oneDishButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lastDishSelected = index;
				}
			});
			oneDishButton.setBounds(0, 60+30*i, 300, 30);
			whiteBox.add(oneDishButton);
			i++;
			row++;
		}
		
		
		//last row is total
		JTextField total;
		String price = ""+currTicket.price;
		if(currTicket.price!=0){
			price=price.substring(0,price.indexOf('.')+3);
		}
		total = new JTextField("Total: $"+ price);
		total.setEditable(false);
		total.setFont(new Font("Tahoma", Font.PLAIN, 14));
		total.setHorizontalAlignment(SwingConstants.CENTER);
		total.setBounds(0, 510, 300, 30);
		whiteBox.add(total);
		
	}

	/**
	 * writes the waiter's name at the top left
	 */
	private void makeNameText() {
		JTextField nameHeader;
		nameHeader = new JTextField("Logged In As: "+ wi.name);
		nameHeader.setEditable(false);
		nameHeader.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameHeader.setHorizontalAlignment(SwingConstants.CENTER);
		nameHeader.setBounds(0, 0, 300, 30);
		add(nameHeader, getComponentCount());
		
	}
	
	/**
	 * Sets up the Back Button
	 */
	private void makeBackButton(){
		
		JButton logOutButton = new JButton("Back");
		logOutButton.setForeground(Color.WHITE);
		logOutButton.setBackground(Color.RED);
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wi.backToMainScreen();
			}
		});
		logOutButton.setBounds(1000, 0, 200, 30);
		add(logOutButton, getComponentCount());
		
	}
	
	/**
	 * Sets up the notifyManager Button
	 */
	private void makeNotifyManagerButton(){
		
		JButton notifyManager = new JButton("Notify Manager");
		notifyManager.setForeground(Color.BLACK);
		notifyManager.setBackground(Color.ORANGE);
		notifyManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeAreYouSure("you want to notify the manager?",2);
			}
		});
		notifyManager.setBounds(600,570, 300, 30);
		add(notifyManager, getComponentCount());
		
	}
	
	/**
	 * Sets up the sendTicket Button
	 */
	private void makeSendTicketButton(){
		
		JButton sendButton = new JButton("Send to Chef");
		sendButton.setForeground(Color.WHITE);
		sendButton.setBackground(Color.GREEN);
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wi.sendTicket(currTicket);
			}
		});
		sendButton.setBounds(0,570, 300, 30);
		add(sendButton, getComponentCount());
		
	}
	
	/**
	 * Sets up the Paid Button
	 */
	private void makePaidButton(){
		
		JButton paidButton = new JButton("Paid");
		paidButton.setForeground(Color.WHITE);
		paidButton.setBackground(Color.RED);
		paidButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//are you sure?
				makeAreYouSure("table "+ currTicket.tableNumber +" paid?",0);
			}
		});
		paidButton.setBounds(900,570, 300, 30);
		add(paidButton, getComponentCount());
		
	}
	
	/**
	 * Creates an are you sure message box
	 */
	private void makeAreYouSure(String m, int i) {
		//Make a White box with "Are you sure"
		JTextField areYouSure;
		areYouSure = new JTextField("Are you sure "+m);
		areYouSure.setEditable(false);
		areYouSure.setFont(new Font("Tahoma", Font.PLAIN, 16));
		areYouSure.setHorizontalAlignment(SwingConstants.CENTER);
		areYouSure.setBackground(Color.ORANGE);
		areYouSure.setBounds(200, 100, 800, 300);
		add(areYouSure);
		setComponentZOrder(areYouSure, 2);
		
		
		//Make yes button
		JButton yes = new JButton("YES");
		yes.setForeground(Color.BLACK);
		yes.setBackground(Color.GREEN);
		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (i ==0){
					wi.paid(currTicket.tableNumber);
				}
				else if(i==2){
					wi.notifyManager(currTicket);
				}
			}
		});
		yes.setBounds(300,300, 200, 30);
		add(yes);
		setComponentZOrder(yes, 1);
		
		//Make no button
		JButton no = new JButton("NO");
		no.setForeground(Color.BLACK);
		no.setBackground(Color.RED);
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateScreen();
			}
		});
		no.setBounds(650,300, 200, 30);
		add(no);
		setComponentZOrder(no, 0);
		repaint();
	}
	
	/**
	 * Sets up the Remove Dish Button
	 */
	private void makeRemoveButton(){
		
		JButton removeButton = new JButton("Remove");
		removeButton.setForeground(Color.BLACK);
		removeButton.setBackground(Color.RED);
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lastDishSelected != -1){
					wi.removeDishFromTicket(lastDishSelected);
					lastDishSelected = -1;
				}
			}
		});
		removeButton.setBounds(300,570, 300, 30);
		add(removeButton, getComponentCount());
		
	}
	
	public void setTicket(Ticket t){
		currTicket = t;
		currTicket.recentlySat=false;
		currTicket.hotFood=false;
		updateScreen();
		
	}

	public void updateScreen() {
		removeAll();
		makeNameText();
		makeTicketOnLeft();
		makeBackButton();
		makeNotifyManagerButton();
		makeSendTicketButton();
		makePaidButton();
		makeRemoveButton();
		makeMenuChoices();
		repaint();
		validate();
	}

	/** makes a notification button on top of screen like banner
	 * once it is clicked it closes it
	 * @param content
	 */
	public void makeNotification(String content) {
		JButton notificationButton = new JButton(content);
		notificationButton.setForeground(Color.BLACK);
		notificationButton.setBackground(Color.WHITE);
		notificationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove(notificationButton);
				updateScreen();
			}
		});
		notificationButton.setBounds(0, 0, 1200, 30);
		add(notificationButton,0);
		repaint();
		
	}
	
}
