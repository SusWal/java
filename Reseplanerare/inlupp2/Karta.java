package inlupp2;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Karta extends JPanel{
	private ImageIcon bg;

	public Karta(String filnamn){
		setLayout(null);
		bg = new ImageIcon(filnamn);
		int w = bg.getIconWidth();
		int h = bg.getIconHeight();
		setPreferredSize(new Dimension(w,h));
		setMaximumSize(new Dimension(w,h));
		setMinimumSize(new Dimension(w,h));
	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(bg.getImage(), 0, 0, this);
	}
}
