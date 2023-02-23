package bookshop;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends javax.swing.JFrame {

	private JFrame frame;
	private JTextField txtname;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 490, 359);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("UserName");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(93, 132, 128, 34);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Sign in");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 36));
		lblNewLabel_1.setBounds(169, 45, 136, 44);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPassword.setBounds(93, 190, 128, 34);
		frame.getContentPane().add(lblPassword);

		txtname = new JTextField();
		txtname.setBounds(219, 139, 136, 27);
		frame.getContentPane().add(txtname);
		txtname.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(219, 197, 136, 27);
		frame.getContentPane().add(passwordField);

		JLabel success = new JLabel("");
		success.setFont(new Font("Tahoma", Font.BOLD, 18));
		success.setBounds(169, 263, 300, 25);
		frame.getContentPane().add(success);
		frame.setVisible(true);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String user = txtname.getText();
					String password = passwordField.getText();
					if (user != null && password != null) {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/bookshop", "root",
								"P@ssw0rd");
						Statement stmt = con.createStatement();
						String sql = "Select * from user Where UserName='" + user + "' and Password='" + password + "'";
						ResultSet rs = stmt.executeQuery(sql);
						String databaseUsername = null;
						String databasePassword = null;
						while (rs.next()) {
							databaseUsername = rs.getString("UserName");
							databasePassword = rs.getString("Password");
//		                    System.out.println(databaseUsername);
//		                    System.out.println(databasePassword);

						}

						if (user.equals(databaseUsername) && password.equals(databasePassword)) {
							success.setText("Successful Login!\n----");
							DatabaseInfo.main(null);

						} else {
							success.setText("Incorrect Password\n----");
						}
						con.close();
					}
				} catch (Exception l) {
					System.out.println(l);
				}

			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(337, 259, 101, 34);
		frame.getContentPane().add(btnNewButton);

	}
}
