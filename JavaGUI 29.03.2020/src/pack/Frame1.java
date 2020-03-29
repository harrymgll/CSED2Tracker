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

public class Frame1 {

	private JFrame frame;
	private JTable personalContactsTable;
	private JTextField personalContactsNameField;
	private JTextField personalContactsTierField;
	private JTextField personalContactsNumField;
	private JTable buisnessContactsTable;
	private JTextField buisnessContactsNameField;
	private JTextField buisnessContactsTierField;
	private JTable personalTierTable;
	private JTable buisnessTiersTable;

	private JTable goalsTable;
	private Connection dbConnection;
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
		//PERSONAL TAB - CONTACTS  - FIELD 1
		personalContactsNameField = new JTextField();
		personalContactsNameField.setBounds(30, 189, 96, 19);
		personalContactsTab.add(personalContactsNameField);
		personalContactsNameField.setColumns(10);
		//PERSONAL TAB - CONTACTS - FIELD 2
		personalContactsTierField = new JTextField();
		personalContactsTierField.setBounds(137, 189, 96, 19);
		personalContactsTab.add(personalContactsTierField);
		personalContactsTierField.setColumns(10);
		//PERSONAL TAB - CONTACTS - FIELD 3
		personalContactsNumField = new JTextField();
		personalContactsNumField.setColumns(10);
		personalContactsNumField.setBounds(243, 189, 96, 19);
		personalContactsTab.add(personalContactsNumField);
		
		//PERSONAL TAB - CONTACTS - ADD BUTTON
		JButton personalContactsAddBtn = new JButton("Add");
		//PERSONAL TAB - CONTACTS - ADD BUTTON - CLICK EVENT
		personalContactsAddBtn.addActionListener(new ActionListener() {
			
			int famRowNum = 0, friRowNum = 0, relRowNum = 0;
			int goalRowNum;
			
			public void actionPerformed(ActionEvent e) {
				//Gets models of tabels
				DefaultTableModel tierModel = (DefaultTableModel)personalTierTable.getModel();
				DefaultTableModel goalsModel = (DefaultTableModel)goalsTable.getModel();
				//Validates input of fields --- Not gunna bother as all strings anyway
				//Adds fields to database
				try {
					PreparedStatement stmnt = dbConnection.prepareStatement("INSERT INTO CONTACTS (name,type,tier,contactNum) VALUES (?,?,?,?)");
					stmnt.setString(1, personalContactsNameField.getText());//Name
					stmnt.setString(2,"Personal");//Type
					stmnt.setString(3, personalContactsTierField.getText()); //Tier
					stmnt.setString(4, personalContactsNumField.getText()); //Number
					stmnt.execute();
					System.out.println("Added to table"); //Debugging
					stmnt.close();
					//Update Contact Table
					updateContactTable();
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}

				
				//Something with other tables
				if (personalContactsTierField.getText().equals("family") || personalContactsTierField.getText().equals("Family")) {
					tierModel.setValueAt(personalContactsNameField.getText(),famRowNum, 0);
					goalsModel.setValueAt(personalContactsNameField.getText(), goalRowNum, 0);
					famRowNum = famRowNum + 1;
					goalRowNum = goalRowNum +1;
					System.out.println("fam row number: " +famRowNum);
				}
				else if (personalContactsTierField.getText().equals("friends") || personalContactsTierField.getText().equals("Friends")) {
					tierModel.setValueAt(personalContactsNameField.getText(), friRowNum, 1);
					goalsModel.setValueAt(personalContactsNameField.getText(), goalRowNum, 0);
					friRowNum = friRowNum + 1;
					goalRowNum = goalRowNum +1;
					System.out.println("friends row number: " +friRowNum);
				}
				else if (personalContactsTierField.getText().equals("relatives") || personalContactsTierField.getText().equals("Relatives")) {
					tierModel.setValueAt(personalContactsNameField.getText(), relRowNum, 2);
					goalsModel.setValueAt(personalContactsNameField.getText(), goalRowNum, 0);
					relRowNum = relRowNum + 1;
					goalRowNum = goalRowNum +1;
					System.out.println("relatives row number: " +relRowNum);
			}
		}
		});
		personalContactsAddBtn.setBounds(375, 188, 85, 21);
		personalContactsTab.add(personalContactsAddBtn);
		
	
		//PERSONAL TAB - DATA
		JPanel personalDataTab = new JPanel();
		personalPanels.addTab("Data", null, personalDataTab, null);
		personalDataTab.setLayout(null);
	
		//PERSONAL TAB - DATA - DROPDOWN LIST
		Choice choice = new Choice();
		choice.setBounds(10, 20, 110, 27);
		personalDataTab.add(choice);
		//PERSONAL TAB - DATA - FULL NAME LABEL
		JLabel lblFullName = new JLabel("Full Name:");
		lblFullName.setBounds(10, 62, 60, 13);
		personalDataTab.add(lblFullName);
		//PERSONAL TAB - DATA - MOBILE LABEL

		JLabel lblMobile = new JLabel("Mobile:");
		lblMobile.setBounds(10, 88, 46, 13);
		personalDataTab.add(lblMobile);
		//PERSONAL TAB - DATA - HOME LABEL

		JLabel lblHome = new JLabel("Home:");
		lblHome.setBounds(10, 111, 46, 13);
		personalDataTab.add(lblHome);
		//PERSONAL TAB - DATA - WORK LABEL

		JLabel lblWork = new JLabel("Work:");
		lblWork.setBounds(10, 134, 46, 13);
		personalDataTab.add(lblWork);
		//PERSONAL TAB - DATA - EMAIL LABEL

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(10, 157, 46, 13);
		personalDataTab.add(lblEmail);
		choice.add("Apples");
		choice.add("Chocolate");
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
		
		
		
		
		
	///////////////////////////////////////////////////////////////////////////////
		
		//BUISNESS TAB
		
		JPanel buisnessTab = new JPanel();
		typePanels.addTab("Business", null, buisnessTab, null);
		buisnessTab.setLayout(null);
		
		JTabbedPane buisnessPanels = new JTabbedPane(JTabbedPane.TOP);
		buisnessPanels.setBounds(0, 0, 424, 238);
		buisnessTab.add(buisnessPanels);
		
		//BUISNESS TAB - CONTACTS
		JPanel buisnessContactsTab = new JPanel();
		buisnessPanels.addTab("Contacts", null, buisnessContactsTab, null);
		buisnessContactsTab.setLayout(null);
		
		//BUISNESS TAB - SCROLLABLE
		JScrollPane buisnessContactsScrollPane = new JScrollPane();
		buisnessContactsScrollPane.setBounds(28, 23, 339, 156);
		buisnessContactsTab.add(buisnessContactsScrollPane);
		
		//BUISNESS TAB - CONTACTS - TABLE
		buisnessContactsTable = new JTable();
		buisnessContactsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Tier"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		buisnessContactsScrollPane.setViewportView(buisnessContactsTable);
		//BUISNESS TAB - CONTACTS - NAME FIELD
		buisnessContactsNameField = new JTextField();
		buisnessContactsNameField.setBounds(28, 189, 96, 19);
		buisnessContactsTab.add(buisnessContactsNameField);
		buisnessContactsNameField.setColumns(10);
		//BUISNESS TAB - CONTACTS - TIER FIELD
		buisnessContactsTierField = new JTextField();
		buisnessContactsTierField.setBounds(146, 189, 96, 19);
		buisnessContactsTab.add(buisnessContactsTierField);
		buisnessContactsTierField.setColumns(10);
		//BUISNESS TAB - CONTACTS - ADD BUTTON
		JButton buisnessContactsButton = new JButton("Add");
		//BUISNESS TAB - CONTACTS - ADD BUTTON - CLICK EVENT
		buisnessContactsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model2 = (DefaultTableModel)buisnessContactsTable.getModel();
				model2.addRow(new Object [] {buisnessContactsNameField.getText(), Integer.parseInt(buisnessContactsTierField.getText())});
				
			}
		});
		
		
		buisnessContactsButton.setBounds(282, 188, 85, 21);
		buisnessContactsTab.add(buisnessContactsButton);
		
		//BUISNESS - DATA
		
		JPanel buisnessDataTab = new JPanel();
		buisnessPanels.addTab("Data", null, buisnessDataTab, null);
		buisnessDataTab.setLayout(null);
		
		//BUISNESS - TIERS
		
		JPanel buisnessTiersTab = new JPanel();
		buisnessPanels.addTab("Tiers", null, buisnessTiersTab, null);
		buisnessTiersTab.setLayout(null);
		
		//BUISNESS - TIERS - SCROLLABLE
		
		JScrollPane buisnessTiersScrollPane = new JScrollPane();
		buisnessTiersScrollPane.setBounds(10, 10, 409, 201);
		buisnessTiersTab.add(buisnessTiersScrollPane);
		
		//BUISNESS - TIERS - TABLE
		
		buisnessTiersTable = new JTable();
		buisnessTiersTable.setModel(new DefaultTableModel(
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
		
		buisnessTiersScrollPane.setViewportView(buisnessTiersTable);
	}
	
	//CONTACT TABLES UPDATE METHOD
	
	private void updateContactTable() throws SQLException 
	{
		//Gets model for contact table
		DefaultTableModel contactModel = (DefaultTableModel)personalContactsTable.getModel();
		//Gets row num
		int rowCount = contactModel.getRowCount();
		//Clears table
		for (int i = rowCount - 1; i >= 0; i--) {
		    contactModel.removeRow(i);
		}
		//Gets data from database contacts table
		Statement stmnt = dbConnection.createStatement();
		ResultSet results = stmnt.executeQuery("SELECT name,tier,contactNum FROM CONTACTS WHERE (type='Personal')");
		//loops through data and adds it to table.
		while(results.next())
		{
			//Adds each row to the table
			contactModel.addRow(new Object [] {results.getString("name"), results.getString("tier"), results.getString("contactNum")});
		}

		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
