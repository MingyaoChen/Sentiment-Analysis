package com.dcu.mingyao;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Preprocess.java
 * @author Mingyao
 * 1. Quantitation
 * 2. Weighting
 *
 */
public class Preprocess {
	public static final int granularity = 2;
	private String data_path = "";
	private List<String> dic; 
	private List<String[]> data;
	private List<Integer> labels;
	private List<List<Double>> bow;
	
	public Preprocess(String data_path) {
		this.data_path = data_path;
		this.dic = new ArrayList<String>();
		this.data = new ArrayList<String[]>();
		this.labels = new ArrayList<Integer>();
		this.bow = new ArrayList<List<Double>>();
		loadData();
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
				String content = line.substring(1);
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
	public void bagOfWords() {
		double tf = 0.0;
		double idf = 0.0;
		
		List<Double> row = new ArrayList<Double>();
		Iterator<String[]> data_ite = data.iterator();
		while(data_ite.hasNext()) {
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
	 * 
	 * 
	 */
	public void featureSelection() {
		
	
	}
	
	/**
	 * Reduce the dimension of data allow easier classification, PCA
	 */
	public void DimReduction() {
		
	}

	public String getdata_path() {
		return data_path;
	}

	public void setdata_path(String data_path) {
		this.data_path = data_path;
	}

	public List<List<Double>> getbow() {
		return bow;
	}

	public void setbow(List<List<Double>> bow) {
		this.bow = bow;
	}
}
