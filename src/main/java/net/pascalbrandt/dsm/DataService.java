package net.pascalbrandt.dsm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.pascalbrandt.dsm.web.fbo.SVMConfigurationForm;
import net.sf.regadb.db.Dataset;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.PatientAttributeValue;
import net.sf.regadb.db.TestResult;
import net.sf.regadb.db.Therapy;
import net.sf.regadb.util.date.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;

@Service("DataService")
public class DataService implements ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(DataService.class);
	private ApplicationContext context;
	
	public static Integer PATIENT_COUNT;
	public static Integer RESISTANT_COUNT;
	public static Integer NOT_RESISTANT_COUNT;
	public static Integer UNLABELLED_COUNT;
	
	public static final String REGA_RESISTANCE_TEST = "REGA v8.0.1";
	public static final Integer REGA_RESISTANCE_TEST_ID1 = 51;
	public static final Integer REGA_RESISTANCE_TEST_ID2 = 54;
	
	public static final Integer REGA_GSS_TEST_TYPE_ID1 = 45;
	public static final Integer REGA_GSS_TEST_TYPE_ID2 = 46;
	
	public static final Integer ATTRIBUTE_INDEX_CLASS = 0;
	public static final Integer ATTRIBUTE_INDEX_ETHNICITY = 1;
	public static final Integer ATTRIBUTE_INDEX_GENDER = 2;
	public static final Integer ATTRIBUTE_INDEX_AGE = 3;	
	public static final Integer ATTRIBUTE_COUNT = 4;
	
	public static final String CLASS_RESISTANT = "Resistant";
	public static final String CLASS_NOT_RESISTANT = "Not Resistant";
	public static final String CLASS_UNLABELED = "Unlabeled";
	
	public static final String ETHNICITY_AFRICAN = "African";
	public static final String ETHNICITY_ASIAN = "Asian";
	public static final String ETHNICITY_CAUCASIAN = "Caucasian";
	
	public static final String GENDER_MALE = "Male";
	public static final String GENDER_FEMALE = "Female";
	
	public static ArrayList<Attribute> attributes = null;

	/**
	 * Determine whether or not is resistant.
	 * 
	 * Is the patient resistant to any of the drugs they were on
	 * at the time of their latest sequence analysis
	 * 
	 * @return
	 */
	public String classifyPatient(Patient p) {
		logger.info("Classifying Patient [" + p.getPatientId() + "]");
		
		RegaService rs = context.getBean(RegaService.class);
		
		List<TestResult> results = rs.getLatestGSSTestResults(p);
				
		Date latestGSSDate;
		
		if (results.size() == 0) {
			// If there the has no sequence analysis results, we don't know their class
			logger.info("No GSS Test Results Available");
			return CLASS_UNLABELED;
		} else {
			latestGSSDate = results.get(0).getTestDate();
			logger.info("Latest Rega Test Date: " + latestGSSDate);		
		}
		
		Therapy therapyAtGSSTestDate = rs.getTherapyAtDate(p, latestGSSDate);
		
		if (rs.isResistantToTherapy(therapyAtGSSTestDate, results)) {
			logger.info("Resistance detected");
			return CLASS_RESISTANT;
		} else {
			logger.info("No resistance detected");
			return CLASS_NOT_RESISTANT;
		}
			
		//return CLASS_UNLABELED;
		/*for (TestResult testResult : tests) {
			if (testResult.getTest().getDescription().equals(REGA_RESISTANCE_TEST)) {
				if (latestSequenceAnalysisResults == null || latestSequenceAnalysisResults.getTestDate().before(testResult.getTestDate()))
						latestSequenceAnalysisResults = testResult;
			}
		}		
		
		// This patient has no Rega 8.0.1 test result, so we can't determine if they're resistant or not
		if (latestSequenceAnalysisResults == null) {
			logger.info("Patient has no " + REGA_RESISTANCE_TEST + " test results");
			return null;
		}
		
		// Find therapy at time of sequencing
		/*Therapy therapyAtTimeOfLatestSequenceAnalysis = null;
		for (Therapy therapy : therapies) {
			if (
				// The start date of the therapy is on or before the test date
				((therapy.getStartDate().before(latestSequenceAnalysisResults.getTestDate()))  ||
			     (therapy.getStartDate().equals(latestSequenceAnalysisResults.getTestDate()))) &&
			    // The stop date of the therapy is on or after the test date or the therapy is ongoing
			    ((therapy.getStopDate() == null) ||
			     (therapy.getStopDate().after(latestSequenceAnalysisResults.getTestDate())) ||
			     (therapy.getStopDate().equals(latestSequenceAnalysisResults.getTestDate())))
			   ) {
					therapyAtTimeOfLatestSequenceAnalysis = therapy;
			}
		}*/
		
		
	}
	
	public Instances buildPatientTrainingDataset(String dataset, SVMConfigurationForm config) {
		logger.info("Building Patient Training Dataset");
		// 
		
		return null;
	}
	
	/**
	 * Main entry point to the data preprocessing phase
	 * 
	 * @return The preprocessed data to be used to train the SVM
	 */
	public Instances getPatientTrainingData(String datasetDescription) {
		logger.info("Getting Patient Training Data for Dataset: " + datasetDescription);
		Instances data = new Instances("Patient Dataset", getAttributes(), ATTRIBUTE_INDEX_CLASS);
		
		RegaService rs = context.getBean(RegaService.class);
		List<Patient> patients = null;
		
		try {
			patients = rs.getPatients(rs.getDataset(datasetDescription));
        }
        catch (Exception e) {
	        logger.error(e.getMessage());
	        e.printStackTrace();
	        return null;
        }
		
		int i = 0;
		for (Patient p : patients) {
			logger.info("Adding patient["+ i++ +"]: " + p.getPatientId());
			data.add(constuctInstanceFromPatient(p));
		}
		
		data.setClassIndex(ATTRIBUTE_INDEX_CLASS);
		
		return data;
	}
	
	public Map<String, Integer> getDatasetLabels(String datasetDescription) {
		HashMap<String, Integer> labelCount = new HashMap<String, Integer>();
		
		labelCount.put(CLASS_RESISTANT, 0);
		labelCount.put(CLASS_NOT_RESISTANT, 0);
		labelCount.put(CLASS_UNLABELED, 0);
		
		RegaService rs = context.getBean(RegaService.class);
		List<Patient> patients = null;
		
		try {
			patients = rs.getPatients(rs.getDataset(datasetDescription));
        }
        catch (Exception e) {
	        logger.error(e.getMessage());
	        e.printStackTrace();
	        return null;
        }
		
		for (Patient p : patients) {
			String label = classifyPatient(p);
			
			labelCount.put(label, labelCount.get(label) + 1);
		}
		
		
		return labelCount;
	}
	
	/**
	 * @param patient
	 * @return
	 */
	public Instance constuctInstanceFromPatient(Patient patient) {
		Instance fVector = new SparseInstance(ATTRIBUTE_COUNT);
		
		Set<PatientAttributeValue> patientAttributes = patient.getPatientAttributeValues();
		
		for(PatientAttributeValue pav : patientAttributes) {

			// Ethnicity
			if(pav.getAttribute().getName().equals(RegaService.REGA_ATTRIBUTE_ETHNICITY)) {
				if(pav.getAttributeNominalValue().getValue().toUpperCase().equals(ETHNICITY_AFRICAN.toUpperCase())) {
					fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY), ETHNICITY_AFRICAN);
				} else if(pav.getAttributeNominalValue().getValue().toUpperCase().equals(ETHNICITY_ASIAN.toUpperCase())) {
					fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY), ETHNICITY_ASIAN);
				} else {
					fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY), ETHNICITY_ASIAN);
				}
			}
			
			// Gender
			if(pav.getAttribute().getName().equals(RegaService.REGA_ATTRIBUTE_GENDER)) {
				if(pav.getAttributeNominalValue().getValue().toUpperCase().equals(GENDER_MALE.toUpperCase())) {
					fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_GENDER), GENDER_MALE);
				} else {
					fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_GENDER), GENDER_FEMALE);
				}
			}
			
			// Age
			if(pav.getAttribute().getName().equals(RegaService.REGA_ATTRIBUTE_AGE)) {
				if(pav.getValue() == "")
					logger.info(">> No Age Attribute Found");
			
				fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_AGE), Integer.parseInt(pav.getValue()));
			}
		
		}
		
		/**
		 * Expert knowledge derived features
		 */
		// Weight Decrease
		
		// Time on fai	ling regimen
		
		// viral load never supressed
	
		// CD4 never recovered
		
		// TODO: Chat to Tulio to get a few more features
		
		// Class
		/*RegaService rs = context.getBean(RegaService.class);
		if(rs.isPatientResistantToCurrentTherapy(patient)) {
			fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS), CLASS_RESISTANT);
		} else {
			fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS), CLASS_NOT_RESISTANT);
		}*/
		
		String patientClass = classifyPatient(patient);
		
		if (patientClass.equals(CLASS_RESISTANT)) {
			fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS), CLASS_RESISTANT);
		} else if (patientClass.equals(CLASS_NOT_RESISTANT)) {
			fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS), CLASS_NOT_RESISTANT);
		}
		
		return fVector;
	}
	
	public Instances getTrainingData() {
		Instances data = new Instances("Training Dataset", getAttributes(), ATTRIBUTE_INDEX_CLASS);
		
		// FIXME: Add dummy item
		Instance dummy;
		
		for(int i = 0; i < 1000; i++) {
			dummy = new DenseInstance(getAttributes().size());
			
			// Ethnicity
			double rand = Math.random();
			if(rand < 0.33) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY), ETHNICITY_AFRICAN);
			} else if (rand < 0.66) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY), ETHNICITY_ASIAN);
			} else {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY), ETHNICITY_CAUCASIAN);
			}
			
			// Gender
			rand = Math.random();
			if(rand < 0.5) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_GENDER), GENDER_FEMALE);
			} else {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_GENDER), GENDER_MALE);
			}
			
			// Age
			rand = Math.random();
			dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_AGE), rand*100);
			
			// Class
			rand = Math.random();
			if(rand < 0.5) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS), CLASS_RESISTANT);
			} else {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS), CLASS_NOT_RESISTANT);
			}
			
			data.add(dummy);
		}
		
		data.setClassIndex(ATTRIBUTE_INDEX_CLASS);
		
		return data;
	}
	
	public Instances getTestingData() {
		Instances data = new Instances("Testing Dataset", getAttributes(), ATTRIBUTE_INDEX_CLASS);
		
		// FIXME: Add dummy item
		Instance dummy;
		
		for(int i = 0; i < 100; i++) {
			dummy = new DenseInstance(getAttributes().size());
			
			// Ethnicity
			double rand = Math.random();
			if(rand < 0.33) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY), ETHNICITY_AFRICAN);
			} else if (rand < 0.66) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY), ETHNICITY_ASIAN);
			} else {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY), ETHNICITY_CAUCASIAN);
			}
			
			// Gender
			rand = Math.random();
			if(rand < 0.5) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_GENDER), GENDER_FEMALE);
			} else {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_GENDER), GENDER_MALE);
			}
			
			// Age
			rand = Math.random();
			dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_AGE), rand*100);
			
			// Class
			rand = Math.random();
			if(rand < 0.5) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS), CLASS_RESISTANT);
			} else {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS), CLASS_NOT_RESISTANT);
			}
			
			data.add(dummy);
		}
		
		data.setClassIndex(ATTRIBUTE_INDEX_CLASS);
		
		return data;		
	}
	
	public ArrayList<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = createAttributes();
		}

		return attributes;
	}
	
	public ArrayList<Attribute> createAttributes() {
		attributes = new ArrayList<Attribute>(ATTRIBUTE_COUNT);

		// Class
		ArrayList<String> classes = new ArrayList<String>(2);
		
		classes.add(CLASS_NOT_RESISTANT);
		classes.add(CLASS_RESISTANT);
		
		Attribute clazz = new Attribute("Class", classes, ATTRIBUTE_INDEX_CLASS);
		attributes.add(ATTRIBUTE_INDEX_CLASS, clazz);		
		
		// Ethnicity African/Asian/Caucasion
		ArrayList<String> ethnicities = new ArrayList<String>(3);
		
		ethnicities.add(ETHNICITY_AFRICAN);
		ethnicities.add(ETHNICITY_ASIAN);
		ethnicities.add(ETHNICITY_CAUCASIAN);
		
		Attribute ethnicity = new Attribute("Ethnicity", ethnicities, ATTRIBUTE_INDEX_ETHNICITY);
		attributes.add(ATTRIBUTE_INDEX_ETHNICITY, ethnicity);
		
		// Gender
		ArrayList<String> genders = new ArrayList<String>(3);
		
		genders.add(GENDER_FEMALE);
		genders.add(GENDER_MALE);
		
		Attribute gender = new Attribute("Gender", genders, ATTRIBUTE_INDEX_GENDER);
		attributes.add(ATTRIBUTE_INDEX_GENDER, gender);
		
		// Age
		Attribute age = new Attribute("Age", ATTRIBUTE_INDEX_AGE);
		attributes.add(ATTRIBUTE_INDEX_AGE, age);
		
		
		/**
		 *  Attributes based on expert knowledge
		 */
		
		return attributes;
	}
	
	public List<Patient> getFailureClinicPatients() throws Exception {
		RegaService rs = context.getBean(RegaService.class);
		
		return rs.getPatients(rs.getDataset(RegaService.AC_FAILURE_CLINIC_DATASET_DESCRIPTOR));
	}

	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {						
		context = applicationContext;
    }
}
