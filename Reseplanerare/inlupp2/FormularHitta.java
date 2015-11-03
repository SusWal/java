package inlupp2;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class FormularHitta extends JPanel{
	private JTextArea ruta;
	private JLabel plats1, plats2;

		public FormularHitta(){		
		    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		    setLocation(400, 300);
		    JPanel rad1 = new JPanel();
		    rad1.add(new JLabel("Från "));
		    rad1.add(plats1 = new JLabel());
		    rad1.add(new JLabel(" till "));
		    rad1.add(plats2 = new JLabel());    
		    add(rad1);
		    
		    JPanel rad2 = new JPanel();
		    ruta = new JTextArea();
		    ruta.setEditable(false);
		    add(ruta);
		    add(rad2);

		}
		
		public void setText(String s){
		    ruta.append(s);
		}
		
		public void setPlats1(String s){
			plats1.setText(s);
		}
		
		
		public void setPlats2(String s){
			plats2.setText(s);
		}
		
		
	}

