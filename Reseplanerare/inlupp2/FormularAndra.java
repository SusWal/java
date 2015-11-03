package inlupp2;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FormularAndra extends JPanel{

	private JTextField tidFalt;
	private JLabel typFalt;

	public FormularAndra(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLocation(400, 300);
		JPanel rad1 = new JPanel();
		add(new JLabel ("Typ av förbindelse: "));
		add(typFalt = new JLabel());
		add(rad1);

		JPanel rad2 = new JPanel();
		add(new JLabel("Tid: "));
		add(tidFalt = new JTextField());
		add(rad2);
	}

	public void setTyp(String s){
		typFalt.setText(s);
	}

	public int getTid(){
		return Integer.parseInt(tidFalt.getText());
	}

	public String getTidAsString(){
		return tidFalt.getText();
	}
}
