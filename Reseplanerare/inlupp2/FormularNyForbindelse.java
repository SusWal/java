package inlupp2;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FormularNyForbindelse extends JPanel{

	private JTextField typFalt = new JTextField(15);
	private JTextField tidFalt = new JTextField(15);

	public FormularNyForbindelse(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLocation(400, 300);
		JPanel rad1 = new JPanel();
		add(new JLabel ("Typ av förbindelse: "));
		add(typFalt);
		add(rad1);

		JPanel rad2 = new JPanel();
		add(new JLabel("Tid: "));
		add(tidFalt);
		add(rad2);
	}

	public String getTyp(){
		return typFalt.getText();
	}

	public int getTid(){
		return Integer.parseInt(tidFalt.getText());
	}

	public String getTidAsString(){
		return tidFalt.getText();
	}
}
