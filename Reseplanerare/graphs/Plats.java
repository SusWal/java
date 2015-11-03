package graphs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Plats extends JComponent{
	private boolean vald = false;
	private String namn;
	private int x;
	private int y;

	public Plats(String namn, int x, int y){
		this.namn = namn;
		this.x = x;
		this.y = y;

		setBounds(x,y,30,30);
		setPreferredSize(new Dimension(30,30));
		setMaximumSize(new Dimension(30,30));
		setMinimumSize(new Dimension(30,30));
	}

	protected void visaPlats(Graphics g){
		g.setColor(Color.RED);
		g.fillOval(0,0,getWidth(),getHeight());

	}    

	protected void paintComponent(Graphics g){
		super.paintComponent(g);


		if (vald){
			g.setColor(Color.RED);
			g.fillOval(0,0,getWidth(),getHeight());
		}
		else{
			g.setColor(Color.BLUE);
			g.fillOval(0,0,getWidth(),getHeight());
			g.drawString(namn, x, y);    		
		}
	}

	public void setVald(boolean b){
		vald = b;

		repaint();
	}

	public boolean isVald(){
		return vald;
	}

	public String getNamn(){ 
		return namn; 
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public String toString(){
		String str = namn + "";
		return str;
	}

}
