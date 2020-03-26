package pack;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private String correctPassword = "123";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login loginFrame = new Login();
					loginFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 396, 261);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 347, 201);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel Username = new JLabel("Username");
		Username.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Username.setBounds(10, 44, 105, 36);
		panel.add(Username);
		
		userNameField = new JTextField();
		userNameField.setBounds(112, 55, 150, 24);
		panel.add(userNameField);
		userNameField.setColumns(10);
		
		JLabel Password = new JLabel("Password");
		Password.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Password.setBounds(10, 90, 105, 36);
		panel.add(Password);
		
		JLabel lblTheBoyzApp = new JLabel("The Boyz App");
		lblTheBoyzApp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTheBoyzApp.setBounds(112, 10, 225, 24);
		panel.add(lblTheBoyzApp);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(112, 100, 150, 24);
		panel.add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = userNameField.getText();
				@SuppressWarnings("deprecation")
				String password = passwordField.getText();
				loginMessage(name, password);
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogin.setBounds(112, 148, 150, 24);
		panel.add(btnLogin);
	}
	
	// method that attempts to login using the parameters passed by the user.
	private void loginMessage(String name, String password) {
		System.out.println("About to login");
		if (name.equals("George") && password.equals(correctPassword)) {
		System.out.println("Logging in as: "+name);
		Frame1 frame1 = new Frame1();
		frame1.newWindow();
		System.out.println("Opening frame");
		dispose();
		}
		else {
			System.out.println("Wrong password bitch");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}



























