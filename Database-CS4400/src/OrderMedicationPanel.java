import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class OrderMedicationPanel extends JPanel {
	
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	private JTextField txtMedicationName;
	
	public OrderMedicationPanel() throws SQLException, ParseException {
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblOrderMedicationFrom = new JLabel("Order Medication From Pharmacy");
		lblOrderMedicationFrom.setBounds(169, 30, 240, 16);
		add(lblOrderMedicationFrom);
		
		JLabel lblMedicationName = new JLabel("Medication Name:");
		lblMedicationName.setBounds(51, 126, 138, 16);
		add(lblMedicationName);
		
		JLabel lblDosage = new JLabel("Dosage:");
		lblDosage.setBounds(51, 184, 61, 16);
		add(lblDosage);
		
		JLabel lblDuration = new JLabel("Duration:");
		lblDuration.setBounds(51, 239, 61, 16);
		add(lblDuration);
		
		JLabel lblConsultingDoctor = new JLabel("Consulting Doctor:");
		lblConsultingDoctor.setBounds(51, 289, 138, 16);
		add(lblConsultingDoctor);
		
		JLabel lblDateOfPrescription = new JLabel("Date of Prescription:");
		lblDateOfPrescription.setBounds(51, 342, 138, 16);
		add(lblDateOfPrescription);
		
		txtMedicationName = new JTextField();
		txtMedicationName.setText("Medication Name");
		txtMedicationName.setBounds(289, 120, 134, 28);
		add(txtMedicationName);
		txtMedicationName.setColumns(10);
		
		String[] dosage = {"1", "2", "3", "4", "5"};
		JComboBox CbDosage = new JComboBox(dosage);
		CbDosage.setBounds(289, 184, 52, 27);
		add(CbDosage);
		
		String[] month = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		JComboBox CbDurationMonth = new JComboBox(month);
		CbDurationMonth.setBounds(289, 235, 52, 27);
		add(CbDurationMonth);
		
		String[] day = {"5", "10", "15", "20", "25", "30"};
		JComboBox CbDurationDay = new JComboBox(day);
		CbDurationDay.setBounds(408, 235, 52, 27);
		add(CbDurationDay);
		
		String[] docNames = doctorList();
		JComboBox CbDoctorList = new JComboBox(docNames);
		CbDoctorList.setBounds(289, 285, 77, 27);
		add(CbDoctorList);
		
		JLabel lblPerDay = new JLabel("Per Day");
		lblPerDay.setBounds(339, 188, 61, 16);
		add(lblPerDay);
		
		JLabel lblMonths = new JLabel("Months");
		lblMonths.setBounds(339, 239, 61, 16);
		add(lblMonths);
		
		JLabel lblDays = new JLabel("Days");
		lblDays.setBounds(459, 239, 61, 16);
		add(lblDays);
		
		String[] dates = getDates();
		JComboBox CbDate = new JComboBox(dates);
		CbDate.setBounds(289, 338, 108, 27);
		add(CbDate);
		
		JLabel lblAddMedicationTo = new JLabel("Add Medication To Basket");
		lblAddMedicationTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		lblAddMedicationTo.setBounds(51, 393, 176, 16);
		add(lblAddMedicationTo);
		
		JButton btnCheckOut = new JButton("Check Out");
		btnCheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCheckOut.setBounds(403, 388, 117, 29);
		add(btnCheckOut);
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
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

		}
		
		String[] docNames = (String[]) nameList.toArray(new String[nameList.size()]);
		for(int i = 0; i < docNames.length; i++){
			System.out.println(docNames[i]);
		}
		return docNames;		
	}
	
	public String[] getDates() throws ParseException{
		List<String> datesList = new ArrayList<String>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		int count = 20;
		Calendar cal = Calendar.getInstance();
		cal.setTime(cal.getTime());
		while(count > 0){
			datesList.add(dateFormat.format(cal.getTime()));
			System.out.println(dateFormat.format(cal.getTime()));
			cal.add(Calendar.DATE, -1);
			count --;
		}
		String[] dates = (String[]) datesList.toArray(new String[datesList.size()]);
		
		return dates;

	}
}
