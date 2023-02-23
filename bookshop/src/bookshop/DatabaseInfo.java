
/**
 * Improvement
 * 1.Connect the database once and can use in different method
 * 2.Avoid sql injection
 * 3.set the password interface or more fuction 
 * 
 * Deadline: this week
 */
package bookshop;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

public class DatabaseInfo extends javax.swing.JFrame {

	private JFrame frame;
	private JTextField txtbookname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField searchpharse;
	private JTextField txtISBN;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					DatabaseInfo window = new DatabaseInfo();
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
	public DatabaseInfo() {

		initialize();
		Connection();
		load_table();

	}

	Connection con = null;
	ResultSet rs;

	public void Connection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/bookshop", "root", "P@ssw0rd");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void load_table() {
		PreparedStatement LoadTable;
		try {
			LoadTable = con.prepareStatement("select * from book");
			rs = LoadTable.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 973, 648);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("BookShop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel.setBounds(371, 27, 213, 56);
		frame.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(32, 115, 441, 272);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("BookName");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(24, 27, 129, 32);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(24, 89, 109, 31);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_1_1.setBounds(24, 213, 109, 31);
		panel.add(lblNewLabel_1_1_1);

		txtbookname = new JTextField();
		txtbookname.setBounds(163, 31, 231, 32);
		panel.add(txtbookname);
		txtbookname.setColumns(10);

		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(163, 92, 231, 32);
		panel.add(txtedition);

		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(163, 216, 231, 32);
		panel.add(txtprice);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("ISBN");
		lblNewLabel_1_1_1_1.setBounds(24, 151, 109, 31);
		panel.add(lblNewLabel_1_1_1_1);
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));

		txtISBN = new JTextField();
		txtISBN.setBounds(163, 154, 231, 32);
		panel.add(txtISBN);
		txtISBN.setColumns(10);

		JButton btnNewButton = new JButton("save");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String bookname, bookedition, bookprice, bookISBN;
				bookname = txtbookname.getText();
				bookedition = txtedition.getText();
				bookprice = txtprice.getText();
				bookISBN = txtISBN.getText();

				if (!bookname.isEmpty() && !bookedition.isEmpty() && !bookprice.isEmpty() && !bookprice.isEmpty()) {
					try {
						PreparedStatement pst;
						// PreparedStatement GetMaxId;
						pst = con.prepareStatement(
								"insert into book(BookName, BookEdition, BookPrice, BookISBN)value(?,?,?,?)");
						// GetMaxId = con.prepareStatement("SELECT MAX(BookId)FROM book");

						pst.setString(1, bookname);
						pst.setString(2, bookedition);
						pst.setString(3, bookprice);
						pst.setString(4, bookISBN);
						pst.executeUpdate();

						JOptionPane.showMessageDialog(null, "Record Added");
						load_table();
						txtbookname.setText("");
						txtedition.setText("");
						txtprice.setText("");
						txtISBN.setText("");
						txtbookname.requestFocus();

					} catch (SQLException el) {
						el.printStackTrace();
					}
				}

			}
		});
		btnNewButton.setBounds(32, 404, 130, 49);
		frame.getContentPane().add(btnNewButton);

		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnExit.setBounds(190, 404, 130, 49);
		frame.getContentPane().add(btnExit);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtbookname.setText("");
				txtedition.setText("");
				txtprice.setText("");
				txtISBN.setText("");
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnClear.setBounds(343, 404, 130, 49);
		frame.getContentPane().add(btnClear);

		JScrollPane showtable = new JScrollPane();
		showtable.setBounds(515, 115, 397, 338);
		frame.getContentPane().add(showtable);

		table = new JTable();
		showtable.setViewportView(table);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(32, 481, 441, 96);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1_2 = new JLabel("BookName");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_2.setBounds(24, 34, 129, 32);
		panel_1.add(lblNewLabel_1_2);

		searchpharse = new JTextField();
		searchpharse.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String key = searchpharse.getText();

				PreparedStatement search;
				try {
					search = con.prepareStatement(
							"select BookName, BookEdition, BookPrice, BookISBN from book where BookISBN = ?");
					search.setString(1, key);
					ResultSet rs = search.executeQuery();

					if (rs.next() == true) {
						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);
						String ISBN = rs.getString(4);

						txtbookname.setText(name);
						txtedition.setText(edition);
						txtprice.setText(price);
						txtISBN.setText(ISBN);

					} else {
						txtbookname.setText("");
						txtedition.setText("");
						txtprice.setText("");
						txtISBN.setText("");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		searchpharse.setColumns(10);
		searchpharse.setBounds(163, 38, 231, 32);
		panel_1.add(searchpharse);

		JButton btnUodate = new JButton("update");
		btnUodate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String bookname, bookedition, bookprice, bookISBN, searchISBN;
				bookname = txtbookname.getText();
				bookedition = txtedition.getText();
				bookprice = txtprice.getText();
				bookISBN = txtISBN.getText();
				searchISBN = searchpharse.getText();
				if (!bookname.isEmpty() && !bookedition.isEmpty() && !bookprice.isEmpty() && !bookprice.isEmpty()) {
					try {
						PreparedStatement pst;
						pst = con.prepareStatement(
								"update book set BookName = ?, BookEdition = ?, BookPrice = ? , BookISBN = ? where bookISBN = ?");
						pst.setString(1, bookname);
						pst.setString(2, bookedition);
						pst.setString(3, bookprice);
						pst.setString(4, bookISBN);
						pst.setString(5, searchISBN);
						pst.executeUpdate();

						JOptionPane.showMessageDialog(null, "Record Updated");
						load_table();
						txtbookname.setText("");
						txtedition.setText("");
						txtprice.setText("");
						txtISBN.setText("");
						txtbookname.requestFocus();

					} catch (SQLException el) {
						el.printStackTrace();
					}
				}

			}
		});
		btnUodate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnUodate.setBounds(554, 507, 130, 49);
		frame.getContentPane().add(btnUodate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchISBN;
				searchISBN = searchpharse.getText();
				try {
					// to check
					PreparedStatement pst;
					pst = con.prepareStatement("delete from book where BookISBN = ?");
					pst.setString(1, searchISBN);
					pst.executeUpdate();

					JOptionPane.showMessageDialog(null, "Record Deleted");
					load_table();
					txtbookname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtISBN.setText("");
					txtbookname.requestFocus();

				} catch (SQLException el) {
					el.printStackTrace();
				}
			}

		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDelete.setBounds(761, 507, 130, 49);
		frame.getContentPane().add(btnDelete);
	}
}
