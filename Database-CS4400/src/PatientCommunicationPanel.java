import java.awt.Graphics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author hailin
 *
 */
@SuppressWarnings("serial")
public class PatientCommunicationPanel extends JPanel{
	
	private JComboBox comboBox;
	private JTextArea textArea;
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	
	public PatientCommunicationPanel() throws SQLException {
		
		setSize(550, 450);
		setLayout(null);
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		JLabel lblNewLabel = new JLabel("Select");
		lblNewLabel.setBounds(66, 114, 61, 16);
		add(lblNewLabel);
		
		String[] docNames = doctorList();
		comboBox = new JComboBox(docNames);
		comboBox.setBounds(170, 110, 126, 27);
		add(comboBox);
		
		JLabel lblMessages = new JLabel("Message");
		lblMessages.setBounds(66, 154, 61, 16);
		add(lblMessages);
		
	    textArea = new JTextArea();
		textArea.setBounds(170, 154, 266, 200);
		add(textArea);
		
		JButton btnNewButton = new JButton("Send Message");
		btnNewButton.setBounds(405, 390, 117, 29);
		add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					try {
						sendMessage();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		});
		
		JLabel lblBack = new JLabel("Back");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				removeAll();
				try {
					add(new PatientHomepagePanel());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				repaint();
			}
		});
		lblBack.setBounds(66, 395, 61, 16);	
		add(lblBack);
		
		JLabel lblSendMessageTo = new JLabel("Send Message To Doctor");
		lblSendMessageTo.setBounds(170, 22, 163, 16);
		add(lblSendMessageTo);
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	/*
	 * select names of all doctors
	 */
	public String[] doctorList() throws SQLException{
		
		String SQL = "SELECT DocUsername, Fname, Lname FROM Doctor";
		
		Statement stmt = conn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(SQL);
		List<String> nameList = new ArrayList<String>();
		while(rs.next()) {
			nameList.add("DR." + rs.getString("Lname"));
			//nameList.add( rs.getString("Lname"));
			
			
		}
		
		String[] docNames = (String[]) nameList.toArray(new String[nameList.size()]);
		for(int i = 0; i < docNames.length; i++){
			System.out.println(docNames[i]);
		}
		return docNames;
		
//		HashMap<String, String> doctorList = new HashMap<>();
//		while(rs.next()) {
//			doctorList.put(rs.getString("docUsername"), rs.getString("Lname"));
//		}
//		
//		return doctorList;
		
	}
	
	/*
	 * insert into message to doctor table
	 */
	public String getDoctorUsename(String Lname) throws SQLException{
		
			
			
		String SQL = "SELECT DocUsername FROM Doctor WHERE Lname = ?";
		String s="";
		try(PreparedStatement stmt = conn.prepareStatement(SQL);){
			stmt.setString(1, Lname);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			 s = rs.getString("DocUsername");
		}
		return s;
	}
	public boolean sendMessage() throws SQLException{
		String SQL = "INSERT INTO Sends_messageToDoc VALUES (?, ?, ?, ?, ?)";
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, currentPatient.cp.getPatientUsername());
			
			System.out.println("cp "+ currentPatient.cp.getPatientUsername() );
			stmt.setString(2, getDoctorUsename((comboBox.getSelectedItem()).toString().substring(3)));
			stmt.setString(3, getCurrentTimeStamp());
			stmt.setString(4, textArea.getText() );
			stmt.setInt(5, 0);
			
			int affected = stmt.executeUpdate();
			if (affected == 1) {
				System.out.println("message sent");
				JOptionPane.showMessageDialog(getParent(), "message sent");

				return true;
			} else {
				System.err.println("error");
				JOptionPane.showMessageDialog(getParent(), "error");
				return false;
			}
		}
		
	
	}
	
	private static String getCurrentTimeStamp() {
		 
		java.util.Date today = new java.util.Date();
		DateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(today.getTime());
 
	}
}
	
	
