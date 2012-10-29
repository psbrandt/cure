package net.pascalbrandt.dsm.rule.impl;

import java.util.StringTokenizer;

import net.pascalbrandt.dsm.ClinicalAttributeFactory;
import net.pascalbrandt.dsm.RegaService;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.PatientAttributeValue;
import weka.core.Attribute;
import weka.core.Instance;

/*
 * Possible Values:
 *      >60
 *      129.42
 *      83
 *      106-141
 */
public class RecentBloodCCRuleImpl extends Rule {

    public RecentBloodCCRuleImpl(RuleService rs) {
        super(rs);
    }

    @Override
    public void setAttributeValue(Attribute attribute, Instance instance, Patient patient) {
        RegaService rs = ruleService.getRegaService();
        
        PatientAttributeValue pav = rs.getPatientAttributeValue(patient, ClinicalAttributeFactory.CLINICAL_REGA_ATTRIBUTE_RECENT_BLOOD_CC);
        
        if(pav == null)
            return; // The is no recent blood cc for this patient
        
        String strVal = pav.getValue();
        Double dVal;
        
        if (strVal.contains(">")) {
            dVal = Double.parseDouble(strVal.substring(1));
        } else if (strVal.contains("-")) {
            StringTokenizer st = new StringTokenizer(strVal, "-");
            
            Double low = Double.parseDouble(st.nextToken());
            Double high = Double.parseDouble(st.nextToken());
            
            dVal = (high + low) / 2.0;
        } else {
            dVal = Double.parseDouble(strVal);
        }
        
        logger.info("Recent Blood CC for " + patient.getPatientId() + " is " + dVal);
        instance.setValue(attribute, dVal);
    }

}
