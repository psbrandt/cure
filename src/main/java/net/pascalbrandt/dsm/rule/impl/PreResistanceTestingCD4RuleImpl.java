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

/*
 * 1) Defined as the closest CD4 count test up to 13 months from the resistance test date
 * 2) A CD4 count test within 4 weeks post-resistance testing if definition 1 is not met.
 * 3) Label as ‘PRT CD4 missing’ or ‘VL unavailable’ if 1 or 2 not met
 */
public class PreResistanceTestingCD4RuleImpl extends Rule {

    public PreResistanceTestingCD4RuleImpl(RuleService rs) {
        super(rs);
    }

    @Override
    public void setAttributeValue(Attribute attribute, Instance instance, Patient patient) {
        RegaService rs = ruleService.getRegaService();

        TestResult prtCD4 = rs.getPreResistanceCD4(patient);
        
        if (prtCD4 != null) {
            Double preResistanceCD4 = Double.parseDouble(prtCD4.getValue());
            
            logger.info("Pre-Restance Test CD4 for " + patient.getPatientId() + " is " + preResistanceCD4);

            instance.setValue(attribute, preResistanceCD4);
        }
    }

}
