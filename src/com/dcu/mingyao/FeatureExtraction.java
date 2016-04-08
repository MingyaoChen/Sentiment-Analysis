package com.dcu.mingyao;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import Jama.EigenvalueDecomposition;
import Jama.Matrix;

/**
 * Preprocess.java
 * @version 1.0
 * @author Mingyao
 * @3-party-libs: Jama Library: Java Linear Algebra Library 
 * 				 TFIDF.java: Calculate the TF-IDF value of each 
 * @Desc	Read Original data in and store the processed data into file
 * 			Process Steps:
 * 				1. Bag of words algorithm to extract out features -- Mingyao
 * 				2. Feature Selection --Siyi
 * 				3. Dimension Reduction -- Mingyao
 * 				4. StoreToFile -- Siyi
 */
public class FeatureExtraction {
	//public static final int granularity = 2;
	private String data_path = "";
	private List<String> dic; 
	private List<String[]> data;
	private List<Integer> labels;
	private List<List<Double>> bow;
	
	/**
	 * Constructor
	 * @param data_path data path
	 */
	public FeatureExtraction(String data_path) {
		this.data_path = data_path;
		this.dic = new ArrayList<String>();
		this.data = new ArrayList<String[]>();
		this.labels = new ArrayList<Integer>();
		this.bow = new ArrayList<List<Double>>();
		loadData();
	}
	
	public void process() {
		this.loadData();
		this.bagOfWords();
		this.featureSelection();
		this.DimReduction();
		this.storeToFile(); // 
	}
	
	/**
	 * 1. Load data into memory
	 * 2. Create dictionary
	 */
	private void loadData() {
		FileReader fr;
		BufferedReader br;
		String line;
	
		try {
			fr = new FileReader(this.data_path);
			br = new BufferedReader(fr);
			
			while((line = br.readLine()) != null) {
				int label = Integer.parseInt(line.substring(0, 1));
				String content = line.substring(1).toLowerCase();
				content = content.replaceAll("[^A-Za-z]+", " ").trim();
				String[] items = content.split("\\s+");
				data.add(items);
				labels.add(label);
				for (int i = 0; i < items.length; i ++) {
					if(dic.contains(items[i]))
						continue;
					dic.add(items[i]);
				}
			}
			System.out.println(dic.get(0));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Iterate through the lines of data, and construct the bag of word matrix
	 * 
	 * TF-IDF
	 * The formula is:
	 * TF(t)  = Number of times t appears in the document / Number of words in the document
	 * IDF(t) = log_e(Total number of documents / Number of Documents containing t)
	 * The TFIDF(t) score of the term t is the multiple of those two.
	 */
	private void bagOfWords() {
		double tf = 0.0;
		double idf = 0.0;
		
		Iterator<String[]> data_ite = data.iterator();
		while(data_ite.hasNext()) {
			List<Double> row = new ArrayList<Double>();
			String[] line = data_ite.next();
			Iterator<String> ite = dic.iterator();
			while(ite.hasNext()) {
				String tmp = ite.next();
				tf = TFIDF.tfCaculator(line, tmp);
				idf = TFIDF.idfCalculator(data, tmp);
				row.add(tf*idf);
			}
			this.bow.add(row);
		}
	}

	/**
	 * Siyi 
	 * 
	 */
	private void featureSelection() {
		
	}
	
	/**
	 * Reduce the dimension of data allow easier classification, PCA if possible
	 * Rely on JAMA pacakge
	 * 1. Zero Mean
	 * 2. Cov Matrix
	 * 3. Eigen value Eigen vector
	 */
	private void DimReduction() {
		// Initialization
		double[][] origin = new double[bow.size()][];
		double[][] zeroMean = new double[bow.size()][];
		
		// 2D list to 2D array
		Iterator<List<Double>> ite = bow.iterator();
		int index = 0;
		while(ite.hasNext()) {
			origin[index] = array_O2D(ite.next().toArray());
			index ++;
		}
		
		System.out.println(origin.length);
		System.out.println(origin[0].length);
		
		// 1. Zero Mean
		Matrix mat = new Matrix(origin);
		Matrix matT = mat.transpose();
		double[][] originT = matT.getArray();
		double[][] zeroMeanT = new double[originT.length][];
		for(int i =0; i < originT.length; i ++) {
			zeroMeanT[i] = equalization(originT[i]); 
		}
		Matrix zt = new Matrix(zeroMeanT);
		zeroMean = zt.transpose().getArray();
		
		// 2. cov matrix
		Matrix m = new Matrix(zeroMean);
		Matrix cov = m.times(m.transpose());
		
		double[][] tmp = cov.getArray();
		
		// 3. Eigen value and Eigen vector
		EigenvalueDecomposition e = new EigenvalueDecomposition(cov);
		double[] eigenValue = e.getRealEigenvalues();
		//Matrix eigenVectors = e.getV();
		
		System.out.println(eigenValue.length);
		for (int i =0; i < 100; i ++) {
			System.out.println(eigenValue[i]);
		}
	}
	
	private double[] equalization(double[] ds) {
		double m = this.mean(ds);
		double[] n = new double[ds.length];
		for (int i = 0; i < ds.length; i ++) {
			n[i] = ds[i] - m;
		}
		return n;
	}

	/**
	 * Store the processed data into .csv format file, 
	 * fields separated by ","
	 * index of class: 0
	 */
	private void storeToFile() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Calculate the mean of an array
	 * @param m
	 * @return
	 */
	private double mean(double[] m) {
	    double sum = 0;
	    for (int i = 0; i < m.length; i++) {
	        sum += m[i];
	    }
	    return sum / m.length;
	}
	
	/**
	 * Convert Object array to Double array
	 * @param arr : object array
	 * @return : double array
	 */
	private double[] array_O2D(Object[] arr) {
		double[] tmp = new double[arr.length];
		for (int i = 0; i < arr.length; i ++) {
			tmp[i] = (double)arr[i];
		}
		return tmp;
	}

	// getters and setters
	public String getdata_path() {
		return data_path;
	}
	public void setdata_path(String data_path) {
		this.data_path = data_path;
	}
}