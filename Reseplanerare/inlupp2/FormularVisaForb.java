package inlupp2;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FormularVisaForb extends JPanel{
	private JLabel info;
	private JTextField typ, tid;

	public FormularVisaForb(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLocation(400, 300);

		JPanel rad1 = new JPanel();
		add(info = new JLabel());
		add(rad1);

		JPanel rad2 = new JPanel();
		add(new JLabel ("Namn: "));
		add(typ = new JTextField());
		typ.setEditable(false);
		add(rad2);

		JPanel rad3 = new JPanel();
		add(new JLabel("Tid: "));
		add(tid = new JTextField());
		tid.setEditable(false);
		add(rad3);
	}

	public void setInfo(String s){
		info.setText(s);
	}

	public void setTyp(String str){
		typ.setText(str);
	}

	public void setTid(int i){
		tid.setText(i+"");
	}
}
