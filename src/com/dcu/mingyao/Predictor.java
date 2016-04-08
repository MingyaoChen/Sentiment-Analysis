package com.dcu.mingyao;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import libsvm.LibSVM;
import libsvm.svm_parameter;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;


/**
 * User SVM to train and predict the data
 * @author mingyao
 *
 */
public class Predictor {
	
	/**
	 * 
	 * @param path : training file path
	 * @param classIndex: index of class
	 * @param separator: separator of fields
	 * @return SVM model
	 */
	public LibSVM svmTrain(String path, int classIndex, String separator) {  
		LibSVM svm = new LibSVM();
		svm_parameter param = new svm_parameter();
	    param.probability = 1;
	    param.gamma = 0.5;
	    param.nu = 0.5;
	    param.C = 1;
	    param.svm_type = svm_parameter.C_SVC;
	    param.kernel_type = svm_parameter.LINEAR;       
	    param.cache_size = 20000;
	    param.eps = 0.001;      
		svm.setParameters(param);
		
		
		try {
			Dataset training = FileHandler.loadDataset(new File(path), classIndex, separator);
			svm.buildClassifier(training);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
       return svm;
	}
	
	/**
	 * 
	 * @param model SVM model
	 * @param test_path : test file path
	 * @param classIndex : index of class
	 * @param separator : separator of fields
	 */
	public void evaluate(LibSVM model, String test_path, int classIndex, String separator) {
		
		Dataset dataForClassification;
		try {
			dataForClassification = FileHandler.loadDataset(new File(test_path), classIndex, separator);
			Map<Object, PerformanceMeasure> pm = EvaluateDataset.testDataset(model, dataForClassification);
			for(Object o:pm.keySet())
			    System.out.println(o+": "+pm.get(o).getAccuracy());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}
}
