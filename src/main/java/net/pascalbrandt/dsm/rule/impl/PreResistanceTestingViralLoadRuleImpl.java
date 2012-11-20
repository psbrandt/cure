package net.pascalbrandt.dsm.rule.impl;

import java.util.Date;
import java.util.List;

import weka.core.Attribute;
import weka.core.Instance;
import net.pascalbrandt.dsm.RegaService;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.TestResult;

public class PreResistanceTestingViralLoadRuleImpl extends Rule {

    public PreResistanceTestingViralLoadRuleImpl(RuleService rs) {
        super(rs);
    }

    @Override
    public void setAttributeValue(Attribute attribute, Instance instance, Patient patient) {
        RegaService rs = ruleService.getRegaService();
        
        List<TestResult> VLResults = rs.getListOfTestResultsSortedByDate(RegaService.VIRAL_LOAD_TEST_ID,
                patient.getPatientIi(), RegaService.SORT_ORDER_DESC);

        Date sequenceDate = rs.getFirstSequenceDate(patient);

        if (sequenceDate == null || VLResults.size() == 0)
            return; // We have no sequence date or no CD4 counts

        Double currentPreResistanceVL = null;
        Date currentPreResistanceVLDate = null;

        for (TestResult tr : VLResults) {

            String strVal = tr.getValue();
            
            if(strVal.contains("<") || strVal.contains("=")) {
                strVal = strVal.substring(1); // get rid of the first character
            }
            
            Double tempResult = Double.parseDouble(strVal);
            Date tempDate = tr.getTestDate();

            // Calculate date difference in days
            long diffInDays = Math.abs((sequenceDate.getTime() - tempDate.getTime())) / (1000 * 60 * 60 * 24);

            if (currentPreResistanceVL == null) {
                // We don't have a value yet

                if ((tempDate.before(sequenceDate) && diffInDays <= RegaService.THIRTEEN_MONTHS_IN_DAYS)
                        || (tempDate.after(sequenceDate) && diffInDays <= RegaService.FOUR_WEEKS_IN_DAYS)) {
                    // The value is valid
                    currentPreResistanceVL = tempResult;
                    currentPreResistanceVLDate = tempDate;
                }
            } else {
                // We have a value

                if (tempDate.before(sequenceDate)) {
                    // The temp value is before the init date

                    // If the temp test result closer to the init date
                    if (tempDate.after(currentPreResistanceVLDate)) {
                        // The result is closer to the init date
                        currentPreResistanceVL = tempResult;
                        currentPreResistanceVLDate = tempDate;
                    }

                } else {
                    // The temp value is after the init date

                    // Only consider replacing the value if the current value is
                    // also after the init date
                    if (currentPreResistanceVLDate.after(sequenceDate)) {
                        // Is the temp value closer to the init date?

                        if (tempDate.before(currentPreResistanceVLDate)) {
                            // The result is closer to the init date
                            currentPreResistanceVL = tempResult;
                            currentPreResistanceVLDate = tempDate;
                        }
                    }
                }
            }
        }

        if (currentPreResistanceVL != null) {
            logger.info("Pre-Restance Test VL for " + patient.getPatientId() + " is " + currentPreResistanceVL
                    + " [Sequence Date: " + sequenceDate + "]");

            instance.setValue(attribute, currentPreResistanceVL);
        }
    }

}
