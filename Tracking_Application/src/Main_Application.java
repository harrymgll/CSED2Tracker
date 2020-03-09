import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.Box;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Font;

public class Main_Application {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_Application window = new Main_Application();
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
	public Main_Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 765, 458);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Box PersonalBox = Box.createVerticalBox();
		PersonalBox.setForeground(Color.YELLOW);
		PersonalBox.setBackground(Color.YELLOW);
		PersonalBox.setBounds(0, 0, 250, 421);
		frame.getContentPane().add(PersonalBox);
		
		Box Personal_TitleBox = Box.createHorizontalBox();
		PersonalBox.add(Personal_TitleBox);
		
		JLabel Personal_TitleLbl = new JLabel("Personal");
		Personal_TitleLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Personal_TitleBox.add(Personal_TitleLbl);
		
		Box horizontalBox = Box.createHorizontalBox();
		PersonalBox.add(horizontalBox);
		
		JButton Personal_TiersBtn = new JButton("Tiers");
		Personal_TiersBtn.setAlignmentY(Component.TOP_ALIGNMENT);
		horizontalBox.add(Personal_TiersBtn);
		
		JButton Personal_ContactBtn = new JButton("Contacts");
		horizontalBox.add(Personal_ContactBtn);
		Personal_ContactBtn.setAlignmentY(Component.TOP_ALIGNMENT);
		
		JButton Personal_AddBtn = new JButton("Add Data");
		Personal_AddBtn.setVerticalAlignment(SwingConstants.BOTTOM);
		horizontalBox.add(Personal_AddBtn);
		Personal_AddBtn.setAlignmentY(Component.TOP_ALIGNMENT);
		Personal_AddBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JButton Personal_GoalsBtn = new JButton("Goals");
		Personal_GoalsBtn.setAlignmentY(Component.TOP_ALIGNMENT);
		horizontalBox.add(Personal_GoalsBtn);
		
		Box BuisnessBox = Box.createVerticalBox();
		BuisnessBox.setBounds(250, 0, 250, 421);
		frame.getContentPane().add(BuisnessBox);
		BuisnessBox.setForeground(Color.YELLOW);
		BuisnessBox.setBackground(Color.YELLOW);
		
		Box Buisness_TitleBox = Box.createHorizontalBox();
		BuisnessBox.add(Buisness_TitleBox);
		
		JLabel Buisness_TitleLbl = new JLabel("Buisness");
		Buisness_TitleLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Buisness_TitleBox.add(Buisness_TitleLbl);
		
		Box Buisness_ButtonBox = Box.createHorizontalBox();
		BuisnessBox.add(Buisness_ButtonBox);
		
		JButton Buisness_TiersBtn = new JButton("Tiers");
		Buisness_TiersBtn.setAlignmentY(Component.TOP_ALIGNMENT);
		Buisness_ButtonBox.add(Buisness_TiersBtn);
		Buisness_TiersBtn.setVerticalAlignment(SwingConstants.TOP);
		Buisness_TiersBtn.setHorizontalAlignment(SwingConstants.LEFT);
		
		JButton Buisness_ContactBtn = new JButton("Contacts");
		Buisness_ButtonBox.add(Buisness_ContactBtn);
		Buisness_ContactBtn.setVerticalAlignment(SwingConstants.TOP);
		Buisness_ContactBtn.setAlignmentY(0.0f);
		
		JButton Buisness_AddBtn = new JButton("Add Data");
		Buisness_ButtonBox.add(Buisness_AddBtn);
		Buisness_AddBtn.setHorizontalAlignment(SwingConstants.LEFT);
		Buisness_AddBtn.setAlignmentY(0.0f);
		Buisness_AddBtn.setAlignmentX(1.0f);
		
		JButton Buisness_GoalsBtn = new JButton("Goals");
		Buisness_GoalsBtn.setAlignmentY(Component.TOP_ALIGNMENT);
		Buisness_ButtonBox.add(Buisness_GoalsBtn);
		
		Box GraphBox = Box.createVerticalBox();
		GraphBox.setForeground(Color.YELLOW);
		GraphBox.setBackground(Color.YELLOW);
		GraphBox.setBounds(500, 0, 250, 421);
		frame.getContentPane().add(GraphBox);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		GraphBox.add(horizontalBox_1);
		
		JLabel lblGraphs = new JLabel("Graphs");
		horizontalBox_1.add(lblGraphs);
		lblGraphs.setFont(new Font("Tahoma", Font.PLAIN, 18));
	}
}
