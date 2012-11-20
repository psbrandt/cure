package net.pascalbrandt.dsm.rule.impl;

import java.util.StringTokenizer;

import org.springframework.util.StringUtils;

import weka.core.Attribute;
import weka.core.Instance;
import net.pascalbrandt.dsm.AttributeFactory;
import net.pascalbrandt.dsm.ClinicalAttributeFactory;
import net.pascalbrandt.dsm.RegaService;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.PatientAttributeValue;

public class OtherDrugRuleImpl extends Rule {

	public OtherDrugRuleImpl(RuleService rs) {
		super(rs);
	}

	@Override
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient) {
		RegaService rs = ruleService.getRegaService();

		PatientAttributeValue pav;

		if(attribute.name().equals(ClinicalAttributeFactory.CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_1)) {
			pav = rs.getPatientAttributeValue(patient, ClinicalAttributeFactory.CLINICAL_REGA_ATTRIBUTE_OTHER_DRUG_1);
		} else if(attribute.name().equals(ClinicalAttributeFactory.CLINICAL_PRETTY_ATTRIBUTE_OTHER_DRUG_2)) {
			pav = rs.getPatientAttributeValue(patient, ClinicalAttributeFactory.CLINICAL_REGA_ATTRIBUTE_OTHER_DRUG_2);
		} else {
			pav = rs.getPatientAttributeValue(patient, ClinicalAttributeFactory.CLINICAL_REGA_ATTRIBUTE_OTHER_DRUG_3);
		}

		if (pav == null) {
			instance.setValue(attribute, AttributeFactory.ATTRIBUTE_VALUE_NO);
			logger.info(attribute.name() + " value for " + patient.getPatientId() + " is [NO]");
			return; // The is no Other Drug value for this patient
		}	
			
		String strVal = pav.getValue();

		if (StringUtils.hasLength(strVal) && !strVal.toUpperCase().equals("NO")) {
			instance.setValue(attribute, AttributeFactory.ATTRIBUTE_VALUE_YES);
			logger.info(attribute.name() + " value for " + patient.getPatientId() + " is [YES]");
			return;
		}

		instance.setValue(attribute, AttributeFactory.ATTRIBUTE_VALUE_NO);
		logger.info(attribute.name() + " value for " + patient.getPatientId() + " is [NO]");
	}

}
