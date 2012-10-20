package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import net.pascalbrandt.dsm.db.RegaDAO;
import net.pascalbrandt.dsm.web.controller.HomeController;
import net.sf.regadb.db.Dataset;
import net.sf.regadb.db.DrugGeneric;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.PatientAttributeValue;
import net.sf.regadb.db.TestResult;
import net.sf.regadb.db.Therapy;
import net.sf.regadb.db.TherapyGeneric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service("RegaService")
public class RegaService implements ApplicationContextAware {
	public static final String REGA_ATTRIBUTE_GENDER = "Gender";
	public static final String REGA_ATTRIBUTE_ETHNICITY = "Ethnicity";
	public static final String REGA_ATTRIBUTE_AGE = "Age";

	public static final String GSS_RESULT_SUSCEPTIBLE = "1.0";
	public static final String GSS_RESULT_RESISTANT = "0.0";
	
	public static final String GSS_TEST_1 = "ANRS v2008.17"; // 53
	public static final Integer GSS_TEST_1_ID = 53;
	
	public static final String GSS_TEST_2 = "HIVDB v5.1.3"; // 52
	public static final Integer GSS_TEST_2_ID = 52;
	
	public static final String GSS_TEST_3 = "REGA v8.0.1"; // 51
	public static final Integer GSS_TEST_3_ID = 51;
	
	public static final String AC_FAILURE_CLINIC_DATASET_DESCRIPTOR = "AC_Failure Management Clinic";
	
	ApplicationContext context;
	
	@Autowired
	@Qualifier("RegaDAO")
	private RegaDAO dao;
	
	private static final Logger logger = LoggerFactory.getLogger(RegaService.class);
	
	public RegaService() {
    }
	
	public void setRegaDAO(RegaDAO dao) {
		this.dao = dao;
	}
	
	public RegaDAO getRegaDAO() {
		return this.dao;
	}
	
	public Date getLatestTestResultDate(List<Integer> testTypes, Integer patientId) {
		return dao.getLatestTestResultDate(testTypes, patientId);
	}
	
	public Integer getPatientCount() throws Exception {
		return dao.getPatientCount();
	}	
	
	public List<Patient> getPatients() throws Exception {
		return dao.getPatients();
	}
	
	public List<Patient> getPatients(Dataset dataset) throws Exception {
		return dao.getPatients(dataset);
	}
	
	public Dataset getDataset(String description) throws Exception {
		return dao.getDataset(description);
	}
	
	public boolean isResistantToTherapy(Therapy therapy, List<TestResult> testResults) {
		
		List<DrugGeneric> drugs = new ArrayList<DrugGeneric>();
		
		// Constuct the list of drugs used in the therapy
		for(TherapyGeneric therapyGeneric : therapy.getTherapyGenerics()) {
			drugs.add(therapyGeneric.getId().getDrugGeneric());
		}
		
		for (TestResult result : testResults) {
			if (drugs.contains(result.getDrugGeneric())) {
				// This result is relevant because the drug is part of the therapy
				
				// Return resistant if any of the three main tests say resistant
				if ((result.getTest().getTestIi() == GSS_TEST_1_ID) ||
				    (result.getTest().getTestIi() == GSS_TEST_2_ID) ||
					(result.getTest().getTestIi() == GSS_TEST_3_ID)) {
					if (result.getValue().equals(GSS_RESULT_RESISTANT))
						logger.info("Found resistance to: " + result.getDrugGeneric().getGenericName());
						return true;
				}
			}
		}
		
		return false;
	}
	
	
	/**
	 * @param testResult
	 * @return
	 */
	public boolean isGSSTestResult(TestResult testResult) {
		if (testResult.getTest().getTestType().getTestTypeIi() == DataService.REGA_GSS_TEST_TYPE_ID1 ||
			testResult.getTest().getTestType().getTestTypeIi() == DataService.REGA_GSS_TEST_TYPE_ID2)
			return true;
		return false;
	}
	
	/**
	 * @param patient
	 * @param date
	 * @return
	 */
	public Therapy getTherapyAtDate(Patient patient, Date date) {
		Therapy therapyAtDate = null;
		
		for (Therapy therapy : patient.getTherapies()) {
			if (
				// The start date of the therapy is on or before the test date
				((therapy.getStartDate().before(date))  ||
			     (therapy.getStartDate().equals(date))) &&
			    // The stop date of the therapy is on or after the test date or the therapy is ongoing
			    ((therapy.getStopDate() == null) ||
			     (therapy.getStopDate().after(date)) ||
			     (therapy.getStopDate().equals(date)))
			   ) {
				therapyAtDate = therapy;
			}
		}
		
		if (therapyAtDate == null) {
			// If they weren't taking their drugs at the time of the sequence analysis
			// select the therapy they were on just before
			
			// Choose the last therapy that started before the date
			for (Therapy therapy : patient.getTherapies()) {
				if (
					// The start date of the therapy is on or before the test date
					((therapy.getStartDate().before(date))  ||
				     (therapy.getStartDate().equals(date)))) {
					if(therapyAtDate == null || therapyAtDate.getStartDate().before(therapy.getStartDate()))
						therapyAtDate = therapy;
				}
			}
		}
		
		return therapyAtDate;
	}
	
	/**
	 * @param patient
	 * @return
	 */
	public List<TestResult> getLatestGSSTestResults(Patient patient) {
		List<TestResult> latestGSSResults = new ArrayList<TestResult>();
		
		// Construct a list of GSS test result dates
		Date currentLatestDate = null;
		for (TestResult tr : patient.getTestResults()) {
			if(isGSSTestResult(tr)) {
				if (currentLatestDate == null) {
					// Choose first result date if it's not yet set
					currentLatestDate = tr.getTestDate();
					latestGSSResults.add(tr);
				} else if (tr.getTestDate().after(currentLatestDate)) {
					// If we find a later result, clear the list and use the date
					latestGSSResults.clear();
					latestGSSResults.add(tr);
					currentLatestDate = tr.getTestDate();
				} else if (tr.getTestDate().equals(currentLatestDate)) {
					// Keep all results at this date
					latestGSSResults.add(tr);
				}
			}
		}
		
		return latestGSSResults;
	}
	
	public PatientAttributeValue getPatientAttributeValue(Patient patient, String attribute) {
		Set<PatientAttributeValue> patientAttributes = patient.getPatientAttributeValues();
		
		for(PatientAttributeValue pav : patientAttributes) {
			if(pav.getAttribute().getName().equals(attribute)) {
				return pav;
			}
		}
		
		return null;
	}
	
	public String classifyPatient(Patient patient) {
		return context.getBean(DataService.class).classifyPatient(patient);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}
}
