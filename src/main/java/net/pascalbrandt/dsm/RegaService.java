package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import net.pascalbrandt.dsm.db.RegaDAO;
import net.sf.regadb.db.Dataset;
import net.sf.regadb.db.DrugGeneric;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.PatientAttributeValue;
import net.sf.regadb.db.TestResult;
import net.sf.regadb.db.Therapy;
import net.sf.regadb.db.TherapyGeneric;
import net.sf.regadb.db.ViralIsolate;

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

    public static final Integer CD4_COUNT_TEST_ID = 23; // CD4 Count (cells/ul)
    public static final Integer VIRAL_LOAD_TEST_ID = 21; // Viral Load
                                                         // (copies/ml)

    public static final String SORT_ORDER_ASC = "asc";
    public static final String SORT_ORDER_DESC = "desc";

    public static final String AC_FAILURE_CLINIC_DATASET_DESCRIPTOR = "AC_Failure Management Clinic";

    public static final double SEVEN_MONTHS_IN_DAYS = 210;
    public static final double TWO_WEEKS_IN_DAYS = 14;
    public static final double THIRTEEN_MONTHS_IN_DAYS = 390;
    public static final double FOUR_WEEKS_IN_DAYS = 28;

    ApplicationContext context;

    @Autowired
    @Qualifier("RegaDAO")
    private RegaDAO dao;

    private static final Logger logger = LoggerFactory.getLogger(RegaService.class);

    public RegaService() {
    }

    public RuleService getRuleService() {
        return context.getBean(RuleService.class);
    }

    public void setRegaDAO(RegaDAO dao) {
        this.dao = dao;
    }

    public RegaDAO getRegaDAO() {
        return this.dao;
    }

    public Date getARTInitiationDate(Integer patientId) {
        return dao.getARTInitiationDate(patientId);
    }

    public List<TestResult> getListOfTestResultsSortedByDate(Integer testType, Integer patientId, String sortOrder) {
        return dao.getListOfTestResultsSortedByDate(testType, patientId, sortOrder);
    }

    public Date getLatestTestResultDate(List<Integer> testTypes, Integer patientId) {
        return dao.getLatestTestResultDate(testTypes, patientId);
    }

    // public

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

    public Double getPeakCD4(Patient patient) {
        List<TestResult> CD4Results = getListOfTestResultsSortedByDate(RegaService.CD4_COUNT_TEST_ID,
                patient.getPatientIi(), RegaService.SORT_ORDER_DESC);

        Double tempPeak = null;

        for (TestResult tr : CD4Results) {

            Double tempResult = Double.parseDouble(tr.getValue());

            if ((tempPeak == null) || (tempResult > tempPeak)) {
                tempPeak = tempResult;
            }

        }

        return tempPeak;
    }

    // Get the CD4 value before the given value
    public TestResult getPreviousCD4(Patient patient, Double value) {
        List<TestResult> CD4Results = getListOfTestResultsSortedByDate(RegaService.CD4_COUNT_TEST_ID,
                patient.getPatientIi(), RegaService.SORT_ORDER_ASC);

        TestResult prev = null;
        TestResult current = null;
        for (TestResult tr : CD4Results) {
            prev = current;
            current = tr;
        }

        return prev;
    }

    public TestResult getPreResistanceCD4(Patient patient) {
        List<TestResult> CD4Results = getListOfTestResultsSortedByDate(RegaService.CD4_COUNT_TEST_ID,
                patient.getPatientIi(), RegaService.SORT_ORDER_ASC);

        Date sequenceDate = getFirstSequenceDate(patient);

        if (sequenceDate == null || CD4Results.size() == 0)
            return null; // We have no sequence date or no CD4 counts

        TestResult currentPreResistanceCD4 = null;
        Date currentPreResistanceCD4Date = null;

        for (TestResult tr : CD4Results) {

            Date tempDate = tr.getTestDate();

            // Calculate date difference in days
            long diffInDays = Math.abs((sequenceDate.getTime() - tempDate.getTime())) / (1000 * 60 * 60 * 24);

            if (currentPreResistanceCD4 == null) {
                // We don't have a value yet

                if ((tempDate.before(sequenceDate) && diffInDays <= THIRTEEN_MONTHS_IN_DAYS)
                        || (tempDate.after(sequenceDate) && diffInDays <= FOUR_WEEKS_IN_DAYS)) {
                    // The value is valid
                    currentPreResistanceCD4 = tr;
                    currentPreResistanceCD4Date = tempDate;
                }
            } else {
                // We have a value

                if (tempDate.before(sequenceDate)) {
                    // The temp value is before the init date

                    // If the temp test result closer to the init date
                    if (tempDate.after(currentPreResistanceCD4Date)) {
                        // The result is closer to the init date
                        currentPreResistanceCD4 = tr;
                        currentPreResistanceCD4Date = tempDate;
                    }

                } else {
                    // The temp value is after the init date

                    // Only consider replacing the value if the current value is
                    // also after the init date
                    if (currentPreResistanceCD4Date.after(sequenceDate)) {
                        // Is the temp value closer to the init date?

                        if (tempDate.before(currentPreResistanceCD4Date)) {
                            // The result is closer to the init date
                            currentPreResistanceCD4 = tr;
                            currentPreResistanceCD4Date = tempDate;
                        }
                    }
                }
            }
        }

        return currentPreResistanceCD4;
    }

    public Double getBaselineCD4(Patient patient) {

        List<TestResult> CD4Results = getListOfTestResultsSortedByDate(RegaService.CD4_COUNT_TEST_ID,
                patient.getPatientIi(), RegaService.SORT_ORDER_DESC);

        Date initDate = getARTInitiationDate(patient.getPatientIi());

        if (initDate == null || CD4Results.size() == 0)
            return null; // We have no start date or no CD4 counts

        Double currentBaselineCD4 = null;
        Date currentBaselineDate = null;

        for (TestResult tr : CD4Results) {

            Double tempResult = Double.parseDouble(tr.getValue());
            Date tempDate = tr.getTestDate();

            // Calculate date difference in days
            long diffInDays = Math.abs((initDate.getTime() - tempDate.getTime())) / (1000 * 60 * 60 * 24);

            if (currentBaselineCD4 == null) {
                // We don't have a value yet

                if ((tempDate.before(initDate) && diffInDays <= SEVEN_MONTHS_IN_DAYS)
                        || (tempDate.after(initDate) && diffInDays <= TWO_WEEKS_IN_DAYS)) {
                    // The value is valid
                    currentBaselineCD4 = tempResult;
                    currentBaselineDate = tempDate;
                }
            } else {
                // We have a value

                if (tempDate.before(initDate)) {
                    // The temp value is before the init date

                    // If the temp test result closer to the init date
                    if (tempDate.after(currentBaselineDate)) {
                        // The result is closer to the init date
                        currentBaselineCD4 = tempResult;
                        currentBaselineDate = tempDate;
                    }

                } else {
                    // The temp value is after the init date

                    // Only consider replacing the value if the current value is
                    // also after the init date
                    if (currentBaselineDate.after(initDate)) {
                        // Is the temp value closer to the init date?

                        if (tempDate.before(currentBaselineDate)) {
                            // The result is closer to the init date
                            currentBaselineCD4 = tempResult;
                            currentBaselineDate = tempDate;
                        }
                    }
                }
            }
        }

        return currentBaselineCD4;

    }

    public boolean isResistantToTherapy(Therapy therapy, List<TestResult> testResults) {

        List<DrugGeneric> drugs = new ArrayList<DrugGeneric>();

        // Constuct the list of drugs used in the therapy
        for (TherapyGeneric therapyGeneric : therapy.getTherapyGenerics()) {
            drugs.add(therapyGeneric.getId().getDrugGeneric());
        }

        for (TestResult result : testResults) {
            if (drugs.contains(result.getDrugGeneric())) {
                // This result is relevant because the drug is part of the
                // therapy

                // Return resistant if any of the three main tests say resistant
                if ((result.getTest().getTestIi() == GSS_TEST_1_ID) || (result.getTest().getTestIi() == GSS_TEST_2_ID)
                        || (result.getTest().getTestIi() == GSS_TEST_3_ID)) {
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
        if (testResult.getTest().getTestType().getTestTypeIi() == DataService.REGA_GSS_TEST_TYPE_ID1
                || testResult.getTest().getTestType().getTestTypeIi() == DataService.REGA_GSS_TEST_TYPE_ID2)
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
            ((therapy.getStartDate().before(date)) || (therapy.getStartDate().equals(date))) &&
            // The stop date of the therapy is on or after the test date or the
            // therapy is ongoing
                    ((therapy.getStopDate() == null) || (therapy.getStopDate().after(date)) || (therapy.getStopDate()
                            .equals(date)))) {
                therapyAtDate = therapy;
            }
        }

        if (therapyAtDate == null) {
            // If they weren't taking their drugs at the time of the sequence
            // analysis
            // select the therapy they were on just before

            // Choose the last therapy that started before the date
            for (Therapy therapy : patient.getTherapies()) {
                if (
                // The start date of the therapy is on or before the test date
                ((therapy.getStartDate().before(date)) || (therapy.getStartDate().equals(date)))) {
                    if (therapyAtDate == null || therapyAtDate.getStartDate().before(therapy.getStartDate()))
                        therapyAtDate = therapy;
                }
            }
        }

        return therapyAtDate;
    }

    public Date getFirstSequenceDate(Patient patient) {
        Date firstSequenceDate = null;

        Set<ViralIsolate> sequences = patient.getViralIsolates();

        for (ViralIsolate sequence : sequences) {
            if (firstSequenceDate == null) {
                firstSequenceDate = sequence.getSampleDate();
            } else {
                if (sequence.getSampleDate().before(firstSequenceDate))
                    firstSequenceDate = sequence.getSampleDate();
            }
        }

        return firstSequenceDate;
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
            if (isGSSTestResult(tr)) {
                if (currentLatestDate == null) {
                    // Choose first result date if it's not yet set
                    currentLatestDate = tr.getTestDate();
                    latestGSSResults.add(tr);
                } else if (tr.getTestDate().after(currentLatestDate)) {
                    // If we find a later result, clear the list and use the
                    // date
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

        for (PatientAttributeValue pav : patientAttributes) {
            if (pav.getAttribute().getName().equals(attribute)) {
                return pav;
            }
        }

        return null;
    }

    /**
     * 1) Defined as the closest CD4 count prior to ART initiation, up to a
     * maximum of 7 months pre-ART initiation. 2) CD4 count within 2 weeks post
     * ART initiation if the first definition is not met.
     */
    public Double getPatientBaselineCD4(Patient patient) {

        return null;
    }

    public String classifyPatient(Patient patient) {
        return context.getBean(DataService.class).classifyPatient(patient);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
