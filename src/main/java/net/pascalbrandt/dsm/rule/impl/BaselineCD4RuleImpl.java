package net.pascalbrandt.dsm.rule.impl;

import java.util.Date;
import java.util.List;

import net.pascalbrandt.dsm.RegaService;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.TestResult;

import org.springframework.stereotype.Component;

import weka.core.Attribute;
import weka.core.Instance;

/*
 * 1) Defined as the closest CD4 count prior to ART initiation, 
 *      up to a maximum of 7 months pre-ART initiation.
 * 2) CD4 count within 2 weeks post ART initiation if the first 
 *      definition is not met. 
 * 3) Label as ‘Baseline CD4 missing’ if 1 or 2 not met
 */
public class BaselineCD4RuleImpl extends Rule {

    public BaselineCD4RuleImpl(RuleService rs) {
        super(rs);
    }

    @Override
    public void setAttributeValue(Attribute attribute, Instance instance, Patient patient) {

        RegaService rs = ruleService.getRegaService();

        Double baselineCD4 = rs.getBaselineCD4(patient);

        if (baselineCD4 != null) {
            logger.info("Baseline CD4 for " + patient.getPatientId() + " is " + baselineCD4);

            instance.setValue(attribute, baselineCD4);
        }
    }
}
