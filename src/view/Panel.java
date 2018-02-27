package view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JPanel;
import view.Panel.GraphXY;
import model.Calcul;

/**
 * classe d'affichage de modéle  choisi des bulles
 *
 */

public class Panel extends JPanel {
	
	private final int ECART = 30;
	private double[][] data ;
	private Calcul c ;
	private int w, h ; 

	//Constructeur
    public Panel(double[][] d, Calcul kmeans)
    {
    	this.data = d ;
    	this.c = kmeans ;
    	this.setSize(500, 500);
    	this.setBackground(Color.white);
    	this.setLayout(new GridLayout(1, 1));
    	this.add(new GraphXY());
    }
    
    //mthode pour dessiner les repres
	private void dessineRepere(Graphics2D g2, String coord) 
	{
		g2.drawLine(ECART, ECART, ECART, h-ECART);
        g2.drawLine(ECART, h-ECART, w-ECART, h-ECART);
        char[] c = coord.toCharArray();
    	g2.drawString(c[1]+"", ECART, ECART-15);
    	g2.drawString(c[0]+"", w-ECART+15, h-ECART);

    	if( c[1] == 'Z' )
    	{
    		for ( int i = h-ECART, description = getParent().getWidth()-850; i > ECART ; i-=9, description++)
            {
            	g2.drawLine(ECART,i,ECART+5,i);
            	g2.drawString(description+"", ECART-25,i);
            }
    	}
    	else
    	{
    		for ( int i = h-ECART, description = 0; i > ECART ; i-=30, description++)
            {
            	g2.drawLine(ECART,i,ECART+5,i);
            	g2.drawString(description+"", ECART-25,i);
            }	
    	}
        
        for ( int i = ECART, description = 0; i < w-ECART ; i+=30, description++ )
        {
        	g2.drawLine(i, h-ECART, i, h-(ECART+5));
        	g2.drawString(description+"", i, h-(ECART-25));
        }
	}

	
	private void dessinePoints(Graphics2D g, String coord) 
	{
        for( ArrayList<double[]> d : c.getClusters())
        {	
    	    Polygon p = new Polygon();
    	    for(double[] i : d)
        	{
        	    double x = i[0];
    			double y = i[1];
        	    if(coord == "XY") {
        	    	g.setColor(Color.blue);
        	    	p.addPoint((ECART+(int)(x*30))+2, ((h-ECART)-(int)(y*30))+2);
        	    	g.fillOval(ECART+(int)(x*30), (h-ECART)-(int)(y*30), 5, 5);        	    
        	    }
        	    
        	}
        	
    	    g.drawPolygon(p);
        	
        }	
	}
	
	//classe interne efffectuant le trac des bulles dans le repre XY
	class GraphXY extends JPanel 
	{
		protected void paintComponent(Graphics g) 
	    {
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D)g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                            RenderingHints.VALUE_ANTIALIAS_ON);
	        w = getWidth();
	        h = getHeight();     
	        dessineRepere(g2, "XY");
	        dessinePoints(g2, "XY");	       
	    }
	}
	
}
