package pack;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.List;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;

import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Frame1 {
	//The frame
	private JFrame frame;
	//Contact tabs table and fields
	private JTable personalContactsTable;
	//Contact Error Labels
	private JLabel personalContactsAddErrorLbl;
	private JLabel personalContactsUpdateErrorLbl;
	private JLabel businessContactsAddErrorLbl;
	private JLabel businessContactsUpdateErrorLbl;
	//Contact entry fields
	private JTextField personalContactsNameField;
	private JComboBox personalContactsTierField;
	private JTextField personalContactsNumField;
	private JTextField businessContactsNameField;
	private JComboBox businessContactsTierField;
	private JTextField businessContactsNumField;
	//Contact edit fields
	private JTextField personalContactsEditNameField;
	private JComboBox personalContactsEditTierField;
	private JTextField personalContactsEditNumField;
	private JTextField businessContactsEditNameField;
	private JComboBox businessContactsEditTierField;
	private JTextField businessContactsEditNumField;
	private JTable businessContactsTable;


	//Data tabs table and fields
	private Choice personalDataContactChoice;
	private JTextField personalDataDayField;
	private JTextField personalDataMonthField;
	private JTextField personalDataYearField;
	private JTextField personalDataDurationField;
	private JTable personalDataTable;
	private Choice businessDataContactChoice;
	private JTextField businessDataDayField;
	private JTextField businessDataMonthField;
	private JTextField businessDataYearField;
	private JTextField businessDataDurationField;
	private JTable businessDataTable;

	//Tier table table and fields
	private JTable personalTiersTable;
	private JTextField personalTiersEditNameField;
	private JTextField personalTiersEditGoalField;
	private JTable businessTiersTable;

	private String[] tierNames = new String[8];
	
	
	//Goals Tabs
	private JTable personalGoalsTable;
	private JLabel personalGoalsNameLbl;
	private JLabel personalGoalsGoalLbl;
	private JLabel personalGoalsWeeklyAmountLbl;
	private JLabel personalGoalsLeftAmountLbl;
	private JLabel personalGoalsPercentageLbl;
	private JTable businessGoalsTable;
	private Connection dbConnection;
	
	private int personalContactEditID;
	private String personalContactEditName;
	private int businessContactEditID;
	private String businessContactEditName;
	private int personalTiersEditID;
	
	private static int PERSONAL_TIERS_BASE_ID = 1;
	private static int BUSINESS_TIERS_BASE_ID = 5;
	
	private Map<String,Integer> personalNamesToID = new HashMap<String,Integer>();
	private Map<String,Integer> businessNamesToID = new HashMap<String,Integer>();
	/**
	 * Launch the application.
	 */
	public void newWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/*
	 * DB CONNECTION FUNCTION
	 */
	public static Connection getConnection()
	{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/appdb";
		String username_and_pass = "admin";
		try 
		{
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username_and_pass, username_and_pass);
			System.out.println("Connected!");
			return conn;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Fuck SQL");
		} 
		catch (ClassNotFoundException e)
		{
			System.out.println("Fuck class");
		}
		return null;
	}
	
	
	/**
	 * Create the application.
	 */
	public Frame1() {
		dbConnection = getConnection();
		initialize();
		try {
			updateContactTable();
			updateDataTables();
			updateTierTables();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	
//	public void dataGetter() {
//		for(int i=0; i<5; i++) {
//			contactsTable.getValueAt(i, 1);
//		}
//	}
	
	private void initialize() {
		
		//THE WHOLE WINDOW
		frame = new JFrame();
		frame.setBounds(100, 100, 822, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//TOP TABS
		JTabbedPane typePanels = new JTabbedPane(JTabbedPane.TOP);
		typePanels.setBounds(10, 10, 549, 433);
		frame.getContentPane().add(typePanels);
		
		//PERSONAL TAB
		JTabbedPane personalPanels = new JTabbedPane(JTabbedPane.TOP);
		typePanels.addTab("Personal", null, personalPanels, null);
		
		//BUSINESS TAB
		JTabbedPane businessPanels = new JTabbedPane(JTabbedPane.TOP);;
		typePanels.addTab("Business", null, businessPanels, "HELP");
		
		//PERSONAL CONTACTS TAB
		JPanel personalContactsTab = new JPanel();
		personalPanels.addTab("Contacts", null, personalContactsTab, null);
		personalContactsTab.setLayout(null);
		/////////////////////////////////////////////////////////////////////////  PERSONAL CONTACTS WINDOW
		JScrollPane personalContactsScrollPane = new JScrollPane();
		personalContactsScrollPane.setBounds(30, 27, 333, 152);
		personalContactsTab.add(personalContactsScrollPane);
		
		//PERSONAL TAB - CONTACTS - TABLE
		personalContactsTable = new JTable();
		personalContactsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Tier", "Number"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		personalContactsScrollPane.setViewportView(personalContactsTable);
		
		//PERSONAL TAB - CONTACTS  -  ENTRY  NAME LBL 
		JLabel personalContactsNameLbl = new JLabel("Name");
		personalContactsNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		personalContactsNameLbl.setBounds(30, 180, 105, 36);
		personalContactsTab.add(personalContactsNameLbl);
		//PERSONAL TAB - CONTACTS  -  ENTRY  NAME FIELD 
		personalContactsNameField = new JTextField();
		personalContactsNameField.setBounds(30, 210, 96, 19);
		personalContactsTab.add(personalContactsNameField);
		personalContactsNameField.setColumns(10);
		
		//PERSONAL TAB - CONTACTS  -  ENTRY  Tier LBL 
		JLabel personalContactsTierLbl = new JLabel("Tier");
		personalContactsTierLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		personalContactsTierLbl.setBounds(137, 180, 105, 36);
		personalContactsTab.add(personalContactsTierLbl);
		//PERSONAL TAB - CONTACTS - ENTRY TIER FIELD 
		personalContactsTierField = new JComboBox();
		personalContactsTierField.setBounds(137, 210, 96, 19);
		personalContactsTab.add(personalContactsTierField);

		
		//PERSONAL TAB - CONTACTS  -  ENTRY  Number LBL 
		JLabel personalContactsNumberLbl = new JLabel("Number");
		personalContactsNumberLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		personalContactsNumberLbl.setBounds(243, 180, 105, 36);
		personalContactsTab.add(personalContactsNumberLbl);
		//PERSONAL TAB - CONTACTS -  ENTRY NUMBER FIELD 
		personalContactsNumField = new JTextField();
		personalContactsNumField.setColumns(10);
		personalContactsNumField.setBounds(243, 210, 96, 19);
		personalContactsTab.add(personalContactsNumField);
		
		
		//PERSONAL TAB - CONTACTS  - EDIT NAME LBL 
		JLabel personalContactsEditNameLbl = new JLabel("Name");
		personalContactsEditNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		personalContactsEditNameLbl.setBounds(30, 250, 105, 36);
		personalContactsTab.add(personalContactsEditNameLbl);
		//PERSONAL TAB - CONTACTS -  EDIT  NAME FIELD 
		personalContactsEditNameField = new JTextField();
		personalContactsEditNameField.setColumns(10);
		personalContactsEditNameField.setBounds(30, 280, 96, 19);
		personalContactsTab.add(personalContactsEditNameField);
		
		//PERSONAL TAB - CONTACTS  -  EDIT TIER LBL 
		JLabel personalContactsEditTierLbl = new JLabel("Name");
		personalContactsEditTierLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		personalContactsEditTierLbl.setBounds(137, 250, 105, 36);
		personalContactsTab.add(personalContactsEditTierLbl);
		//PERSONAL TAB - CONTACTS -  EDIT TIER FIELD 
		personalContactsEditTierField = new JComboBox();
//		personalContactsEditTierField.setColumns(10);
		personalContactsEditTierField.setBounds(137, 280, 96, 19);
		personalContactsTab.add(personalContactsEditTierField);	
		
		//PERSONAL TAB - CONTACTS  -  EDIT NUMBER LBL 
		JLabel personalContactsEditNumLbl = new JLabel("Name");
		personalContactsEditNumLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		personalContactsEditNumLbl.setBounds(243, 250, 105, 36);
		personalContactsTab.add(personalContactsEditNumLbl);
		//PERSONAL TAB - CONTACTS -  EDIT NUMBER FIELD
		personalContactsEditNumField = new JTextField();
		personalContactsEditNumField.setColumns(10);
		personalContactsEditNumField.setBounds(243, 280, 96, 19);
		personalContactsTab.add(personalContactsEditNumField);
		
		//PERSONAL TAB - CONTACTS - EDIT BUTTON
		
		JButton personalContactsEditBtn = new JButton("Edit");
		//PERSONAL TAB - CONTACTS - EDIT BUTTON - ON CLICK
		personalContactsEditBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//Sets the Edit fields to the values in selected table row
				DefaultTableModel contactTable = (DefaultTableModel)personalContactsTable.getModel();
				int row = personalContactsTable.getSelectedRow();
				String name = (String) contactTable.getValueAt(row,0);
				String tier = (String) contactTable.getValueAt(row,1);
				String number = (String) contactTable.getValueAt(row,2);
				personalContactsEditNameField.setText(name);
				personalContactsEditTierField.setSelectedItem(tier);;
				personalContactsEditNumField.setText(number);
				//sets variable so program doesnt see un-edited name as a duplicate
				personalContactEditName = name;
				//sets the currently selected ID
				personalContactEditID = getContactID(name,tier,number);
				
				
			}	
		});
		personalContactsEditBtn.setBounds(375, 70, 85, 21);
		personalContactsTab.add(personalContactsEditBtn);
		
		//PERSONAL TAB - CONTACTS - UPDATE BUTTON
		
		JButton personalContactsUpdateBtn = new JButton("Update");
		//PERSONAL TAB - CONTACTS - UPDATE BUTTON - ON CLICK
		personalContactsUpdateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
					String name = personalContactsEditNameField.getText();
					int tier = getTierIDFromName( personalContactsEditTierField.getSelectedItem().toString() );
					String num = personalContactsEditNumField.getText();
					//Checks to see if Name is unedited.
					boolean nameUnedited = false;
					if(name.contentEquals(personalContactEditName))
					{
						nameUnedited = true;
					}
					if(contactValidation(name,num,"Personal",personalContactsUpdateErrorLbl, nameUnedited)) {;
						PreparedStatement stmnt = dbConnection.prepareStatement("UPDATE CONTACTS SET name =?, tier = ? , contactNum = ? WHERE idContacts = ?");
						stmnt.setString(1, name);
						stmnt.setInt(2, tier);
						stmnt.setString(3, num);
						stmnt.setInt(4, personalContactEditID);
						stmnt.executeUpdate();
						stmnt.close();
						System.out.println("name: " + name);
						System.out.println("tier: " + tier);
						System.out.println("num: " + num);
						System.out.println("id: " + personalContactEditID);
						updateContactTable();
					}
				} 
				catch (SQLException e1) {
					System.out.println("error?");
					e1.printStackTrace();
				}
			}	
		});
		personalContactsUpdateBtn.setBounds(375, 280, 85, 21);
		personalContactsTab.add(personalContactsUpdateBtn);
		
		//PERSONAL TAB - CONTACTS- UPDATE - ERROR LBL
		personalContactsUpdateErrorLbl = new JLabel();
		personalContactsUpdateErrorLbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		personalContactsUpdateErrorLbl.setBounds(30, 290, 350, 36);
		personalContactsUpdateErrorLbl.setForeground(Color.red);
		personalContactsTab.add(personalContactsUpdateErrorLbl);
		
		//PERSONAL TAB - CONTACTS - REMOVE BUTTON

		JButton personalContactsRemoveBtn = new JButton("Remove");
		//PERSONAL TAB - CONTACTS - REMOVE BUTTON - ON CLICK
		personalContactsRemoveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//Gets the values of the currently selected row
				DefaultTableModel contactTable = (DefaultTableModel)personalContactsTable.getModel();
				int row = personalContactsTable.getSelectedRow();
				String name = (String) contactTable.getValueAt(row,0);
				String tier = (String) contactTable.getValueAt(row,1);
				String number = (String) contactTable.getValueAt(row,2);
				//updates the ID of the currently selected row
				personalContactEditID = getContactID(name,tier,number);
				
				try {
					
					//Removes all data about the contact from the Data table
					
					PreparedStatement dataStmnt = dbConnection.prepareStatement("DELETE FROM DATA WHERE idContact=?");
					
					//Removes the entry with that contact ID.
					PreparedStatement contactStmnt = dbConnection.prepareStatement("DELETE FROM CONTACTS WHERE idContacts=?");
					dataStmnt.setInt(1,personalContactEditID);
					contactStmnt.setInt(1,personalContactEditID);
					contactStmnt.executeUpdate();
					dataStmnt.executeUpdate();
					contactStmnt.close();
					dataStmnt.close();
					updateContactTable();
					updateDataTables();
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
		});
		personalContactsRemoveBtn.setBounds(375, 111, 85, 21);
		personalContactsTab.add(personalContactsRemoveBtn);
		
		//PERSONAL TAB - CONTACTS - ADD BUTTON
		JButton personalContactsAddBtn = new JButton("Add");
		
		//PERSONAL TAB - CONTACTS - ADD BUTTON - CLICK EVENT
		personalContactsAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Adds fields to database
				String name = personalContactsNameField.getText();
				int tier = getTierIDFromName( personalContactsTierField.getSelectedItem().toString() );
				String num = personalContactsNumField.getText();
				if(contactValidation(name,num,"Personal",personalContactsAddErrorLbl))  {
					try {
						PreparedStatement stmnt = dbConnection.prepareStatement("INSERT INTO CONTACTS (name,type,tier,contactNum) VALUES (?,?,?,?)");
						stmnt.setString(1, name);//Name
						stmnt.setString(2,"Personal");//Type
						stmnt.setInt(3, tier); //Tier
						stmnt.setString(4, num); //Number
						stmnt.execute();
						System.out.println("Added to table"); //Debugging
						stmnt.close();
						//Update Contact Table
						updateContactTable();
					} 
					catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
		}
		});
		personalContactsAddBtn.setBounds(375, 210, 85, 21);
		personalContactsTab.add(personalContactsAddBtn);
		
		//PERSONAL TAB - CONTACTS- ADD - ERROR LBL
		personalContactsAddErrorLbl = new JLabel();
		personalContactsAddErrorLbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		personalContactsAddErrorLbl.setBounds(30, 220, 350, 36);
		personalContactsAddErrorLbl.setForeground(Color.red);
		personalContactsTab.add(personalContactsAddErrorLbl);
		
	
		//PERSONAL TAB - DATA
		JPanel personalDataTab = new JPanel();
		personalPanels.addTab("Enter Data", null, personalDataTab, null);
		personalDataTab.setLayout(null);
	
		//PERSONAL TAB - DATA - SCROLL PANE
		JScrollPane personalDataScrollPane = new JScrollPane();
		personalDataScrollPane.setBounds(10, 200, 520, 177);
		personalDataTab.add(personalDataScrollPane);
		
		//PERSONAL TAB - DATA - DROPDOWN LIST
		personalDataContactChoice = new Choice();
		personalDataContactChoice.setBounds(10, 20, 110, 27);
		personalDataTab.add(personalDataContactChoice);
		
		//PERSONAL TAB - DATA -  DATE - DAY
		personalDataDayField = new JTextField();
		personalDataDayField.setColumns(10);
		personalDataDayField.setBounds(130, 20, 20, 19);
		personalDataTab.add(personalDataDayField);
		
		//PERSONAL TAB - DATA -  DATE - MONTH
		personalDataMonthField = new JTextField();
		personalDataMonthField.setColumns(10);
		personalDataMonthField.setBounds(155, 20, 20, 19);
		personalDataTab.add(personalDataMonthField);
		
		//PERSONAL TAB - DATA -  DATE - YEAR
		personalDataYearField = new JTextField();
		personalDataYearField.setColumns(10);
		personalDataYearField.setBounds(180, 20, 40, 19);
		personalDataTab.add(personalDataYearField);
		
		//PERSONAL TAB - DATA -  DATE - DURATION
		personalDataDurationField = new JTextField();
		personalDataDurationField.setColumns(10);
		personalDataDurationField.setBounds(270, 20, 40, 19);
		personalDataTab.add(personalDataDurationField);
		
		
		//PERSONAL TAB - DATA - ADD BUTTON
		JButton personalDataAddBtn = new JButton("Add");
		//PERSONAL TAB - DATA - ADD BUTTON - CLICK EVENT
		personalDataAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Get data from input fields
				String recipient = personalDataContactChoice.getSelectedItem();
				int recID = personalNamesToID.get(recipient);
				String date = personalDataYearField.getText() +"-" + personalDataMonthField.getText() + "-" + personalDataDayField.getText();
				String duration = personalDataDurationField.getText();
				try {
					PreparedStatement stmnt = dbConnection.prepareStatement("INSERT INTO DATA (idContact,duration,date) VALUES (?,?,?)");
					stmnt.setInt(1, recID);//Contact ID
					stmnt.setString(2, duration);// Duration
					stmnt.setString(3, date); //Date
					stmnt.execute();
					System.out.println("Added to table"); //Debugging
					stmnt.close();
					updateDataTables();
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		});
		personalDataAddBtn.setBounds(350, 20, 85, 21);
		personalDataTab.add(personalDataAddBtn);
		


		//PERSONAL TAB - DATA - REMOVE BUTTON

		JButton personalDataRemoveBtn = new JButton("Remove");
		//PERSONAL TAB - DATA - REMOVE BUTTON - ON CLICK
		personalDataRemoveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//Gets the values of the currently selected row
				DefaultTableModel dataTable = (DefaultTableModel)personalDataTable.getModel();
				int row = personalDataTable.getSelectedRow();
				int id = Integer.parseInt((String)dataTable.getValueAt(row,0));
				try {
					//Removes the entry with that contact ID.
					PreparedStatement stmnt = dbConnection.prepareStatement("DELETE FROM DATA WHERE idData = ?");
					stmnt.setInt(1,id);
					stmnt.executeUpdate();
					stmnt.close();
					updateDataTables();
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
		});
		personalDataRemoveBtn.setBounds(10, 170, 85, 21);
		personalDataTab.add(personalDataRemoveBtn);
		
		//PERSONAL TAB - DATA - DATA TABLE
		personalDataTable = new JTable();
		personalDataTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
			},
			new String[] {
				"ID","Name", "Duration", "Date"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class,String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		personalDataScrollPane.setViewportView(personalDataTable);
		
		//PERSONAL TAB - TIERS

		JPanel personalTiersTab = new JPanel();
		personalPanels.addTab("Tiers", null, personalTiersTab, null);
		personalTiersTab.setLayout(null);
		
		JScrollPane personalTiersScrollPane = new JScrollPane();
		personalTiersScrollPane.setBounds(10, 24, 454, 177);
		personalTiersTab.add(personalTiersScrollPane);
		
		//PERSONAL TAB - TIERS - TABLE
		personalTiersTable = new JTable();
		personalTiersTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				
			},
			new String[] {
				"Tier", "Weekly Goal"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		personalTiersScrollPane.setViewportView(personalTiersTable);

		
		

		//PERSONAL TAB - Tiers -  EDIT  NAME FIELD 
		personalTiersEditNameField = new JTextField();
		personalTiersEditNameField.setColumns(10);
		personalTiersEditNameField.setBounds(30, 210, 96, 19);
		personalTiersTab.add(personalTiersEditNameField);
		//PERSONAL TAB - Tiers -  EDIT GOAL FIELD 
		personalTiersEditGoalField = new JTextField();
		personalTiersEditGoalField.setColumns(10);
		personalTiersEditGoalField.setBounds(137, 210, 96, 19);
		personalTiersTab.add(personalTiersEditGoalField);	

		
		
		
		
		
		//PERSONAL TAB - Tiers - EDIT BUTTON

		JButton personalTiersEditBtn = new JButton("Edit");
		//PERSONAL TAB - Tiers - EDIT BUTTON - ON CLICK
		personalTiersEditBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//Sets the Edit fields to the values in selected table row
				DefaultTableModel TiersTable = (DefaultTableModel)personalTiersTable.getModel();
				int row = personalTiersTable.getSelectedRow();
				String name = (String) TiersTable.getValueAt(row,0);
				String goal = (String) TiersTable.getValueAt(row,1);
				personalTiersEditNameField.setText(name);
				personalTiersEditGoalField.setText(goal);
				//sets the currently selected ID
				personalTiersEditID = PERSONAL_TIERS_BASE_ID + row;
				System.out.println(personalTiersEditID);
				
				
			}	
		});
		personalTiersEditBtn.setBounds(475, 70, 60, 21);
		personalTiersTab.add(personalTiersEditBtn);
		
		//PERSONAL TAB - Tiers - UPDATE BUTTON
		
		JButton personalTiersUpdateBtn = new JButton("Update");
		//PERSONAL TAB - Tiers - UPDATE BUTTON - ON CLICK
		personalTiersUpdateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
					String name = personalTiersEditNameField.getText();
					int goal = Integer.parseInt(personalTiersEditGoalField.getText());
					if(! (Arrays.asList(tierNames).contains(name)) ) { //PH VALUE TRUE
						PreparedStatement stmnt = dbConnection.prepareStatement("UPDATE Tiers SET tierName =?, weeklyGoal = ?  WHERE tierID = ?");
						stmnt.setString(1, name);
						stmnt.setInt(2, goal);
						stmnt.setInt(3, personalTiersEditID);
						stmnt.executeUpdate();
						stmnt.close();
						updateTierTables();
						updateContactTable();
					}
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}	
		});
		personalTiersUpdateBtn.setBounds(375, 210, 85, 21);
		personalTiersTab.add(personalTiersUpdateBtn);

		
		
		//PERSONAL TAB - GOALS

		JPanel personalGoalsTab = new JPanel();
		personalPanels.addTab("Goals", null, personalGoalsTab, null);
		personalGoalsTab.setLayout(null);
		
		
		//PERSONAL - GOALS -NAME LBL
		personalGoalsNameLbl = new JLabel("Name: ");
		personalGoalsNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		personalGoalsNameLbl.setBounds(140, 10, 350, 36);
		personalGoalsTab.add(personalGoalsNameLbl);
		//PERSONAL - GOALS -Goal LBL
		personalGoalsGoalLbl = new JLabel("Weekly Goal: ");
		personalGoalsGoalLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		personalGoalsGoalLbl.setBounds(140, 60, 350, 36);
		personalGoalsTab.add(personalGoalsGoalLbl);
		//PERSONAL - GOALS -NAME LBL
		personalGoalsWeeklyAmountLbl = new JLabel("Hours This Week: ");
		personalGoalsWeeklyAmountLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		personalGoalsWeeklyAmountLbl.setBounds(140, 110, 350, 36);
		personalGoalsTab.add(personalGoalsWeeklyAmountLbl);
		//PERSONAL - GOALS -Goal LBL
		personalGoalsLeftAmountLbl = new JLabel("Hours Left: ");
		personalGoalsLeftAmountLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		personalGoalsLeftAmountLbl.setBounds(140, 160, 350, 36);
		personalGoalsTab.add(personalGoalsLeftAmountLbl);
		//PERSONAL - GOALS -Goal LBL
		personalGoalsPercentageLbl = new JLabel("Percentage Completeion:  %");
		personalGoalsPercentageLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		personalGoalsPercentageLbl.setBounds(140, 210, 350, 36);
		personalGoalsTab.add(personalGoalsPercentageLbl);
		
		
		JScrollPane personalGoalsScrollPane = new JScrollPane();
		personalGoalsScrollPane.setBounds(10, 10, 99, 359);
		personalGoalsTab.add(personalGoalsScrollPane);
		//PERSONAL TAB - GOALS - TABLE
		personalGoalsTable = new JTable();
		personalGoalsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"Contacts"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		personalGoalsScrollPane.setViewportView(personalGoalsTable);
		
		//PERSONAL - GOALS - TABLE - SELECTION LISTENER
		personalGoalsTable.getSelectionModel().addListSelectionListener((ListSelectionListener) new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	//get name and set label
	        	String selectedName = personalGoalsTable.getValueAt(personalGoalsTable.getSelectedRow(), 0).toString();
	        	personalGoalsNameLbl.setText("Name: "+ selectedName);
	        	//Gets ID to be used in DB queries
	            int id = getContactID(selectedName,"Personal");
	            try {
	            	//Gets a result for the current contact in the DB
					PreparedStatement contactStatement = dbConnection.prepareStatement("SELECT name, tier FROM contacts WHERE idContacts = ?");
					contactStatement.setInt(1, id);
					ResultSet contact = contactStatement.executeQuery();
					contact.next();
					//Gets the weekly goal info from the Tier table and a reference to the tier DB entry in function
					PreparedStatement tierStatement = dbConnection.prepareStatement("SELECT weeklyGoal FROM tiers WHERE tierID = ?");
					tierStatement.setInt(1, contact.getInt("tier"));
					ResultSet tier = tierStatement.executeQuery();
					tier.next();
					//Finally gets Results of all data entries for the contact from start of the week
					LocalDate date = LocalDate.now();
					TemporalField field = WeekFields.of(Locale.UK).dayOfWeek();
					LocalDate weekStart = date.with(field, 1);
					PreparedStatement dataStatement = dbConnection.prepareStatement("SELECT duration FROM DATA WHERE idContact = ? AND  date > ?");
					dataStatement.setInt(1, id);
					dataStatement.setString(2, weekStart.toString());
					ResultSet data = dataStatement.executeQuery();
					//Now calculates data for labels and updates them
					int goal = tier.getInt("weeklyGoal");
					System.out.println("Weekly Goal: " + goal + 'h');
					personalGoalsGoalLbl.setText("Weekly Goal: " + goal + 'h');
					int totalTime = 0;
					while(data.next())
					{
						totalTime += data.getInt("duration");
					}
					personalGoalsWeeklyAmountLbl.setText("Hours This Week: " + totalTime + 'h');
					int hoursLeft = goal - totalTime;
					if (hoursLeft <0 )
					{
						hoursLeft = 0;
					}
					personalGoalsLeftAmountLbl.setText("Hours Left: " + hoursLeft + 'h');
					System.out.println(totalTime / goal);
					double percentage = ((double)totalTime /(double)goal) * 100;
					if (percentage > 100) 
					{
						percentage = 100;
					}
					String percentageDisplay = new DecimalFormat("#.##").format(percentage);
					personalGoalsPercentageLbl.setText("Percentage Completion:  " + percentageDisplay + "%");
					//CLOSE STATEMENTS AND RESULT SETS
					contactStatement.close();
					contact.close();
					tierStatement.close();
					tier.close();
					dataStatement.close();
					data.close();
					
	            } 	
				
	            catch (SQLException e) {
					
					e.printStackTrace();
				}
	            
	            
	        }
	    });

		
		/*
	private JLabel personalGoalsNameLbl;
	private JLabel personalGoalsGoalLbl;
	private JLabel personalGoalsWeeklyAmountLbl;
	private JLabel personalGoalsLeftAmountLbl;
	private JLabel personalGoalsPercentageLbl;
		 */

		
		
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		

		
		//BUSINESS CONTACTS TAB
		JPanel businessContactsTab = new JPanel();
		businessPanels.addTab("Contacts", null, businessContactsTab, null);
		businessContactsTab.setLayout(null);
		/////////////////////////////////////////////////////////////////////////  BUSINESS CONTACTS WINDOW
		JScrollPane businessContactsScrollPane = new JScrollPane();
		businessContactsScrollPane.setBounds(30, 27, 333, 152);
		businessContactsTab.add(businessContactsScrollPane);
		
		//BUSINESS TAB - CONTACTS - TABLE
		businessContactsTable = new JTable();
		businessContactsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Tier", "Number"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		businessContactsScrollPane.setViewportView(businessContactsTable);
		
		//BUSINESS TAB - CONTACTS  -  ENTRY  NAME LBL 
		JLabel businessContactsNameLbl = new JLabel("Name");
		businessContactsNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		businessContactsNameLbl.setBounds(30, 180, 105, 36);
		businessContactsTab.add(businessContactsNameLbl);
		//BUSINESS TAB - CONTACTS  -  ENTRY  NAME FIELD 
		businessContactsNameField = new JTextField();
		businessContactsNameField.setBounds(30, 210, 96, 19);
		businessContactsTab.add(businessContactsNameField);
		businessContactsNameField.setColumns(10);
		
		//BUSINESS TAB - CONTACTS  -  ENTRY  Tier LBL 
		JLabel businessContactsTierLbl = new JLabel("Tier");
		businessContactsTierLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		businessContactsTierLbl.setBounds(137, 180, 105, 36);
		businessContactsTab.add(businessContactsTierLbl);
		//BUSINESS TAB - CONTACTS - ENTRY TIER FIELD 
		businessContactsTierField = new JComboBox();
		businessContactsTierField.setBounds(137, 210, 96, 19);
		businessContactsTab.add(businessContactsTierField);

		
		//BUSINESS TAB - CONTACTS  -  ENTRY  Number LBL 
		JLabel businessContactsNumberLbl = new JLabel("Number");
		businessContactsNumberLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		businessContactsNumberLbl.setBounds(243, 180, 105, 36);
		businessContactsTab.add(businessContactsNumberLbl);
		//BUSINESS TAB - CONTACTS -  ENTRY NUMBER FIELD 
		businessContactsNumField = new JTextField();
		businessContactsNumField.setColumns(10);
		businessContactsNumField.setBounds(243, 210, 96, 19);
		businessContactsTab.add(businessContactsNumField);
		
		
		//BUSINESS TAB - CONTACTS  - EDIT NAME LBL 
		JLabel businessContactsEditNameLbl = new JLabel("Name");
		businessContactsEditNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		businessContactsEditNameLbl.setBounds(30, 250, 105, 36);
		businessContactsTab.add(businessContactsEditNameLbl);
		//BUSINESS TAB - CONTACTS -  EDIT  NAME FIELD 
		businessContactsEditNameField = new JTextField();
		businessContactsEditNameField.setColumns(10);
		businessContactsEditNameField.setBounds(30, 280, 96, 19);
		businessContactsTab.add(businessContactsEditNameField);
		
		//BUSINESS TAB - CONTACTS  -  EDIT TIER LBL 
		JLabel businessContactsEditTierLbl = new JLabel("Name");
		businessContactsEditTierLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		businessContactsEditTierLbl.setBounds(137, 250, 105, 36);
		businessContactsTab.add(businessContactsEditTierLbl);
		//BUSINESS TAB - CONTACTS -  EDIT TIER FIELD 
		businessContactsEditTierField = new JComboBox();
//				businessContactsEditTierField.setColumns(10);
		businessContactsEditTierField.setBounds(137, 280, 96, 19);
		businessContactsTab.add(businessContactsEditTierField);	
		
		//BUSINESS TAB - CONTACTS  -  EDIT NUMBER LBL 
		JLabel businessContactsEditNumLbl = new JLabel("Name");
		businessContactsEditNumLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		businessContactsEditNumLbl.setBounds(243, 250, 105, 36);
		businessContactsTab.add(businessContactsEditNumLbl);
		//BUSINESS TAB - CONTACTS -  EDIT NUMBER FIELD
		businessContactsEditNumField = new JTextField();
		businessContactsEditNumField.setColumns(10);
		businessContactsEditNumField.setBounds(243, 280, 96, 19);
		businessContactsTab.add(businessContactsEditNumField);
		
		//BUSINESS TAB - CONTACTS - EDIT BUTTON
		
		JButton businessContactsEditBtn = new JButton("Edit");
		//BUSINESS TAB - CONTACTS - EDIT BUTTON - ON CLICK
		businessContactsEditBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//Sets the Edit fields to the values in selected table row
				DefaultTableModel contactTable = (DefaultTableModel)businessContactsTable.getModel();
				int row = businessContactsTable.getSelectedRow();
				String name = (String) contactTable.getValueAt(row,0);
				String tier = (String) contactTable.getValueAt(row,1);
				String number = (String) contactTable.getValueAt(row,2);
				businessContactsEditNameField.setText(name);
				businessContactsEditTierField.setSelectedItem(tier);;
				businessContactsEditNumField.setText(number);
				//sets the currently selected ID
				businessContactEditID = getContactID(name,tier,number);
				
				
			}	
		});
		businessContactsEditBtn.setBounds(375, 70, 85, 21);
		businessContactsTab.add(businessContactsEditBtn);
		
		//BUSINESS TAB - CONTACTS - UPDATE BUTTON
		
		JButton businessContactsUpdateBtn = new JButton("Update");
		//BUSINESS TAB - CONTACTS - UPDATE BUTTON - ON CLICK
		businessContactsUpdateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
					String name = businessContactsEditNameField.getText();
					int tier = getTierIDFromName( businessContactsEditTierField.getSelectedItem().toString() );
					String num = businessContactsEditNumField.getText();
					if(contactValidation(name,num,"Business",businessContactsUpdateErrorLbl)) {
						PreparedStatement stmnt = dbConnection.prepareStatement("UPDATE CONTACTS SET name =?, tier = ? , contactNum = ? WHERE idContacts = ?");
						stmnt.setString(1, name);
						stmnt.setInt(2, tier);
						stmnt.setString(3, num);
						stmnt.setInt(4, businessContactEditID);
						stmnt.executeUpdate();
						stmnt.close();
						updateContactTable();
					}
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}	
		});
		businessContactsUpdateBtn.setBounds(375, 280, 85, 21);
		businessContactsTab.add(businessContactsUpdateBtn);
		
		//BUSINESS TAB - CONTACTS- UPDATE - ERROR LBL
		businessContactsUpdateErrorLbl = new JLabel();
		businessContactsUpdateErrorLbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		businessContactsUpdateErrorLbl.setBounds(30, 290, 300, 36);
		businessContactsUpdateErrorLbl.setForeground(Color.red);
		businessContactsTab.add(businessContactsUpdateErrorLbl);
		
		//BUSINESS TAB - CONTACTS - REMOVE BUTTON

		JButton businessContactsRemoveBtn = new JButton("Remove");
		//BUSINESS TAB - CONTACTS - REMOVE BUTTON - ON CLICK
		businessContactsRemoveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//Gets the values of the currently selected row
				DefaultTableModel contactTable = (DefaultTableModel)businessContactsTable.getModel();
				int row = businessContactsTable.getSelectedRow();
				String name = (String) contactTable.getValueAt(row,0);
				String tier = (String) contactTable.getValueAt(row,1);
				String number = (String) contactTable.getValueAt(row,2);
				//updates the ID of the currently selected row
				businessContactEditID = getContactID(name,tier,number);
				
				try {
					
					//Removes all data about the contact from the Data table
					
					PreparedStatement dataStmnt = dbConnection.prepareStatement("DELETE FROM DATA WHERE idContact=?");
					
					//Removes the entry with that contact ID.
					PreparedStatement contactStmnt = dbConnection.prepareStatement("DELETE FROM CONTACTS WHERE idContacts=?");
					dataStmnt.setInt(1,businessContactEditID);
					contactStmnt.setInt(1,businessContactEditID);
					contactStmnt.executeUpdate();
					dataStmnt.executeUpdate();
					contactStmnt.close();
					dataStmnt.close();
					updateContactTable();
					updateDataTables();
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
		});
		businessContactsRemoveBtn.setBounds(375, 111, 85, 21);
		businessContactsTab.add(businessContactsRemoveBtn);
		
		//BUSINESS TAB - CONTACTS - ADD BUTTON
		JButton businessContactsAddBtn = new JButton("Add");
		
		//BUSINESS TAB - CONTACTS - ADD BUTTON - CLICK EVENT
		businessContactsAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Adds fields to database
				String name = businessContactsNameField.getText();
				int tier = getTierIDFromName( businessContactsTierField.getSelectedItem().toString() );
				String num = businessContactsNumField.getText();
				if(contactValidation(name,num,"Business",businessContactsAddErrorLbl))  {
					try {
						PreparedStatement stmnt = dbConnection.prepareStatement("INSERT INTO CONTACTS (name,type,tier,contactNum) VALUES (?,?,?,?)");
						stmnt.setString(1, name);//Name
						stmnt.setString(2,"Business");//Type
						stmnt.setInt(3, tier); //Tier
						stmnt.setString(4, num); //Number
						stmnt.execute();
						System.out.println("Added to table"); //Debugging
						stmnt.close();
						//Update Contact Table
						updateContactTable();
					} 
					catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
		}
		});
		businessContactsAddBtn.setBounds(375, 210, 85, 21);
		businessContactsTab.add(businessContactsAddBtn);
		
		//BUSINESS TAB - CONTACTS- ADD - ERROR LBL
		businessContactsAddErrorLbl = new JLabel();
		businessContactsAddErrorLbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		businessContactsAddErrorLbl.setBounds(30, 220,400, 36);
		businessContactsAddErrorLbl.setForeground(Color.red);
		businessContactsTab.add(businessContactsAddErrorLbl);
		
		
		//business TAB - DATA
		JPanel businessDataTab = new JPanel();
		businessPanels.addTab("Enter Data", null, businessDataTab, null);
		businessDataTab.setLayout(null);
	
		//business TAB - DATA - SCROLL PANE
		JScrollPane businessDataScrollPane = new JScrollPane();
		businessDataScrollPane.setBounds(10, 200, 520, 177);
		businessDataTab.add(businessDataScrollPane);
		
		//business TAB - DATA - DROPDOWN LIST
		businessDataContactChoice = new Choice();
		businessDataContactChoice.setBounds(10, 20, 110, 27);
		businessDataTab.add(businessDataContactChoice);
		
		//business TAB - DATA -  DATE - DAY
		businessDataDayField = new JTextField();
		businessDataDayField.setColumns(10);
		businessDataDayField.setBounds(130, 20, 20, 19);
		businessDataTab.add(businessDataDayField);
		
		//business TAB - DATA -  DATE - MONTH
		businessDataMonthField = new JTextField();
		businessDataMonthField.setColumns(10);
		businessDataMonthField.setBounds(155, 20, 20, 19);
		businessDataTab.add(businessDataMonthField);
		
		//business TAB - DATA -  DATE - YEAR
		businessDataYearField = new JTextField();
		businessDataYearField.setColumns(10);
		businessDataYearField.setBounds(180, 20, 40, 19);
		businessDataTab.add(businessDataYearField);
		
		//business TAB - DATA -  DATE - DURATION
		businessDataDurationField = new JTextField();
		businessDataDurationField.setColumns(10);
		businessDataDurationField.setBounds(270, 20, 40, 19);
		businessDataTab.add(businessDataDurationField);
		
		
		//business TAB - DATA - ADD BUTTON
		JButton businessDataAddBtn = new JButton("Add");
		//business TAB - DATA - ADD BUTTON - CLICK EVENT
		businessDataAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Get data from input fields
				String recipient = businessDataContactChoice.getSelectedItem();
				int recID = businessNamesToID.get(recipient);
				String date = businessDataYearField.getText() +"-" + businessDataMonthField.getText() + "-" + businessDataDayField.getText();
				String duration = businessDataDurationField.getText();
				try {
					PreparedStatement stmnt = dbConnection.prepareStatement("INSERT INTO DATA (idContact,duration,date) VALUES (?,?,?)");
					stmnt.setInt(1, recID);//Contact ID
					stmnt.setString(2, duration);// Duration
					stmnt.setString(3, date); //Date
					stmnt.execute();
					System.out.println("Added to table"); //Debugging
					stmnt.close();
					updateDataTables();
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		});
		businessDataAddBtn.setBounds(350, 20, 85, 21);
		businessDataTab.add(businessDataAddBtn);

		//business TAB - DATA - REMOVE BUTTON

		JButton businessDataRemoveBtn = new JButton("Remove");
		//business TAB - DATA - REMOVE BUTTON - ON CLICK
		businessDataRemoveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//Gets the values of the currently selected row
				DefaultTableModel dataTable = (DefaultTableModel)businessDataTable.getModel();
				int row = businessDataTable.getSelectedRow();
				int id = Integer.parseInt((String)dataTable.getValueAt(row,0));
				try {
					//Removes the entry with that contact ID.
					PreparedStatement stmnt = dbConnection.prepareStatement("DELETE FROM DATA WHERE idData = ?");
					stmnt.setInt(1,id);
					stmnt.executeUpdate();
					stmnt.close();
					updateDataTables();
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
		});
		businessDataRemoveBtn.setBounds(10, 170, 85, 21);
		businessDataTab.add(businessDataRemoveBtn);
		
		//business TAB - DATA - DATA TABLE
		businessDataTable = new JTable();
		businessDataTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
				{null, null, null,null},
			},
			new String[] {
				"ID","Name", "Duration", "Date"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class,String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		businessDataScrollPane.setViewportView(businessDataTable);

		
		//SETS CONTACT DROPDOWNS FOR BOTH DATA TABS
		SetDataContactDropdowns();
		
		//business - TIERS
		
		JPanel businessTiersTab = new JPanel();
		businessPanels.addTab("Tiers", null, businessTiersTab, null);
		businessTiersTab.setLayout(null);
		
		//business - TIERS - SCROLLABLE
		
		JScrollPane businessTiersScrollPane = new JScrollPane();
		businessTiersScrollPane.setBounds(10, 10, 409, 201);
		businessTiersTab.add(businessTiersScrollPane);

		//business TAB - TIERS - TABLE
		businessTiersTable = new JTable();
		businessTiersTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				
			},
			new String[] {
				"Tier", "Weekly Goal"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		businessTiersScrollPane.setViewportView(businessTiersTable);
		
		
		//BUSINESS TAB - GOALS

		JPanel businessGoalsTab = new JPanel();
		businessPanels.addTab("Goals", null, businessGoalsTab, null);
		businessGoalsTab.setLayout(null);
		//BUSINESS TAB - GOALS - SCROLL PANE
		JScrollPane businessGoalsScrollPane = new JScrollPane();
		businessGoalsScrollPane.setBounds(10, 10, 99, 359);
		businessGoalsTab.add(businessGoalsScrollPane);
		//BUSINESS TAB - GOALS - TABLE
		businessGoalsTable = new JTable();
		
		businessGoalsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"Contacts"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		
		
		businessGoalsScrollPane.setViewportView(businessGoalsTable);
		
		
		JPanel businessGoalsPanel = new JPanel();
		businessGoalsPanel.setBounds(119, 10, 410, 359);
		businessGoalsTab.add(businessGoalsPanel);

		
		
	}
	
	//Validation Tables        && isNum(number) && isNotDuplicateName(name,type)
	 
	
	private boolean contactValidation(String name, String number, String type, JLabel errorLbl)
	{//First Check if name is not num
		if(isName(name))
		{
			if(isNum(number))
			{
				if(isNotDuplicateName(name,type))
				{
					errorLbl.setText("");
					return true;
				}
				else
				{
					errorLbl.setText("ERROR: Name cannot be a duplicate.");
					return false;
				}
				
			}
			else
			{
				errorLbl.setText("ERROR: Not a valid number.");
				return false;
			}
		}
		else
		{
			errorLbl.setText("ERROR: Name Cannot be a number.");
			
			return false;
		}
	}
	
	private boolean contactValidation(String name, String number, String type, JLabel errorLbl, boolean bypassDuplicate)
	{//First Check if name is not num
		if(isName(name))
		{
			if(isNum(number))
			{
				if(!bypassDuplicate)
				{	
					if(isNotDuplicateName(name,type))
					{
						errorLbl.setText("");
						return true;
					}
					else
					{
						errorLbl.setText("ERROR: Name cannot be a duplicate.");
						return false;
					}
				
				}
				else 
				{
					return true;
				}
			}
			else
			{
				errorLbl.setText("ERROR: Not a valid number.");
				return false;
			}
		}
		else
		{
			errorLbl.setText("ERROR: Name Cannot be a number.");
			
			return false;
		}
	}
	
	private boolean isNum(String str) {
		if(str.contentEquals(""))
		{
			return false;
		}
		try {
			int test = Integer.parseInt(str);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}
	
	
	private boolean isName(String str)
	{
		if(str.contentEquals(""))
		{
			return false;
		}
		try {
			int test = Integer.parseInt(str);
			//System.out.println("\n"+test);
		}
		catch(NumberFormatException e)
		{
			return true;
		}
		return false;
	}
	
	
	private boolean isNotDuplicateName(String name,String type)
	{
		try {
			PreparedStatement stmnt = dbConnection.prepareStatement("SELECT name FROM CONTACTS WHERE type=?");
			stmnt.setString(1,type);
			ResultSet results = stmnt.executeQuery();
			while(results.next())
			{
				if(results.getString("name").contentEquals(name))
				{
					return false;
				}
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
		
	}
	//METHODS FOR UPDATING UI WITH DB
	
	
	//UPDATES CONTACT TABLES
	
	private String getTierName(int tierId)
	{
		try {
			PreparedStatement stmnt = dbConnection.prepareStatement("SELECT tierName FROM TIERS WHERE tierID = ?");
			stmnt.setInt(1, tierId);
			ResultSet result = stmnt.executeQuery();
			result.next();
			return result.getString("tierName");
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	private void updateContactTable() throws SQLException 
	{
		//Gets model for contact table
		DefaultTableModel personalModel = (DefaultTableModel)personalContactsTable.getModel();
		DefaultTableModel businessModel = (DefaultTableModel)businessContactsTable.getModel();
		//Gets row nums
		int rowCountP = personalModel.getRowCount();
		int rowCountB = businessModel.getRowCount();
		//Clears tables
		for (int i = rowCountP - 1; i >= 0; i--) {
			personalModel.removeRow(i);
		}
		for (int i = rowCountB - 1; i >= 0; i--) {
			businessModel.removeRow(i);
		}
		//Clear the name-id dictionaries
		personalNamesToID.clear();
		businessNamesToID.clear();
		//Gets data from database contacts table
		Statement stmnt = dbConnection.createStatement();
		ResultSet results = stmnt.executeQuery("SELECT idContacts,name,type,tier,contactNum FROM CONTACTS");
		//loops through data and adds it to table.
		while(results.next())
		{
			//Adds row to corresponding table
			if(results.getString("type").contentEquals("Personal"))
			{
				//gets the 
				String tierName = getTierName(results.getInt("tier"));
				personalModel.addRow(new Object [] {results.getString("name"), tierName, results.getString("contactNum")});
				//Add name and ID to dict
				personalNamesToID.put(results.getString("name"), results.getInt("idContacts"));
			}
			
			else
			{
				String tierName = getTierName(results.getInt("tier"));
				businessModel.addRow(new Object [] {results.getString("name"), tierName, results.getString("contactNum")});
				//Add name and ID to dict
				businessNamesToID.put(results.getString("name"), results.getInt("idContacts"));
			}
			
		}
		stmnt.close();
		//Now set the contact dropdowns
		SetDataContactDropdowns();
		updateGoalTables();
		
	}
	
	//UPDATES THE DATA TABLES
	
	private void updateDataTables() throws SQLException
	{
		//gets table models
		DefaultTableModel personalModel = (DefaultTableModel)personalDataTable.getModel();
		DefaultTableModel businessModel = (DefaultTableModel)businessDataTable.getModel();
		//clears tables
		int rowCountP = personalModel.getRowCount();
		for (int i = rowCountP - 1; i >= 0; i--) {
			personalModel.removeRow(i);
		}
		int rowCountB = businessModel.getRowCount();
		for (int i = rowCountB - 1; i >= 0; i--) {
			businessModel.removeRow(i);
		}
		//Gets the data results
		Statement stmnt = dbConnection.createStatement();
		ResultSet results = stmnt.executeQuery("SELECT idData,idContact,duration,DATE_FORMAT(date, \"%D %M %Y\") FROM DATA");
		while(results.next()) {
			//Gets the details about the person from contacts table
			PreparedStatement contactDetails = dbConnection.prepareStatement("SELECT name,type FROM CONTACTS WHERE idContacts = ?");
			contactDetails.setInt(1, results.getInt("idContact"));
			ResultSet contact = contactDetails.executeQuery();
			contact.next();
			//Checks person type to add them to required table
			if(contact.getString("type").contentEquals("Personal")) {
				personalModel.addRow(new Object [] {Integer.toString(results.getInt("idData")),contact.getString("name"), results.getString("duration"), results.getString(4)});
			}
			else
			{
				businessModel.addRow(new Object [] {Integer.toString(results.getInt("idData")),contact.getString("name"), results.getString("duration"), results.getString(4)});
			}
			contact.close();
			contactDetails.close();
		}
		stmnt.close();
		results.close();
		
		
	}
	
	private void updateTierTables() throws SQLException 
	{
		//Gets model for contact table
		DefaultTableModel personalModel = (DefaultTableModel)personalTiersTable.getModel();
		DefaultTableModel businessModel = (DefaultTableModel)businessTiersTable.getModel();
		//Gets row nums
		int rowCountP = personalModel.getRowCount();
		int rowCountB = businessModel.getRowCount();
		//Clears tables
		for (int i = rowCountP - 1; i >= 0; i--) {
			personalModel.removeRow(i);
		}
		for (int i = rowCountB - 1; i >= 0; i--) {
			businessModel.removeRow(i);
		}
		//CLEARS ALL SELECTION BOXES
		personalContactsTierField.removeAllItems();
		personalContactsEditTierField.removeAllItems();
		businessContactsTierField.removeAllItems();
		businessContactsEditTierField.removeAllItems();
		int index = 0;
		//Gets data from database contacts table
		Statement stmnt = dbConnection.createStatement();
		ResultSet results = stmnt.executeQuery("SELECT tierID, tierName, weeklyGoal FROM TIERS");
		//loops through data and adds it to table.
		while(results.next())
		{
			//adds tier name to the list of tier names
			tierNames[index] = results.getString("tierName");
			index++;
			
			//Adds row to corresponding table -- -First 4 tiers are designated for personal
			if(results.getInt("tierID") < 5)
			{
				personalContactsTierField.addItem(results.getString("tierName"));
				personalContactsEditTierField.addItem(results.getString("tierName"));
				personalModel.addRow(new Object [] {results.getString("tierName"), results.getString("weeklyGoal")});
				
			}
			
			else
			{
				businessContactsTierField.addItem(results.getString("tierName"));
				businessContactsEditTierField.addItem(results.getString("tierName"));
				businessModel.addRow(new Object [] {results.getString("tierName"), results.getString("weeklyGoal")});
			}
		}
		results.close();
		stmnt.close();
	}
	
	//UPDATES GOAL TABLE
	private void updateGoalTables()
	{
		DefaultTableModel personalModel = (DefaultTableModel)personalGoalsTable.getModel();
		DefaultTableModel businessModel = (DefaultTableModel)businessGoalsTable.getModel();
		//Gets row nums
		int rowCountP = personalModel.getRowCount();
		int rowCountB = businessModel.getRowCount();
		//Clears tables
		for (int i = rowCountP - 1; i >= 0; i--) {
			personalModel.removeRow(i);
		}
		for (int i = rowCountB - 1; i >= 0; i--) {
			businessModel.removeRow(i);
		}
		//Get contact name and type from contacts table
		try {
			Statement stmnt = dbConnection.createStatement();
			ResultSet results = stmnt.executeQuery("SELECT name,type FROM CONTACTS");
			//Add names to correct Lists
			while(results.next())
			{
				if(results.getString("type").contentEquals("Personal"))
				{
					personalModel.addRow(new Object [] {results.getString("name")});
				}
				
				else
				{
					businessModel.addRow(new Object [] {results.getString("name")});
				}
			}
			stmnt.close();
			results.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	//UPDATES THE DATA CONTACT DROPDOWNS
	
	private void SetDataContactDropdowns() {
		//Clear Lists
		personalDataContactChoice.removeAll();
		businessDataContactChoice.removeAll();
		//Get contact name and type from contacts table
		try {
			Statement stmnt = dbConnection.createStatement();
			ResultSet results = stmnt.executeQuery("SELECT name,type FROM CONTACTS");
			//Add names to correct Lists
			while(results.next())
			{
				if(results.getString("type").contentEquals("Personal"))
				{
					personalDataContactChoice.add(results.getString("name"));
				}
				
				else
				{
					businessDataContactChoice.add(results.getString("name"));
				}
			}
			stmnt.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
	//Methods for getting the ContactID from the name.
	
	private int getContactID (String name, String tier, String number)
	{
		try {
			PreparedStatement stmnt = dbConnection.prepareStatement("SELECT idContacts FROM CONTACTS WHERE (name = ?) AND (tier = ?) AND (contactNum = ?)");
			stmnt.setString(1,name);
			stmnt.setInt(2,getTierIDFromName(tier));
			stmnt.setString(3,number);
			ResultSet results = stmnt.executeQuery();
			while(results.next())
			{
				return results.getInt("idContacts");
			}
			results.close();
			stmnt.close();
		} 
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		return -1;
		
		
	}
	private int getContactID (String name, String type)
	{
		try {
			PreparedStatement stmnt = dbConnection.prepareStatement("SELECT idContacts FROM CONTACTS WHERE (name = ?) AND (type = ?) ");
			stmnt.setString(1,name);
			stmnt.setString(2,type);
			ResultSet results = stmnt.executeQuery();
			while(results.next())
			{
				return results.getInt("idContacts");
			}
			results.close();
			stmnt.close();
		} 
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		return -1;
		
		
	}
	
	private int getTierIDFromName (String tier_name) 
	{
		try {
			PreparedStatement stmnt = dbConnection.prepareStatement( "SELECT tierID FROM TIERS WHERE tierName = ?");
			stmnt.setString(1, tier_name);
			ResultSet results = stmnt.executeQuery();
			while(results.next())
			{
				return results.getInt("tierID");
			}
			results.close();
			stmnt.close();
			
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
			
		} 
		return -1;
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
