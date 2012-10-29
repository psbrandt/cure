package net.pascalbrandt.dsm.rule.impl;

import java.util.StringTokenizer;

import weka.core.Attribute;
import weka.core.Instance;
import net.pascalbrandt.dsm.ClinicalAttributeFactory;
import net.pascalbrandt.dsm.RegaService;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.PatientAttributeValue;

/*
 * 11/3 -- pretty sure this is a typo, but will fix in code for future DB dumps
 * 12 
 * 14.9
 * N/A
 */
public class RecentBloodHBRuleImpl extends Rule {

    public RecentBloodHBRuleImpl(RuleService rs) {
        super(rs);
    }

    @Override
    public void setAttributeValue(Attribute attribute, Instance instance, Patient patient) {
        RegaService rs = ruleService.getRegaService();

        PatientAttributeValue pav = rs.getPatientAttributeValue(patient,
                ClinicalAttributeFactory.CLINICAL_REGA_ATTRIBUTE_RECENT_BLOOD_HB);

        if (pav == null)
            return; // The is no recent blood hb for this patient

        String strVal = pav.getValue();
        Double dVal;

        if (strVal.equals("N/A")) {
            return; // There is no value
        } else if (strVal.contains("/")) {
            dVal = Double.parseDouble(strVal.replace('/', '.'));
        } else {
            dVal = Double.parseDouble(strVal);
        }

        logger.info("Recent Blood HB for " + patient.getPatientId() + " is " + dVal);
        instance.setValue(attribute, dVal);
    }

}
