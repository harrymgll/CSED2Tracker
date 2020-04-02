package pack;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.List;
import java.awt.BorderLayout;
import java.awt.Choice;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Frame1 {
	//The frame
	private JFrame frame;
	//Contact tabs table and fields
	private JTable personalContactsTable;
	//Contact entry fields
	private JTextField personalContactsNameField;
	private JTextField personalContactsTierField;
	private JTextField personalContactsNumField;
	private JTextField businessContactsNameField;
	private JTextField businessContactsTierField;
	private JTextField businessContactsNumField;
	//Contact edit fields
	private JTextField personalContactsEditNameField;
	private JTextField personalContactsEditTierField;
	private JTextField personalContactsEditNumField;
	private JTextField businessContactsEditNameField;
	private JTextField businessContactsEditTierField;
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
	private JTable personalTierTable;
	private JTable businessTiersTable;

	private JTable goalsTable;
	private Connection dbConnection;
	
	private int personalContactEditID;
	private int businessContactEditID;
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
		//PERSONAL TAB - CONTACTS  -  ENTRY  NAME FIELD 
		personalContactsNameField = new JTextField();
		personalContactsNameField.setBounds(30, 189, 96, 19);
		personalContactsTab.add(personalContactsNameField);
		personalContactsNameField.setColumns(10);
		//PERSONAL TAB - CONTACTS - ENTRY TIER FIELD 
		personalContactsTierField = new JTextField();
		personalContactsTierField.setBounds(137, 189, 96, 19);
		personalContactsTab.add(personalContactsTierField);
		personalContactsTierField.setColumns(10);
		//PERSONAL TAB - CONTACTS -  ENTRY NUMBER FIELD 
		personalContactsNumField = new JTextField();
		personalContactsNumField.setColumns(10);
		personalContactsNumField.setBounds(243, 189, 96, 19);
		personalContactsTab.add(personalContactsNumField);
		
		//PERSONAL TAB - CONTACTS -  EDIT  NAME FIELD 
		personalContactsEditNameField = new JTextField();
		personalContactsEditNameField.setColumns(10);
		personalContactsEditNameField.setBounds(30, 210, 96, 19);
		personalContactsTab.add(personalContactsEditNameField);
		//PERSONAL TAB - CONTACTS -  EDIT TIER FIELD 
		personalContactsEditTierField = new JTextField();
		personalContactsEditTierField.setColumns(10);
		personalContactsEditTierField.setBounds(137, 210, 96, 19);
		personalContactsTab.add(personalContactsEditTierField);	
		
		//PERSONAL TAB - CONTACTS -  EDIT NUMBER FIELD
		personalContactsEditNumField = new JTextField();
		personalContactsEditNumField.setColumns(10);
		personalContactsEditNumField.setBounds(243, 210, 96, 19);
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
				personalContactsEditTierField.setText(tier);
				personalContactsEditNumField.setText(number);
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
					String tier = personalContactsEditTierField.getText();
					String num = personalContactsEditNumField.getText();
					if(contactValidation(name,num,"Personal")) {
						PreparedStatement stmnt = dbConnection.prepareStatement("UPDATE CONTACTS SET name =?, tier = ? , contactNum = ? WHERE idContacts = ?");
						stmnt.setString(1, name);
						stmnt.setString(2, tier);
						stmnt.setString(3, num);
						stmnt.setInt(4, personalContactEditID);
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
		personalContactsUpdateBtn.setBounds(375, 210, 85, 21);
		personalContactsTab.add(personalContactsUpdateBtn);
		
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
				String tier = personalContactsTierField.getText();
				String num = personalContactsNumField.getText();
				if(contactValidation(name,num,"Personal"))  {
					try {
						PreparedStatement stmnt = dbConnection.prepareStatement("INSERT INTO CONTACTS (name,type,tier,contactNum) VALUES (?,?,?,?)");
						stmnt.setString(1, name);//Name
						stmnt.setString(2,"Personal");//Type
						stmnt.setString(3, tier); //Tier
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
		personalContactsAddBtn.setBounds(375, 188, 85, 21);
		personalContactsTab.add(personalContactsAddBtn);
		
	
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
		personalTierTable = new JTable();
		personalTierTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Family", "Friends", "Relatives"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		personalTiersScrollPane.setViewportView(personalTierTable);

		//PERSONAL TAB - GOALS

		JPanel personalGoalsTab = new JPanel();
		personalPanels.addTab("Goals", null, personalGoalsTab, null);
		personalGoalsTab.setLayout(null);
		
		
		
		
		JScrollPane personalGoalsScrollPane = new JScrollPane();
		personalGoalsScrollPane.setBounds(10, 10, 99, 359);
		personalGoalsTab.add(personalGoalsScrollPane);
		//PERSONAL TAB - GOALS - TABLE
		goalsTable = new JTable();
		goalsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = goalsTable.getSelectedRow();
				
			}
		});
		goalsTable.setModel(new DefaultTableModel(
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
		personalGoalsScrollPane.setViewportView(goalsTable);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(119, 10, 410, 359);
		personalGoalsTab.add(panel);
		
		
		
		
		
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
		

		
		//BUSINESS TAB - CONTACTS
		JPanel businessContactsTab = new JPanel();
		businessPanels.addTab("Contacts", null, businessContactsTab, null);
		businessContactsTab.setLayout(null);
		
		//BUSINESS TAB  CONTACTS - SCROLLABLE
		JScrollPane businessContactsScrollPane = new JScrollPane();
		businessContactsScrollPane.setBounds(30, 27, 333, 152);
		businessContactsTab.add(businessContactsScrollPane);
		
		//BUSINESS TAB - CONTACTS  -  ENTRY  NAME FIELD 
		businessContactsNameField = new JTextField();
		businessContactsNameField.setBounds(30, 189, 96, 19);
		businessContactsTab.add(businessContactsNameField);
		businessContactsNameField.setColumns(10);
		//BUSINESS TAB - CONTACTS - ENTRY TIER FIELD 
		businessContactsTierField = new JTextField();
		businessContactsTierField.setBounds(137, 189, 96, 19);
		businessContactsTab.add(businessContactsTierField);
		businessContactsTierField.setColumns(10);
		//BUSINESS TAB - CONTACTS -  ENTRY NUMBER FIELD 
		businessContactsNumField = new JTextField();
		businessContactsNumField.setColumns(10);
		businessContactsNumField.setBounds(243, 189, 96, 19);
		businessContactsTab.add(businessContactsNumField);
		
		//BUSINESS TAB - CONTACTS -  EDIT  NAME FIELD 
		businessContactsEditNameField = new JTextField();
		businessContactsEditNameField.setColumns(10);
		businessContactsEditNameField.setBounds(30, 210, 96, 19);
		businessContactsTab.add(businessContactsEditNameField);
		//BUSINESS TAB - CONTACTS -  EDIT TIER FIELD 
		businessContactsEditTierField = new JTextField();
		businessContactsEditTierField.setColumns(10);
		businessContactsEditTierField.setBounds(137, 210, 96, 19);
		businessContactsTab.add(businessContactsEditTierField);	
		
		//BUSINESS TAB - CONTACTS -  EDIT NUMBER FIELD
		businessContactsEditNumField = new JTextField();
		businessContactsEditNumField.setColumns(10);
		businessContactsEditNumField.setBounds(243, 210, 96, 19);
		businessContactsTab.add(businessContactsEditNumField);
		
		
		//BUSINESS TAB - CONTACTS - TABLE
		businessContactsTable = new JTable();
		businessContactsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Tier" ,"Number"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class , String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		businessContactsScrollPane.setViewportView(businessContactsTable);
		
		
		//BUSINESS TAB - CONTACTS - EDIT BUTTON

		JButton businessContactsEditBtn = new JButton("Edit");
		//BUSINESS TAB - CONTACTS - EDIT BUTTON - ON CLICK
		businessContactsEditBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//Sets the Edit fields to the values in selected table row
				DefaultTableModel businessTable = (DefaultTableModel)businessContactsTable.getModel();
				int row = businessContactsTable.getSelectedRow();
				String name = (String) businessTable.getValueAt(row,0);
				String tier = (String) businessTable.getValueAt(row,1);
				String number = (String) businessTable.getValueAt(row,2);
				businessContactsEditNameField.setText(name);
				businessContactsEditTierField.setText(tier);
				businessContactsEditNumField.setText(number);
				//sets the currently selected ID
				businessContactEditID = getContactID(name,"Business");
				
				
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
					String tier = businessContactsEditTierField.getText();
					String num = businessContactsEditNumField.getText();
					if(contactValidation(name,num,"Business")) {
						PreparedStatement stmnt = dbConnection.prepareStatement("UPDATE CONTACTS SET name =?, tier = ? , contactNum = ? WHERE idContacts = ?");
						stmnt.setString(1, name);
						stmnt.setString(2, tier);
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
		businessContactsUpdateBtn.setBounds(375, 210, 85, 21);
		businessContactsTab.add(businessContactsUpdateBtn);
		
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
				businessContactEditID = getContactID(name,"Business");
				
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
				String tier = businessContactsTierField.getText();
				String num = businessContactsNumField.getText();
				if(contactValidation(name,num,"Business"))  {
					try {
						PreparedStatement stmnt = dbConnection.prepareStatement("INSERT INTO CONTACTS (name,type,tier,contactNum) VALUES (?,?,?,?)");
						stmnt.setString(1, name);//Name
						stmnt.setString(2,"Business");//Type
						stmnt.setString(3, tier); //Tier
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
		businessContactsAddBtn.setBounds(375, 188, 85, 21);
		businessContactsTab.add(businessContactsAddBtn);
		
		
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
		
		//business - TIERS - TABLE
		
		businessTiersTable = new JTable();
		businessTiersTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"Management", "Co-workers"
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
	}
	
	//Validation Tables
	
	
	private boolean contactValidation(String name, String number, String type)
	{
		if(isName(name) && isNum(number) && isNotDuplicateName(name,type))
		{
			return true;
		}
		else
		{
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
				personalModel.addRow(new Object [] {results.getString("name"), results.getString("tier"), results.getString("contactNum")});
				//Add name and ID to dict
				personalNamesToID.put(results.getString("name"), results.getInt("idContacts"));
			}
			
			else
			{
				businessModel.addRow(new Object [] {results.getString("name"), results.getString("tier"), results.getString("contactNum")});
				//Add name and ID to dict
				businessNamesToID.put(results.getString("name"), results.getInt("idContacts"));
			}
			
		}
		stmnt.close();
		//Now set the contact dropdowns
		SetDataContactDropdowns();
		
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
			stmnt.setString(2,tier);
			stmnt.setString(3,number);
			ResultSet results = stmnt.executeQuery();
			while(results.next())
			{
				return results.getInt("idContacts");
			}
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
			stmnt.close();
		} 
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		return -1;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
