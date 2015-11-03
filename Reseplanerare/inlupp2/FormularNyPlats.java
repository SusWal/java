package inlupp2;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FormularNyPlats extends JPanel{

	private JTextField platsFalt = new JTextField(15);

	public FormularNyPlats(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLocation(400, 300);
		JPanel rad = new JPanel();
		add(new JLabel ("Platsens namn: "));
		add(platsFalt);
		add(rad);
	}

	public String getNamn(){
		return platsFalt.getText();
	}
}
