package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.pascalbrandt.dsm.web.fbo.SVMConfigurationForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SelectedTag;

@Service("SVMService")
public class SVMService implements ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(SVMService.class);
	private ApplicationContext context;
	
	private LibSVM svm = null;
	
	public LibSVM getSVM() {
		if (svm == null)
			svm = createSVM();
		return svm;
	}
	
	public Map<Integer, String> getSVMTypes() {
		HashMap<Integer, String> svmTypes = new HashMap<Integer, String>();
		
		svmTypes.put(1, "nu-SVC");
		svmTypes.put(2, "one-class SVM");
		svmTypes.put(3, "epsilon-SVR");
		svmTypes.put(4, "nu-SVR");
		
		return svmTypes;
	}
	
	public Map<Integer, String> getKernelTypes() {
		HashMap<Integer, String> kernelTypes = new HashMap<Integer, String>();
		
		kernelTypes.put(2, "Radial Basis");
		kernelTypes.put(0, "Linear");
		kernelTypes.put(1, "Polynomial");		
		kernelTypes.put(3, "Sigmoid");
		
		return kernelTypes;
	}
	
	public Evaluation evaluateClassifier(SVMConfigurationForm config) {
		logger.info("Evaluating Classifier");
		
		// Initialize the SVM
		svm = getSVM();
		
		// FIXME: Figure this out!
		//svm.setNu(0.001);
		
		// Set the parameters
		svm = setParameters(svm, config.getType(), config.getKernel(), config.getGamma(), config.getC());
		
		// Build attribute vector
		ArrayList<Attribute> attributes = AttributeFactory.constructAttributeList(config);
		
		// FIXME: This is just for debugging
		AttributeFactory.displayAttributeList(attributes);
		
		// Train
		DataService ds = context.getBean(DataService.class);
		//Instances trainingData = ds.getTrainingData();
		Instances trainingData = ds.getPatientTrainingInstances(RegaService.AC_FAILURE_CLINIC_DATASET_DESCRIPTOR, attributes);
		svm = train(svm, trainingData);		
		
		// Test
		return testSVM(svm, trainingData);
	}
	
	public Evaluation crossValidateClassifier(SVMConfigurationForm config, int numFolds, Random random) {
		logger.info("Cross Validating SVM with " + numFolds + " folds");
		
		// Initialize the SVM
		svm = getSVM();
		
		// Set the parameters
		svm = setParameters(svm, config.getType(), config.getKernel(), config.getGamma(), config.getC());
		
		// Build attribute vector
		ArrayList<Attribute> attributes = AttributeFactory.constructAttributeList(config);
		
		// FIXME: This is just for debugging
		AttributeFactory.displayAttributeList(attributes);

		// Train
		DataService ds = context.getBean(DataService.class);

		Instances trainingData = ds.getPatientTrainingInstances(RegaService.AC_FAILURE_CLINIC_DATASET_DESCRIPTOR, attributes);

		svm = train(svm, trainingData);		
		
		Evaluation eval = null;
		try {
	        eval = new Evaluation(trainingData);
        }
        catch (Exception e) {
        	logger.error(e.getMessage());
	        e.printStackTrace();
        }
		
		try {
	        eval.crossValidateModel(svm, trainingData, numFolds, random);
        }
        catch (Exception e) {
        	logger.error(e.getMessage());
	        e.printStackTrace();
        }
		
		return eval;
	}	
	
	
	public Evaluation classify(Integer type, Integer kernel, Double gamma, Double C) {
		logger.info("Classifying");
		
		DataService ds = context.getBean(DataService.class);
		
		// Initialize the SVM
		svm = getSVM();
		
		// Set the parameters
		svm = setParameters(svm, type, kernel, gamma, C);
		
		// Train
		//Instances trainingData = ds.getTrainingData();
		Instances trainingData = ds.getPatientTrainingData(RegaService.AC_FAILURE_CLINIC_DATASET_DESCRIPTOR);
		svm = train(svm, trainingData);		
		
		// Test
		return testSVM(svm, trainingData);
	}
	
	public Evaluation testSVM(LibSVM svm, Instances trainingData) {
		logger.info("Testing SVM");
		
		DataService ds = context.getBean(DataService.class);
		
		Evaluation eval = null;
		try {
	        eval = new Evaluation(trainingData);
        }
        catch (Exception e) {
        	logger.error(e.getMessage());
	        e.printStackTrace();
        }
		
		try {
	        eval.evaluateModel(svm, ds.getTestingData());
        }
        catch (Exception e) {
        	logger.error(e.getMessage());
	        e.printStackTrace();
        }
		
		return eval;
	}
	
	public Evaluation crossValidate(Integer type, Integer kernel, Double gamma, Double C, int numFolds, Random random) {
		logger.info("Cross Validating SVM with " + numFolds + " folds");
		
		// Initialize the SVM
		svm = getSVM();
		
		// Set the parameters
		svm = setParameters(svm, type, kernel, gamma, C);
		
		DataService ds = context.getBean(DataService.class);
		
		Instances data = ds.getPatientTrainingData(RegaService.AC_FAILURE_CLINIC_DATASET_DESCRIPTOR);
		
		// Train
		//Instances trainingData = ds.getTrainingData();
		//Instances trainingData = ds.getPatientTrainingData(RegaService.AC_FAILURE_CLINIC_DATASET_DESCRIPTOR);
		svm = train(svm, data);		
		
		Evaluation eval = null;
		try {
	        eval = new Evaluation(data);
        }
        catch (Exception e) {
        	logger.error(e.getMessage());
	        e.printStackTrace();
        }
		
		try {
	        eval.crossValidateModel(svm, data, numFolds, random);
        }
        catch (Exception e) {
        	logger.error(e.getMessage());
	        e.printStackTrace();
        }
		
		return eval;
	}
	
	public LibSVM createSVM() {
		logger.info("Creating SVM");
		
		return new LibSVM();
	}
	
	public LibSVM setParameters(LibSVM svm, Integer type, Integer kernel, Double gamma, Double C) {
		logger.info("Setting SVM parameters");
		
		// Set the SVM Type
		svm.setSVMType(new SelectedTag(type, LibSVM.TAGS_SVMTYPE));
		
		// Set the kernel Type
		svm.setKernelType(new SelectedTag(kernel, LibSVM.TAGS_KERNELTYPE));
		
		// TODO: Make more this work for other kernel types
		svm.setGamma(gamma);
		svm.setCost(C);
		
		return svm;
	}
	
	public LibSVM train(LibSVM svm, Instances data) {
		logger.info("Training SVM");
		
		try {
			svm.buildClassifier(data);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}			
			
		return svm;
	}

	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
    }
	
}
