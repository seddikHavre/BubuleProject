package model;
import java.util.Random;
import java.util.ArrayList;

/**
 * Classe de tracer des trajectoires des particules 
 *
 */

public class Calcul {
	//tableau de donnes
	private double[][] data;
	//numro de cluster
	private int numClusters;
	//tableau bidimensionnel
	private double[][] clusterCenters;
	//taille des donnes
	private int dataSize;
	private int dataDim;
	private ArrayList[] clusters;
	private double[] clusterVars; 
	// epsilon
	private double epsilon;

	//Constructeur de la classe
	public Calcul(double[][] data, int numClusters, double[][] clusterCenters) {
		dataSize = data.length;
		dataDim = data[0].length;

		this.data = data;

		this.numClusters = numClusters;

		this.clusterCenters = clusterCenters;

		clusters = new ArrayList[numClusters];
		for (int i = 0; i < numClusters; i++) {
			clusters[i] = new ArrayList();
		}
		clusterVars = new double[numClusters];

		epsilon = 0.01;
	}

	//Constructeur de la classe
	public Calcul(double[][] data, int numClusters) {
		this(data, numClusters, true);
	}

	//Constructeur de la classe
	public Calcul(double[][] data, int numClusters, boolean randomizeCenters) {
		dataSize = data.length;
		dataDim = data[0].length;

		this.data = data;

		this.numClusters = numClusters;

		this.clusterCenters = new double[numClusters][dataDim];

		clusters = new ArrayList[numClusters];
		for (int i = 0; i < numClusters; i++) {
			clusters[i] = new ArrayList();
		}
		clusterVars = new double[numClusters];

		epsilon = 0.01;

		if (randomizeCenters) {
			randomizeCenters(numClusters, data);
		}
	}

	//mthode qui remplit de manire alatoire le tab clusterCenters
	private void randomizeCenters(int numClusters, double[][] data) {
		Random r = new Random();
		int[] check = new int[numClusters];
		for (int i = 0; i < numClusters; i++) {
			int rand = r.nextInt(dataSize);
			if (check[i] == 0) {
				this.clusterCenters[i] = data[rand].clone();
				check[i] = 1;
			} else {
				i--;
			}
		}
	}

	//mthode pour calculer
	private void calculateClusterCenters() {
		for (int i = 0; i < numClusters; i++) {
			int clustSize = clusters[i].size();

			for (int k = 0; k < dataDim; k++) {

				double sum = 0d;
				for (int j = 0; j < clustSize; j++) {
					double[] elem = (double[]) clusters[i].get(j);
					sum += elem[k];
				}

				clusterCenters[i][k] = sum / clustSize;
			}
		}
	}

	private void calculateClusterVars() {
		for (int i = 0; i < numClusters; i++) {
			int clustSize = clusters[i].size();
			Double sum = 0d;

			for (int j = 0; j < clustSize; j++) {

				double[] elem = (double[]) clusters[i].get(j);

				for (int k = 0; k < dataDim; k++) {
					sum += Math.pow((Double) elem[k] - getClusterCenters()[i][k], 2);
				}
			}

			clusterVars[i] = sum / clustSize;
		}
	}

	//getter
	public double getTotalVar() {
		double total = 0d;
		for (int i = 0; i < numClusters; i++) {
			total += clusterVars[i];
		}
		return total;
	}

	//getter
	public double[] getClusterVars() {
		return clusterVars;
	}

	public ArrayList[] getClusters() {
		return clusters;
	}

	
	private void assignData() {
		for (int k = 0; k < numClusters; k++) {
			clusters[k].clear();
		}
		
		for (int i = 0; i < dataSize; i++) {
			int clust = 0;
			double dist = Double.MAX_VALUE;
			double newdist = 0;

			for (int j = 0; j < numClusters; j++)
			{
				if (clusters[j].size() < 5 )
				{
					
			        newdist = distToCenter(data[i], j);
					if (newdist <= dist  )
					{
						clust = j;
						dist = newdist;
					}
				}
			}

			clusters[clust].add(data[i]);
		}

	}

	private double calculAngle(double pAnglex, double pAngley, double p1x, double p1y, double p2x, double p2y ) 
	{
		// Calcul des distances
		double pAp1 = Math.sqrt(Math.abs( Math.pow((pAnglex-p1x),2) + Math.pow((pAngley-p1y),2) ));
		double p1p2 = Math.sqrt(Math.abs( Math.pow((p1x-p2x),2) + Math.pow((p1y-p2y),2) ));
		double p2pA = Math.sqrt(Math.abs( Math.pow((p2x-pAnglex),2) + Math.pow((p2y-pAngley),2) ));
		
		double cospAngle = (Math.pow(pAp1,2) + Math.pow(p2pA,2) - Math.pow(p1p2,2)) / (2*pAp1*p2pA);
		double cosAngle = Math.acos(cospAngle);
		  
		double angle = cosAngle*(180/Math.PI);
	  
		return angle ;
	}
	
	private double distToCenter(double[] datum, int j) {
		double sum = 0d;
		for (int i = 0; i < dataDim; i++) {
			sum += Math.pow((datum[i] - getClusterCenters()[j][i]), 2);
		}

		return Math.sqrt(sum);
	}

	
	public void calculateClusters() {

		double var1 = Double.MAX_VALUE;
		double var2;
		double delta;

		int i = 0 ;
		do {
			calculateClusterCenters();
			assignData();
			calculateClusterVars();
			var2 = getTotalVar();
			if (Double.isNaN(var2))
			{
				delta = Double.MAX_VALUE;
				randomizeCenters(numClusters, data);
				assignData();
				calculateClusterCenters();
				calculateClusterVars();
			} else {
				delta = Math.abs(var1 - var2);
				var1 = var2;
			}

			i++ ;
			System.out.println(i);
		} while (delta > epsilon && i < 10000);
	}
	
	public void setEpsilon(double epsilon) {
		if (epsilon > 0) {
			this.epsilon = epsilon;
		}
	}

	public int getNumClusters() {
		return numClusters;
	}
	
	public double[][] getClusterCenters() {
		return clusterCenters;
	}
}
