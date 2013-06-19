package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.pascalbrandt.dsm.web.fbo.SVMConfigurationForm;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.PatientAttributeValue;
import net.sf.regadb.db.TestResult;
import net.sf.regadb.db.Therapy;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
	private static final Logger logger = LoggerFactory
			.getLogger(DataService.class);
	private ApplicationContext context;

	public static Integer PATIENT_COUNT;
	public static Integer RESISTANT_COUNT;
	public static Integer NOT_RESISTANT_COUNT;
	public static Integer UNLABELLED_COUNT;

	public static final String REGA_RESISTANCE_TEST = "REGA v8.0.1";

	public static final Integer REGA_RESISTANCE_TEST_ID1 = 51;
	public static final Integer REGA_RESISTANCE_TEST_ID2 = 54;

	public static final String REGA_STANFORD_RESISTANCE_TEST = "HIVDB 6.0.5";
	public static final Integer REGA_STANFORD_RESISTANCE_TEST_II = 57;

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
	 * Is the patient resistant to any of the drugs they were on at the time of
	 * their latest sequence analysis
	 * 
	 * @return
	 */
	public String classifyPatient(Patient p) {
		logger.info("Classifying Patient [" + p.getPatientId() + "]");

		RegaService rs = context.getBean(RegaService.class);

		List<TestResult> results = rs.getLatestGSSTestResults(p);

		Date latestGSSDate;

		if (results.size() == 0) {
			// If there the has no sequence analysis results, we don't know
			// their class
			logger.info("No GSS Test Results Available");
			return CLASS_UNLABELED;
		} else {
			latestGSSDate = results.get(0).getTestDate();
			logger.info("Latest Rega Test Date: " + latestGSSDate);
		}

		Therapy therapyAtGSSTestDate = rs.getTherapyAtDate(p, latestGSSDate);

		if (rs.isResistantToTherapy(therapyAtGSSTestDate, results)) {
			logger.info("Resistance detected");
			return ClassAttributeFactory.CLASS_ATTRIBUTE_RESISTANT;
		} else {
			logger.info("No resistance detected");
			return ClassAttributeFactory.CLASS_ATTRIBUTE_NOT_RESISTANT;
		}

		// return CLASS_UNLABELED;
		/*
		 * for (TestResult testResult : tests) { if
		 * (testResult.getTest().getDescription().equals(REGA_RESISTANCE_TEST))
		 * { if (latestSequenceAnalysisResults == null ||
		 * latestSequenceAnalysisResults
		 * .getTestDate().before(testResult.getTestDate()))
		 * latestSequenceAnalysisResults = testResult; } }
		 * 
		 * // This patient has no Rega 8.0.1 test result, so we can't determine
		 * if they're resistant or not if (latestSequenceAnalysisResults ==
		 * null) { logger.info("Patient has no " + REGA_RESISTANCE_TEST +
		 * " test results"); return null; }
		 * 
		 * // Find therapy at time of sequencing /*Therapy
		 * therapyAtTimeOfLatestSequenceAnalysis = null; for (Therapy therapy :
		 * therapies) { if ( // The start date of the therapy is on or before
		 * the test date
		 * ((therapy.getStartDate().before(latestSequenceAnalysisResults
		 * .getTestDate())) ||
		 * (therapy.getStartDate().equals(latestSequenceAnalysisResults
		 * .getTestDate()))) && // The stop date of the therapy is on or after
		 * the test date or the therapy is ongoing ((therapy.getStopDate() ==
		 * null) ||
		 * (therapy.getStopDate().after(latestSequenceAnalysisResults.getTestDate
		 * ())) ||
		 * (therapy.getStopDate().equals(latestSequenceAnalysisResults.getTestDate
		 * ()))) ) { therapyAtTimeOfLatestSequenceAnalysis = therapy; } }
		 */

	}

	public Instances buildPatientTrainingDataset(String dataset,
			SVMConfigurationForm config) {
		logger.info("Building Patient Training Dataset");
		//

		return null;
	}

	public Instances getPatientTrainingInstances(String datasetDescriptor,
			ArrayList<Attribute> attributes) {
		logger.info("Building Patient Training Instances");

		Instances data = new Instances("Patient Dataset", attributes,
				ClassAttributeFactory.CLASS_ATTRIBUTE_INDEX);

		RegaService rs = context.getBean(RegaService.class);
		List<Patient> patients = null;

		try {
			//patients = rs.getPatients(rs.getDataset(datasetDescriptor));
			
			// TODO: FIX_ME
			// Getting both failure and clinic datasets for dissertation analysis
			
			// 1. Get the failure management clinic patients
			patients = (ArrayList<Patient>) rs
					.getPatients(rs.getDataset(RegaService.AC_FAILURE_CLINIC_DATASET_DESCRIPTOR));
			
			// 2. Get the failure support camp patient
			patients.addAll((ArrayList<Patient>) rs
					.getPatients(rs.getDataset(RegaService.AC_FAILURE_SUPPORT_CAMP_DATASET_DESCRIPTOR)));				

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}

		int i = 0;
		for (Patient p : patients) {
			if (patientPassesAcceptanceStudyCriteria(p)) {
				logger.info("Adding patient[" + i++ + "]: " + p.getPatientId());
				data.add(constuctInstanceFromPatient(p, attributes));
			} else {
				logger.info("Patient " + p.getPatientId()
						+ " has no viral isolate.");
			}
		}

		data.setClassIndex(ClassAttributeFactory.CLASS_ATTRIBUTE_INDEX);

		return data;
	}

	/**
	 * Main entry point to the data preprocessing phase
	 * 
	 * @return The preprocessed data to be used to train the SVM
	 */
	public Instances getPatientTrainingData(String datasetDescription) {
		logger.info("Getting Patient Training Data for Dataset: "
				+ datasetDescription);
		Instances data = new Instances("Patient Dataset", getAttributes(),
				ATTRIBUTE_INDEX_CLASS);

		RegaService rs = context.getBean(RegaService.class);
		List<Patient> patients = null;

		try {
			patients = rs.getPatients(rs.getDataset(datasetDescription));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}

		int i = 0;
		for (Patient p : patients) {
			logger.info("Adding patient[" + i++ + "]: " + p.getPatientId());
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
		} catch (Exception e) {
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

	public Instance constuctInstanceFromPatient(Patient patient,
			ArrayList<Attribute> attributes) {
		// Use a sparce vector because we may not have all values for this
		// patient
		Instance instance = new SparseInstance(attributes.size());

		for (Attribute a : attributes) {
			AttributeFactory.addAttributeValue(a, instance, patient,
					context.getBean(RegaService.class));
		}

		return instance;
	}

	/**
	 * @param patient
	 * @return
	 */
	public Instance constuctInstanceFromPatient(Patient patient) {
		Instance fVector = new SparseInstance(ATTRIBUTE_COUNT);

		Set<PatientAttributeValue> patientAttributes = patient
				.getPatientAttributeValues();

		for (PatientAttributeValue pav : patientAttributes) {

			// Ethnicity
			if (pav.getAttribute().getName()
					.equals(RegaService.REGA_ATTRIBUTE_ETHNICITY)) {
				if (pav.getAttributeNominalValue().getValue().toUpperCase()
						.equals(ETHNICITY_AFRICAN.toUpperCase())) {
					fVector.setValue(
							getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY),
							ETHNICITY_AFRICAN);
				} else if (pav.getAttributeNominalValue().getValue()
						.toUpperCase().equals(ETHNICITY_ASIAN.toUpperCase())) {
					fVector.setValue(
							getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY),
							ETHNICITY_ASIAN);
				} else {
					fVector.setValue(
							getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY),
							ETHNICITY_ASIAN);
				}
			}

			// Gender
			if (pav.getAttribute().getName()
					.equals(RegaService.REGA_ATTRIBUTE_GENDER)) {
				if (pav.getAttributeNominalValue().getValue().toUpperCase()
						.equals(GENDER_MALE.toUpperCase())) {
					fVector.setValue(getAttributes()
							.get(ATTRIBUTE_INDEX_GENDER), GENDER_MALE);
				} else {
					fVector.setValue(getAttributes()
							.get(ATTRIBUTE_INDEX_GENDER), GENDER_FEMALE);
				}
			}

			// Age
			if (pav.getAttribute().getName()
					.equals(RegaService.REGA_ATTRIBUTE_AGE)) {
				if (pav.getValue() == "")
					logger.info(">> No Age Attribute Found");

				fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_AGE),
						Integer.parseInt(pav.getValue()));
			}

		}

		/**
		 * Expert knowledge derived features
		 */
		// Weight Decrease

		// Time on fai ling regimen

		// viral load never supressed

		// CD4 never recovered

		// TODO: Chat to Tulio to get a few more features

		// Class
		/*
		 * RegaService rs = context.getBean(RegaService.class);
		 * if(rs.isPatientResistantToCurrentTherapy(patient)) {
		 * fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS),
		 * CLASS_RESISTANT); } else {
		 * fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS),
		 * CLASS_NOT_RESISTANT); }
		 */

		String patientClass = classifyPatient(patient);

		if (patientClass.equals(CLASS_RESISTANT)) {
			fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS),
					CLASS_RESISTANT);
		} else if (patientClass.equals(CLASS_NOT_RESISTANT)) {
			fVector.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS),
					CLASS_NOT_RESISTANT);
		}

		return fVector;
	}

	public String trimOpCharacters(String val) {
		if (val.startsWith("<") || val.startsWith("=")) {
			return val.substring(1);
		}
		return val;
	}

	public boolean patientPassesAcceptanceStudyCriteria(Patient p) {
		RegaService rs = context.getBean(RegaService.class);

		// 1. Patient must have at least one viral isolate
		if (!rs.hasViralIsolate(p)) {
			logger.info("Patient [" + p.getPatientId()
					+ "] has no viral isolates.");
			return false;
		}

		return true;
	}

	public XSSFWorkbook excelExport(String dataset, SVMConfigurationForm config) {

		ArrayList<Patient> patients = null;
		RegaService regaService = context.getBean(RegaService.class);

		// Step 1: Get the patients from the DB
		try {

			if (dataset == null) {
				patients = (ArrayList<Patient>) regaService.getPatients();
			} else {
				// TODO: FIX_ME
				// Getting both failure and clinic datasets for dissertation analysis
				
				// 1. Get the failure management clinic patients
				patients = (ArrayList<Patient>) regaService
						.getPatients(regaService.getDataset(RegaService.AC_FAILURE_CLINIC_DATASET_DESCRIPTOR));
				
				// 2. Get the failure support camp patient
				patients.addAll((ArrayList<Patient>) regaService
						.getPatients(regaService.getDataset(RegaService.AC_FAILURE_SUPPORT_CAMP_DATASET_DESCRIPTOR)));				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Step 2: Create column headers
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet firstSheet = workbook.createSheet(dataset);
		firstSheet.createFreezePane( 0, 1, 0, 1 );

		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Times New Roman");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(font);

		XSSFRow headerRow = firstSheet.createRow(0);
		XSSFCell cellA = headerRow.createCell(0);

		cellA.setCellStyle(headerStyle);
		cellA.setCellValue(new XSSFRichTextString("Patient ID"));

		int col = 1;
		// Demographic
		String[] demographic = config.getSelectedDemographicAttributes();
		for (int i = 0; i < demographic.length; i++) {
			XSSFCell temp = headerRow.createCell(col++);
			temp.setCellStyle(headerStyle);
			temp.setCellValue(demographic[i]);
		}

		// Clinical
		String[] clinical = config.getSelectedClinicalAttributes();
		for (int i = 0; i < clinical.length; i++) {
			XSSFCell temp = headerRow.createCell(col++);
			temp.setCellStyle(headerStyle);
			temp.setCellValue(clinical[i]);
		}

		// Adherence
		String[] adherence = config.getSelectedAdherenceAttributes();
		for (int i = 0; i < adherence.length; i++) {
			XSSFCell temp = headerRow.createCell(col++);
			temp.setCellStyle(headerStyle);
			temp.setCellValue(adherence[i]);
		}

		// Other
		String[] other = config.getSelectedOtherAttributes();
		for (int i = 0; i < other.length; i++) {
			XSSFCell temp = headerRow.createCell(col++);
			temp.setCellStyle(headerStyle);
			temp.setCellValue(other[i]);
		}

		// Class
		// XSSFCell temp = headerRow.createCell(col++);
		// temp.setCellStyle(headerStyle);
		// temp.setCellValue("Resistance");

		// Labels
		HashSet<String> labels = ClassAttributeFactory.MULTI_LABEL_DRUG_RESISTANCE_LABELS;
		for (String s : labels) {
			XSSFCell temp = headerRow.createCell(col++);
			temp.setCellStyle(headerStyle);
			temp.setCellValue(s);
		}

		// Step 3: Produce Data
		RegaService rs = context.getBean(RegaService.class);

		RuleService ruleService = context.getBean(RuleService.class);

		int row = 1;
		for (Patient p : patients) {
			if (patientPassesAcceptanceStudyCriteria(p)) {

				XSSFRow tempRow = firstSheet.createRow(row);
				Instance instance = new SparseInstance(demographic.length
						+ clinical.length + adherence.length + other.length);

				tempRow.createCell(0).setCellValue(p.getPatientId());

				col = 1;
				// Demographic
				List<Attribute> demographicAttributes = DemographicAttributeFactory
						.createAttributes(demographic);
				ArrayList<Attribute> demographicAttributesAL = new ArrayList<Attribute>(
						demographicAttributes);
				Instances data = new Instances("dummy",
						demographicAttributesAL, 300);
				for (Attribute attr : demographicAttributes) {
					XSSFCell tempCell = tempRow.createCell(col++);

					data.add(instance);

					DemographicAttributeFactory.addAttributeValue(attr,
							instance, p, rs);
					if (attr.isNumeric()) {

						Double d = instance.value(attr);

						if (d != Double.NaN)
							tempCell.setCellValue(d);

					} else {
						String val = instance.stringValue(data.attribute(attr
								.name()));

						if (!val.equals("dummy"))
							tempCell.setCellValue(val);

					}
					
					if((tempCell != null) && (tempCell.getRawValue() != null)) {					
						if (tempCell.getRawValue().equals("#NUM!"))
							tempCell.setCellValue("");
					}
				}

				// Clinical

				List<Attribute> clinicalAttributes = ClinicalAttributeFactory
						.createAttributes(clinical, ruleService);
				ArrayList<Attribute> clinicalAttributesAL = new ArrayList<Attribute>(
						clinicalAttributes);
				Instances clinicalData = new Instances("dummy",
						clinicalAttributesAL, 300);
				instance = new SparseInstance(demographic.length
						+ clinical.length + adherence.length + other.length);
				for (Attribute attr : clinicalAttributes) {
					XSSFCell tempCell = tempRow.createCell(col++);

					clinicalData.add(instance);

					ClinicalAttributeFactory.addAttributeValue(attr, instance,
							p, rs);
					if (attr.isNumeric()) {
						Double d = instance.value(attr);

						boolean isMissing = instance.isMissing(attr);

						logger.info("Value for [" + attr.name() + "] is [" + d
								+ "]");

						tempCell.setCellValue(d);

						if (tempCell.getRawValue().equals("#NUM!"))
							tempCell.setCellValue("");

					} else {
						String val = instance.stringValue(clinicalData
								.attribute(attr.name()));

						if (!val.equals("dummy"))
							tempCell.setCellValue(val);

					}
				}

				// Adherence
				Instance dummyinstance = new SparseInstance(demographic.length
						+ clinical.length + adherence.length + other.length);

				List<Attribute> adherenceAttributes = AdherenceAttributeFactory
						.createAttributes(adherence, ruleService);
				ArrayList<Attribute> adherenceAttributesAL = new ArrayList<Attribute>(
						adherenceAttributes);
				Instances adherenceData = new Instances("dummy",
						adherenceAttributesAL, 300);
				instance = new SparseInstance(demographic.length
						+ clinical.length + adherence.length + other.length);
				for (Attribute attr : adherenceAttributes) {
					XSSFCell tempCell = tempRow.createCell(col++);

					adherenceData.add(dummyinstance);

					AdherenceAttributeFactory.addAttributeValue(attr,
							dummyinstance, p, rs);
					if (attr.isNumeric()) {
						Double d = dummyinstance.value(attr);

						tempCell.setCellValue(d);

						if (tempCell.getRawValue().equals("#NUM!"))
							tempCell.setCellValue("");

					} else {
						String val = dummyinstance.stringValue(adherenceData
								.attribute(attr.name()));

						if (!val.equals("dummy"))
							tempCell.setCellValue(val);

					}
				}

				// Other
				Instance otherdummyinstance = new SparseInstance(
						demographic.length + clinical.length + adherence.length
								+ other.length);

				List<Attribute> otherAttributes = OtherAttributeFactory
						.createAttributes(other, ruleService);
				ArrayList<Attribute> otherAttributesAL = new ArrayList<Attribute>(
						otherAttributes);
				Instances otherData = new Instances("dummy", otherAttributesAL,
						300);
				instance = new SparseInstance(demographic.length
						+ clinical.length + adherence.length + other.length);
				for (Attribute attr : otherAttributes) {
					XSSFCell tempCell = tempRow.createCell(col++);

					otherData.add(otherdummyinstance);

					OtherAttributeFactory.addAttributeValue(attr,
							otherdummyinstance, p, rs);
					if (attr.isNumeric()) {
						Double d = otherdummyinstance.value(attr);

						tempCell.setCellValue(d);

						if (tempCell.getRawValue().equals("#NUM!"))
							tempCell.setCellValue("");

					} else {
						String val = otherdummyinstance.stringValue(otherData
								.attribute(attr.name()));

						if (!val.equals("dummy"))
							tempCell.setCellValue(val);

					}
				}

				// Class
				// XSSFCell tempCell = tempRow.createCell(col++);
				// tempCell.setCellValue(rs.classifyPatient(p));

				// Labels
				Instance labeldummyinstance = new SparseInstance(
						demographic.length
								+ clinical.length
								+ adherence.length
								+ other.length
								+ ClassAttributeFactory.MULTI_LABEL_DRUG_RESISTANCE_LABELS
										.size());

				List<Attribute> labelAttributes = ClassAttributeFactory
						.getLabelAttributes();// OtherAttributeFactory.createAttributes(other,
												// ruleService);
				ArrayList<Attribute> labelAttributesAL = new ArrayList<Attribute>(
						labelAttributes);
				Instances labelData = new Instances("dummy", labelAttributesAL,
						300);
				instance = new SparseInstance(demographic.length
						+ clinical.length + adherence.length + other.length);
				for (Attribute attr : labelAttributes) {
					XSSFCell tempCell = tempRow.createCell(col++);

					labelData.add(labeldummyinstance);

					ClassAttributeFactory.addAttributeValue(attr,
							labeldummyinstance, p, rs);
					if (attr.isNumeric()) {
						Double d = labeldummyinstance.value(attr);

						tempCell.setCellValue(d);

						if (tempCell.getRawValue().equals("#NUM!"))
							tempCell.setCellValue("");

					} else {
						String val = labeldummyinstance.stringValue(labelData
								.attribute(attr.name()));

						if (!val.equals("dummy"))
							tempCell.setCellValue(val);

					}
				}

				row++;
			}
		}

		return workbook;

	}

	public Instances getTrainingData() {
		Instances data = new Instances("Training Dataset", getAttributes(),
				ATTRIBUTE_INDEX_CLASS);

		// FIXME: Add dummy item
		Instance dummy;

		for (int i = 0; i < 1000; i++) {
			dummy = new DenseInstance(getAttributes().size());

			// Ethnicity
			double rand = Math.random();
			if (rand < 0.33) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY),
						ETHNICITY_AFRICAN);
			} else if (rand < 0.66) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY),
						ETHNICITY_ASIAN);
			} else {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY),
						ETHNICITY_CAUCASIAN);
			}

			// Gender
			rand = Math.random();
			if (rand < 0.5) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_GENDER),
						GENDER_FEMALE);
			} else {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_GENDER),
						GENDER_MALE);
			}

			// Age
			rand = Math.random();
			dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_AGE), rand * 100);

			// Class
			rand = Math.random();
			if (rand < 0.5) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS),
						CLASS_RESISTANT);
			} else {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS),
						CLASS_NOT_RESISTANT);
			}

			data.add(dummy);
		}

		data.setClassIndex(ATTRIBUTE_INDEX_CLASS);

		return data;
	}

	public Instances getTestingData() {
		Instances data = new Instances("Testing Dataset", getAttributes(),
				ATTRIBUTE_INDEX_CLASS);

		// FIXME: Add dummy item
		Instance dummy;

		for (int i = 0; i < 100; i++) {
			dummy = new DenseInstance(getAttributes().size());

			// Ethnicity
			double rand = Math.random();
			if (rand < 0.33) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY),
						ETHNICITY_AFRICAN);
			} else if (rand < 0.66) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY),
						ETHNICITY_ASIAN);
			} else {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_ETHNICITY),
						ETHNICITY_CAUCASIAN);
			}

			// Gender
			rand = Math.random();
			if (rand < 0.5) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_GENDER),
						GENDER_FEMALE);
			} else {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_GENDER),
						GENDER_MALE);
			}

			// Age
			rand = Math.random();
			dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_AGE), rand * 100);

			// Class
			rand = Math.random();
			if (rand < 0.5) {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS),
						CLASS_RESISTANT);
			} else {
				dummy.setValue(getAttributes().get(ATTRIBUTE_INDEX_CLASS),
						CLASS_NOT_RESISTANT);
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

		Attribute ethnicity = new Attribute("Ethnicity", ethnicities,
				ATTRIBUTE_INDEX_ETHNICITY);
		attributes.add(ATTRIBUTE_INDEX_ETHNICITY, ethnicity);

		// Gender
		ArrayList<String> genders = new ArrayList<String>(3);

		genders.add(GENDER_FEMALE);
		genders.add(GENDER_MALE);

		Attribute gender = new Attribute("Gender", genders,
				ATTRIBUTE_INDEX_GENDER);
		attributes.add(ATTRIBUTE_INDEX_GENDER, gender);

		// Age
		Attribute age = new Attribute("Age", ATTRIBUTE_INDEX_AGE);
		attributes.add(ATTRIBUTE_INDEX_AGE, age);

		/**
		 * Attributes based on expert knowledge
		 */

		return attributes;
	}

	public List<Patient> getFailureClinicPatients() throws Exception {
		RegaService rs = context.getBean(RegaService.class);

		return rs.getPatients(rs
				.getDataset(RegaService.AC_FAILURE_CLINIC_DATASET_DESCRIPTOR));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}

	public Double getLeastSquaresFitGradient(List<TestResult> results) {

		// Make sure we have at least two data point to calculate the change
		if (results == null || results.size() < 2)
			return null;

		// Initialize the regression Model
		SimpleRegression linearLeastSquaresRegression = new SimpleRegression();

		// Use days as the domain, with the first data point at zero
		Double currentX = 0.0;
		
		Date currentDate = results.get(0).getTestDate();

		for (int i = 0; i < results.size(); i++) {
			TestResult tr = results.get(i);
			
			if(i > 0 ) {
				currentX += getDiffInDays(currentDate, tr.getTestDate());
			}
				
			currentDate = tr.getTestDate();
			
			Double currentY = Double.parseDouble(trimOpCharacters(tr.getValue()));
			
			logger.info("Adding data point (" + currentX + "," + currentY + ")");
			
			linearLeastSquaresRegression.addData(currentX, currentY);
		}

		return linearLeastSquaresRegression.getSlope();
	}

	public Double getDiffInDays(Date startDate, Date endDate) {
		return (double) (Math.abs((endDate.getTime() - startDate.getTime()))
				/ (1000 * 60 * 60 * 24));
	}
}
