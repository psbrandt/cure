package net.pascalbrandt.dsm.rule.impl;

import net.pascalbrandt.dsm.AttributeFactory;
import net.pascalbrandt.dsm.RegaService;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.TestResult;
import weka.core.Attribute;
import weka.core.Instance;

/*
 * Assessed using the WHO definitions35:
 *      1) PRT CD4 count is less then baseline CD4 count
 *      2) PRT CD4 count is 50% less then the peak CD4 count on treatment
 *      3) PRT CD4 count is <100 cells/uL, and the CD4 count prior to this was done >6
 *          months ago and is also <100 cells/uL
 * If any of these definitions are met, then that patient has met WHO criteria for immunological failure.
 */
public class PreResistanceImmunoFailure extends Rule {

    public static final Double SIX_MONTHS_IN_DAYS = 180.0;

    public PreResistanceImmunoFailure(RuleService rs) {
        super(rs);
    }

    @Override
    public void setAttributeValue(Attribute attribute, Instance instance, Patient patient) {
        RegaService rs = ruleService.getRegaService();

        TestResult preResistanceTestingCD4TR = rs.getPreResistanceCD4(patient);

        if (preResistanceTestingCD4TR == null)
            return; // Can't do this calculation without PRT CD4

        Double preResistanceTestingCD4 = Double.parseDouble(preResistanceTestingCD4TR.getValue());

        Double baselineCD4 = rs.getBaselineCD4(patient);

        // #1
        if (baselineCD4 != null) {
            if (preResistanceTestingCD4 < baselineCD4) {
                logger.info("Pre-Restance Immuno Failure " + patient.getPatientId() + " is ["
                        + AttributeFactory.ATTRIBUTE_VALUE_YES + "]");

                instance.setValue(attribute, AttributeFactory.ATTRIBUTE_VALUE_YES);
                return;
            }
        }

        // #2
        Double peakCD4 = rs.getPeakCD4(patient);
        if (peakCD4 != null) {
            if (preResistanceTestingCD4 < peakCD4 / 2.0) {
                logger.info("Pre-Restance Immuno Failure " + patient.getPatientId() + " is ["
                        + AttributeFactory.ATTRIBUTE_VALUE_YES + "]");

                instance.setValue(attribute, AttributeFactory.ATTRIBUTE_VALUE_YES);
                return;
            }
        }

        // #3
        TestResult prePrePRT = rs.getPreviousCD4(patient, preResistanceTestingCD4);
        if (prePrePRT != null) {
            long diffInDays = Math.abs((prePrePRT.getTestDate().getTime() - preResistanceTestingCD4TR
                    .getTestDate().getTime())) / (1000 * 60 * 60 * 24);

            Double preBaselineVal = Double.parseDouble(prePrePRT.getValue());

            if ((preResistanceTestingCD4 < 100.0) && (preBaselineVal < 100.00) && (diffInDays > SIX_MONTHS_IN_DAYS)) {
                logger.info("Pre-Restance Immuno Failure " + patient.getPatientId() + " is ["
                        + AttributeFactory.ATTRIBUTE_VALUE_YES + "]");

                instance.setValue(attribute, AttributeFactory.ATTRIBUTE_VALUE_YES);
                return;
            }
        }

        // Otherwise
        logger.info("Pre-Restance Immuno Failure " + patient.getPatientId() + " is ["
                + AttributeFactory.ATTRIBUTE_VALUE_NO + "]");

        instance.setValue(attribute, AttributeFactory.ATTRIBUTE_VALUE_NO);

    }

}
