package inlupp2;


import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import graphs.Edge;
import graphs.ListGraph;
import graphs.Plats;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Reseplanerare extends JFrame{
	private ListGraph g = new ListGraph();
	private Karta karta = null;
	private JFileChooser filValj;
	private JButton hitta, visa, nyPlats, nyForb, andraForb;
	private JMenuItem menyHitta, menyVisa, menyNyplats, menyNyforb, menyAndra;
	private MusLyssNyPlats mlnp;
	private MusKlickPaPlats mkpp = new MusKlickPaPlats();
	private Plats p1 = null, p2 = null;

	Reseplanerare(){
		super("Reseplanerare");

		setLayout(new BorderLayout());
		JMenuBar meny = new JMenuBar();
		setJMenuBar(meny);

		JMenu arkiv = new JMenu("Arkiv");
		meny.add(arkiv);
		JMenuItem nytt = new JMenuItem("Nytt");
		nytt.addActionListener(new NyttLyss());
		arkiv.add(nytt);
		JMenuItem avsluta = new JMenuItem("Avsluta");
		avsluta.addActionListener(new AvslutaLyss());
		arkiv.add(avsluta);


		JMenu operationer = new JMenu("Operationer");
		meny.add(operationer);
		menyHitta = new JMenuItem("Hitta förbindelse");
		operationer.add(menyHitta);
		menyVisa = new JMenuItem("Visa förbindelse");
		operationer.add(menyVisa);
		menyNyplats = new JMenuItem("Ny plats");
		operationer.add(menyNyplats);
		menyNyforb = new JMenuItem("Ny förbindelse");
		operationer.add(menyNyforb);
		menyAndra = new JMenuItem("Ändra förbindelse");
		operationer.add(menyAndra);

		JPanel norr = new JPanel();
		add(norr, BorderLayout.NORTH);
		hitta = new JButton("Hitta förbindelse");
		norr.add(hitta);	    	
		visa = new JButton("Visa förbindelse");	
		norr.add(visa);			
		nyPlats = new JButton("Ny plats");
		norr.add(nyPlats);			
		nyForb = new JButton("Ny förbindelse");
		norr.add(nyForb);			
		andraForb = new JButton("Ändra förbindelse");
		norr.add(andraForb);


		setLocation(350, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();

	}

	class NyttLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			filValj = new JFileChooser(".");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Bilder", "jpg", "gif","png");
			filValj.setFileFilter(filter);

			int svar = filValj.showOpenDialog(Reseplanerare.this);

			if (svar != JFileChooser.APPROVE_OPTION)
				return;

			File f = filValj.getSelectedFile();
			String filnamn = f.getAbsolutePath();
			if (karta != null)
				remove(karta);
			karta = new Karta(filnamn);

			menyHitta.addActionListener(new HittLyss());
			menyVisa.addActionListener(new VisaLyss());
			menyNyplats.addActionListener(new NyPlatsLyss());
			menyNyforb.addActionListener(new NyForbLyss());
			menyAndra.addActionListener(new AndrLyss());

			hitta.addActionListener(new HittLyss());
			visa.addActionListener(new VisaLyss());
			nyPlats.addActionListener(new NyPlatsLyss());
			nyForb.addActionListener(new NyForbLyss());
			andraForb.addActionListener(new AndrLyss());
			add(karta, BorderLayout.CENTER);
			validate();
			repaint();
			pack();
		}
	}


	class NyPlatsLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			karta.removeMouseListener(mlnp);
			karta.setCursor(Cursor.getDefaultCursor());
			karta.addMouseListener(mlnp = new MusLyssNyPlats());
			karta.setCursor(Cursor.getPredefinedCursor(CROSSHAIR_CURSOR));

		}
	}


	class NyForbLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			FormularNyForbindelse form = new FormularNyForbindelse();
			for(;;){
				try{
					if (p1 == null || p2 == null){
						JOptionPane.showMessageDialog(karta, "Välj två platser");
						break;
					}

					if (g.getEdgeBetween(p1, p2)!=null){
						JOptionPane.showMessageDialog(karta, "Finns redan en förbindelse mellan dessa platser");
						break;
					}

					int i = showConfirmDialog(Reseplanerare.this, form, "Lägg till en förbindelse", JOptionPane.OK_CANCEL_OPTION);				
					if(i == 0 && !form.getTyp().isEmpty() && !form.getTidAsString().isEmpty()){

						g.connect(p1, p2, form.getTyp(), form.getTid());
						karta.removeMouseListener(mlnp);
						karta.setCursor(Cursor.getDefaultCursor());
						nyPlats.setEnabled(true);
						break;
					}
					if (i == 0 && form.getTyp().isEmpty())
						showMessageDialog(Reseplanerare.this, "Skriv in en typ av förbindelse", "Fel", JOptionPane.ERROR_MESSAGE);
					if (i == 0 && form.getTidAsString().isEmpty())
						showMessageDialog(Reseplanerare.this, "Skriv in en tid", "Fel", JOptionPane.ERROR_MESSAGE);
					if (i == 2)
						break;
					if (i == -1)
						break;	

				}catch(NumberFormatException e){
					showMessageDialog(Reseplanerare.this, "Du kan inte mäta tid med bokstäver" , "Fel!", JOptionPane.ERROR_MESSAGE);
				}
				catch(IllegalArgumentException ie){
					showMessageDialog(Reseplanerare.this, ie.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}


	class AndrLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			if(p1 == null || p2 == null){
				showMessageDialog(karta, "Välj två platser");
			}else{
				for(;;){
					try{
						FormularAndra form = new FormularAndra();
						Edge edge = g.getEdgeBetween(p1, p2);
						if(edge == null){
							showMessageDialog(karta, "Finns ingen förbindelse mellan dessa två platser");
							break;
						}else{
							form.setTyp(edge.getName());
							int i = showConfirmDialog(Reseplanerare.this, form, "Ändra förbindelse", JOptionPane.OK_CANCEL_OPTION);
							if(i == 0 && !form.getTidAsString().isEmpty()){	  			
								g.setConnectionWeight(p1, p2, form.getTid());
								break;
							}
							if(i == 0 && form.getTidAsString().isEmpty())
								showMessageDialog(Reseplanerare.this, "Skriv in en tid", "Fel", JOptionPane.ERROR_MESSAGE);
							if (i == 2)
								break;
							if(i == -1)
								break;	
						}

					}catch(NumberFormatException e){
						showMessageDialog(Reseplanerare.this, "Du kan inte mäta tid i bokstäver" , "Fel!", JOptionPane.ERROR_MESSAGE);
					}
					catch(IllegalArgumentException ie){
						showMessageDialog(Reseplanerare.this, ie.getMessage(), "Fel", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}


	class VisaLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			if (p1 == null || p2 == null){
				JOptionPane.showMessageDialog(karta, "Välj två platser");
			}
			else{
				Edge edge = g.getEdgeBetween(p1, p2);
				if(edge == null){
					showMessageDialog(karta, "Finns ingen förbindelse mellan dessa två platser");
				}else{
					FormularVisaForb form = new FormularVisaForb();
					form.setInfo("Förbindelsen mellan " + p1.getNamn() + " och " + p2.getNamn() + ":");				
					form.setTyp(edge.getName());
					form.setTid(edge.getWeight());
					showMessageDialog(Reseplanerare.this, form, "Förbindelseinfo", JOptionPane.OK_OPTION);				
					//p1.setVald(false);
					//p2.setVald(false);
					//p1 = p2 = null;
				}
			}
		}
	}

	class HittLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){

			if (p1 == null || p2 == null){
				JOptionPane.showMessageDialog(karta, "Välj två platser");
			}
			else{
				boolean finns = g.pathExists(p1, p2);
				if(!finns){
					showMessageDialog(karta, "Finns ingen väg att finna mellan dessa två platser");
				}
				if(finns){
					LinkedList<Edge> väg = new LinkedList<Edge>();
					väg = g.getAPath(p1, p2);
					FormularHitta form = new FormularHitta();
					form.setPlats1(p1.getNamn());
					form.setPlats2(p2.getNamn());
					int total = 0;
					for(Edge ed : väg){
						//form.setText(ed.getName() + " till " + ed.getDestination() + ed.getWeight() + "\n");
						form.setText(ed.toString());
						total += ed.getWeight();
					}
					form.setText("Totalt " + total);
					showMessageDialog(Reseplanerare.this, form, "Hitta väg", JOptionPane.OK_CANCEL_OPTION);
				}
			}		
		}
	}


	class MusLyssNyPlats extends MouseAdapter{
		public void mouseClicked(MouseEvent mev){  

			int x = mev.getX();
			int y = mev.getY();
			FormularNyPlats form = new FormularNyPlats();
			for(;;){
				try{  			
					int i = showConfirmDialog(Reseplanerare.this, form, "Lägg till en plats", JOptionPane.OK_CANCEL_OPTION);
					if(i == 0 && !form.getNamn().isEmpty()){
						Plats p = new Plats(form.getNamn(), x, y);
						p.addMouseListener(mkpp);
						g.add(p);							
						JLabel platsNamn = new JLabel(form.getNamn());
						platsNamn.setForeground(Color.BLUE);
						platsNamn.setBounds(x-15, y-30, 200, 50);
						karta.add(platsNamn);
						karta.add(p);
						karta.removeMouseListener(mlnp);							
						karta.setCursor(Cursor.getDefaultCursor());
						nyPlats.setEnabled(true);
						validate();
						repaint();
						break;
					}
					else if (i == 0 && form.getNamn().isEmpty()){
						showMessageDialog(Reseplanerare.this, "Skriv in ett namn", "Fel", JOptionPane.ERROR_MESSAGE);
						karta.removeMouseListener(mlnp);
						karta.setCursor(Cursor.getDefaultCursor());
					}
					if (i == 2){
						karta.removeMouseListener(mlnp);
						karta.setCursor(Cursor.getDefaultCursor());
						break;
					}
					if(i==-1){
						karta.removeMouseListener(mlnp);
						karta.setCursor(Cursor.getDefaultCursor());
						break;
					}

				}catch(IndexOutOfBoundsException e){
					showMessageDialog(Reseplanerare.this, e.getMessage(), "Fel!", JOptionPane.ERROR_MESSAGE);
				}
				catch(IllegalArgumentException iae){
					showMessageDialog(Reseplanerare.this, iae.getMessage(), "Fel!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	class MusKlickPaPlats extends MouseAdapter{
		public void mouseClicked(MouseEvent mev){
			Plats p = (Plats)mev.getSource();	
			if (p.isVald()){
				p.setVald(false);
				if (p1 == p){
					p1 = null;
				}else if(p2 == p){
					p2 = null;
				}
			}else if(!p.isVald()){
				if(p1 == null){
					p1 = p;
					p.setVald(true);
				}else if (p2 == null && p1 != p){
					p2 = p;
					p.setVald(true);								
				}
			}	
		}
	}   

	class AvslutaLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			System.exit(0); 		
		}
	}    

	public static void main(String[] args){
		new Reseplanerare();
	}
}
