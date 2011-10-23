package MainServer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.mysql.jdbc.Connection;

public class GetPassword extends JFrame {
	public GetPassword(){
		final JFrame frame = this;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JPasswordField field = new JPasswordField("",15);
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String url =
                    "jdbc:mysql://localhost:3306/FileFriends";

            try {
				Connection con =
				        (Connection) DriverManager.getConnection(
				        url, "root", field.getText());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,"IncorrectPassword");
				return;
				
			}
		//		SQLDATA.password=field.getText();
				frame.dispose();
			}
			
		});
		JPanel panel = new JPanel();
		panel.add(field,BorderLayout.CENTER);
		panel.add(ok,BorderLayout.SOUTH);
		add(panel);
		pack();
		setVisible(true);
	}
}
