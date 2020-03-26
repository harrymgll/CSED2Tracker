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

public class Frame1 {

	private JFrame frame;
	private JTable contactsTable;
	private JTextField first;
	private JTextField second;
	private JTable table_1;
	private JTextField name;
	private JTextField tier;
	private JTable tierTable;
	private JTable table_3;
	private JTextField third;
	private JTable goalsTable;

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

	/**
	 * Create the application.
	 */
	public Frame1() {
		initialize();
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
		frame = new JFrame();
		frame.setBounds(100, 100, 822, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 10, 549, 433);
		frame.getContentPane().add(tabbedPane_1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.addTab("Personal", null, tabbedPane, null);
		
		JPanel contactsPanel = new JPanel();
		tabbedPane.addTab("Contacts", null, contactsPanel, null);
		contactsPanel.setLayout(null);
		/////////////////////////////////////////////////////////////////////////
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 27, 333, 152);
		contactsPanel.add(scrollPane);
		
		contactsTable = new JTable();
		contactsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Tier", "Calls"
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
		scrollPane.setViewportView(contactsTable);
		
		first = new JTextField();
		first.setBounds(30, 189, 96, 19);
		contactsPanel.add(first);
		first.setColumns(10);
		
		second = new JTextField();
		second.setBounds(137, 189, 96, 19);
		contactsPanel.add(second);
		second.setColumns(10);
		
		third = new JTextField();
		third.setColumns(10);
		third.setBounds(243, 189, 96, 19);
		contactsPanel.add(third);
		
		// button code for the personal table
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			
			int famRowNum = 0, friRowNum = 0, relRowNum = 0;
			int goalRowNum;
			
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel)contactsTable.getModel();
				DefaultTableModel model2 = (DefaultTableModel)tierTable.getModel();
				DefaultTableModel model3 = (DefaultTableModel)goalsTable.getModel();
				model.addRow(new Object [] {first.getText(), second.getText(), Integer.parseInt(third.getText())});
				if (second.getText().equals("family") || second.getText().equals("Family")) {
					model2.setValueAt(first.getText(),famRowNum, 0);
					model3.setValueAt(first.getText(), goalRowNum, 0);
					famRowNum = famRowNum + 1;
					goalRowNum = goalRowNum +1;
					System.out.println("fam row number: " +famRowNum);
				}
				else if (second.getText().equals("friends") || second.getText().equals("Friends")) {
					model2.setValueAt(first.getText(), friRowNum, 1);
					model3.setValueAt(first.getText(), goalRowNum, 0);
					friRowNum = friRowNum + 1;
					goalRowNum = goalRowNum +1;
					System.out.println("friends row number: " +friRowNum);
				}
				else if (second.getText().equals("relatives") || second.getText().equals("Relatives")) {
					model2.setValueAt(first.getText(), relRowNum, 2);
					model3.setValueAt(first.getText(), goalRowNum, 0);
					relRowNum = relRowNum + 1;
					goalRowNum = goalRowNum +1;
					System.out.println("relatives row number: " +relRowNum);
			}
		}
		});
		btnAdd.setBounds(375, 188, 85, 21);
		contactsPanel.add(btnAdd);
		
	
		
		JPanel dataPanel = new JPanel();
		tabbedPane.addTab("Data", null, dataPanel, null);
		dataPanel.setLayout(null);
	
		//drop down list in DATA tab
		Choice choice = new Choice();
		choice.setBounds(10, 20, 110, 27);
		dataPanel.add(choice);
		
		JLabel lblFullName = new JLabel("Full Name:");
		lblFullName.setBounds(10, 62, 60, 13);
		dataPanel.add(lblFullName);
		
		JLabel lblMobile = new JLabel("Mobile:");
		lblMobile.setBounds(10, 88, 46, 13);
		dataPanel.add(lblMobile);
		
		JLabel lblHome = new JLabel("Home:");
		lblHome.setBounds(10, 111, 46, 13);
		dataPanel.add(lblHome);
		
		JLabel lblWork = new JLabel("Work:");
		lblWork.setBounds(10, 134, 46, 13);
		dataPanel.add(lblWork);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(10, 157, 46, 13);
		dataPanel.add(lblEmail);
		choice.add("Apples");
		choice.add("Chocolate");
		
		JPanel tiersPanel = new JPanel();
		tabbedPane.addTab("Tiers", null, tiersPanel, null);
		tiersPanel.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 24, 454, 177);
		tiersPanel.add(scrollPane_2);
		
		tierTable = new JTable();
		tierTable.setModel(new DefaultTableModel(
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
		scrollPane_2.setViewportView(tierTable);
		
		JPanel Goals = new JPanel();
		tabbedPane.addTab("Goals", null, Goals, null);
		Goals.setLayout(null);
		
		
		
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 10, 99, 359);
		Goals.add(scrollPane_4);
		
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
		scrollPane_4.setViewportView(goalsTable);
		
		JPanel panel = new JPanel();
		panel.setBounds(119, 10, 410, 359);
		Goals.add(panel);
		
		
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("Business", null, panel_3, null);
		panel_3.setLayout(null);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBounds(0, 0, 424, 238);
		panel_3.add(tabbedPane_2);
		
		JPanel panel_4 = new JPanel();
		tabbedPane_2.addTab("Contacts", null, panel_4, null);
		panel_4.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(28, 23, 339, 156);
		panel_4.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
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
		scrollPane_1.setViewportView(table_1);
		
		name = new JTextField();
		name.setBounds(28, 189, 96, 19);
		panel_4.add(name);
		name.setColumns(10);
		
		tier = new JTextField();
		tier.setBounds(146, 189, 96, 19);
		panel_4.add(tier);
		tier.setColumns(10);
		// button code for the business tab
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model2 = (DefaultTableModel)table_1.getModel();
				model2.addRow(new Object [] {name.getText(), Integer.parseInt(tier.getText())});
				
			}
		});
		
		/////////////////////////
		
		////////////////////////
		
		btnNewButton.setBounds(282, 188, 85, 21);
		panel_4.add(btnNewButton);
		
		JPanel panel_5 = new JPanel();
		tabbedPane_2.addTab("Data", null, panel_5, null);
		panel_5.setLayout(null);
		
		JPanel panel_6 = new JPanel();
		tabbedPane_2.addTab("Tiers", null, panel_6, null);
		panel_6.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 10, 409, 201);
		panel_6.add(scrollPane_3);
		
		table_3 = new JTable();
		table_3.setModel(new DefaultTableModel(
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
		scrollPane_3.setViewportView(table_3);
	}
}
