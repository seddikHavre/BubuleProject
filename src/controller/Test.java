package controller;
import java.io.BufferedInputStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JFrame;
import view.Panel;
import model.Calcul;


/**
 * Classe de test contenant la mthode main du projet
 *
 */

public class Test {
	public static void main(String[] args) throws IOException
	{
		System.out.println("**********Menu****************");
		System.out.println("0-Coordonnees_mm/norma_N5_tau4_dt2_delai820_000000");
		System.out.println("1-Coordonnees_mm/norma_N5_tau4_dt2_delai820_000001");
		System.out.println("2-Coordonnees_mm/norma_N5_tau4_dt2_delai820_000002.txt");
		System.out.println("3-Coordonnees_mm/norma_N5_tau4_dt2_delai820_000003");
		System.out.println("4-Coordonnees_mm/norma_N5_tau4_dt2_delai820_000004");
		System.out.println("5-Coordonnees_mm/norma_N5_tau4_dt2_delai820_000005");
		System.out.println("6-Coordonnees_mm/norma_N5_tau4_dt2_delai820_000006");
		System.out.println("7-Coordonnees_mm/norma_N5_tau4_dt2_delai820_000007");
		System.out.println("8-Coordonnees_mm/norma_N5_tau4_dt2_delai820_000008");
		System.out.println("9-Coordonnees_mm/norma_N5_tau4_dt2_delai820_000009");

		System.out.println("Saisire un modéle de bulles que tu veut analysé entre 0 et 9");
	
		
		
		
		//Chargement d'un fichier de coordonnes des bulles
		
		File file = getfile();
				//prsentant les donnes
		double[][] data = new double[Test.countLines(file)][3];
		//Lecture du fichier
	    FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        int numLine = 0 ;
        for (String line = br.readLine(); line != null; line = br.readLine())
        {
        	int numCol = 0 ;
        	
           	for( String s : line.split(" "))
           	{
           		if( !s.isEmpty() && numCol < 3)
           		{
           			data[numLine][numCol] = Double.parseDouble(s);
           			numCol++ ;
           		}
           	}
        	numLine++ ;
        }

        br.close();
        fr.close();
        
        for ( double[] d : data)
        {
        	for( double v: d)
        	{
        		System.out.print(v+" |||||| ");
        	}
        	System.out.println();
        }
        
        
        Calcul c = new Calcul(data, Test.countLines(file)/5, true);
        c.setEpsilon(0.01);
        c.calculateClusters();
        
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocation(50,50);
        f.add(new Panel(data, c));
        f.setSize(550,530);

        f.setVisible(true);
	}
	
	
	public static File getfile(){
		 int i;
			 File file = null;
			Scanner sc = new Scanner(System.in);
			i=sc.nextInt();
			
			switch (i){
			
			case 0 : file=new File("Coordonnees_mm/norma_N5_tau4_dt2_delai820_000000.txt");
			break;
			case 1 : file=new File("Coordonnees_mm/norma_N5_tau4_dt2_delai820_000001.txt" );
			break;
			case 2 : file=new File("Coordonnees_mm/norma_N5_tau4_dt2_delai820_000002.txt");
			break;
			case 3 : file=new File("Coordonnees_mm/norma_N5_tau4_dt2_delai820_000003.txt");
			break;
			case 4 : file=new File("Coordonnees_mm/norma_N5_tau4_dt2_delai820_000004.txt");
			break;
			case 5 : file=new File("Coordonnees_mm/norma_N5_tau4_dt2_delai820_000005.txt");
			break;
			case 6 : file=new File("Coordonnees_mm/norma_N5_tau4_dt2_delai820_000006.txt");
			break;
			case 7 : file=new File("Coordonnees_mm/norma_N5_tau4_dt2_delai820_000007.txt");
			break;
			case 8 : file=new File("Coordonnees_mm/norma_N5_tau4_dt2_delai820_000008.txt");
			break;
			case 9 : file=new File("Coordonnees_mm/norma_N5_tau4_dt2_delai820_000009.txt");
			break;
			
			}
			
			return file ;
						
		}
	
	
	//mthode pour compter le nombre de lignes dans un fichier
	public static int countLines(File file) throws IOException 
	{
	    InputStream is = new BufferedInputStream(new FileInputStream(file));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
}
