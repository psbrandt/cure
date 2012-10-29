package net.pascalbrandt.dsm.rule.impl;

import java.util.Date;
import java.util.List;

import net.pascalbrandt.dsm.RegaService;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.TestResult;
import weka.core.Attribute;
import weka.core.Instance;

/*
 * 1) Time on Failing Regimen (TFR) begins from the date of the earliest VL above 1000 copies/ml 
 *      that is not followed by a VL test less then 50 copies/ml and ends at the resistance test date.
 * 2) If all of the VL tests are greater then 1000 copies/ml, TFR is set to ART start date.
 */
public class TimeOnFailingRegimenRuleImpl extends Rule {

    public static final Double TREATMENT_FAILING_VIRAL_LOAD = 10000.0;
    public static final Double TREATMENT_WORKING_VIRAL_LOAD = 50.0;

    public TimeOnFailingRegimenRuleImpl(RuleService rs) {
        super(rs);
    }

    @Override
    public void setAttributeValue(Attribute attribute, Instance instance, Patient patient) {
        RegaService rs = ruleService.getRegaService();

        // Get a list of viral loads in reverse order
        List<TestResult> VLResults = rs.getListOfTestResultsSortedByDate(RegaService.VIRAL_LOAD_TEST_ID,
                patient.getPatientIi(), RegaService.SORT_ORDER_DESC);

        Date sequenceDate = rs.getFirstSequenceDate(patient);

        Date initDate = rs.getARTInitiationDate(patient.getPatientIi());

        if (initDate == null)
            return; // This patient has never been on any treatment

        boolean foundLowVL = false;

        Date previousDate = null;
        Date mostRecentVL = null;

        for (TestResult vl : VLResults) {

            if (mostRecentVL == null) {
                mostRecentVL = vl.getTestDate();
            }

            Double vlValue = Double.parseDouble(stripPrefix(vl.getValue()));

            if (vlValue < TREATMENT_WORKING_VIRAL_LOAD) {
                if (previousDate == null) {
                    // this is the most recent VL
                    logger.info("TFR for " + patient.getPatientId() + " is 0");

                    instance.setValue(attribute, 0);
                    return;
                } else {
                    foundLowVL = true;
                    Double diff;
                    if (sequenceDate == null) {
                        diff = new Double(getDiffInDays(previousDate, mostRecentVL));
                    } else {
                        diff = new Double(getDiffInDays(previousDate, sequenceDate));
                    }
                    logger.info("TFR for " + patient.getPatientId() + " is " + diff);

                    instance.setValue(attribute, diff);
                    return;
                }
            }

            previousDate = vl.getTestDate();
        }

        if (!foundLowVL) {
            // Viral load never supressed
            Double diff;

            if (sequenceDate == null) {
                diff = new Double(getDiffInDays(initDate, mostRecentVL));
            } else {
                diff = new Double(getDiffInDays(initDate, sequenceDate));
            }

            logger.info("TFR for " + patient.getPatientId() + " is " + diff);
            instance.setValue(attribute, diff);
        }

    }

    private String stripPrefix(String viralLoad) {
        if (viralLoad.contains("=") || viralLoad.contains("<")) {
            return viralLoad.substring(1);
        } else {
            return viralLoad;
        }
    }

    private long getDiffInDays(Date toDate, Date fromDate) {
        return Math.abs((toDate.getTime() - fromDate.getTime())) / (1000 * 60 * 60 * 24);
    }

}
