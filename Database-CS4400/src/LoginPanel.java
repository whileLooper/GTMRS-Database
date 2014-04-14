import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;


@SuppressWarnings("serial")
public class LoginPanel extends JPanel{
	private JTextField UsernameField;
	private JTextField PasswordField;
	public static BufferedImage image;

	public LoginPanel(){
		
		setSize(550,450);
		setLayout(null);

		setBackground(Color.WHITE);
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}


		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(99, 118, 72, 25);
		add(lblNewLabel);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(99, 171, 72, 25);
		add(lblPassword);

		UsernameField = new JTextField();
		UsernameField.setBounds(204, 116, 134, 28);
		add(UsernameField);
		UsernameField.setColumns(10);

		PasswordField = new JTextField();
		PasswordField.setColumns(10);
		PasswordField.setBounds(204, 169, 134, 28);
		add(PasswordField);

		JButton btnNewButton = new JButton("Log In");
		btnNewButton.setBounds(299, 208, 117, 29);
		add(btnNewButton);

		JButton btnNew = new JButton("Create Account");
		btnNew.setBounds(96, 208, 125, 29);
		add(btnNew);
		repaint();
		setVisible(true);

	}
	public void paintComponent(Graphics g) 
	  { 
		super.paintComponent(g);
	    g.drawImage(image, 0, 0, null); 
	  } 
	
	public void registerPanel(){
		
	}
}
