package net.pascalbrandt.dsm.rule.impl;

import org.springframework.util.StringUtils;

import weka.core.Attribute;
import weka.core.Instance;
import net.pascalbrandt.dsm.AttributeFactory;
import net.pascalbrandt.dsm.ClinicalAttributeFactory;
import net.pascalbrandt.dsm.OtherAttributeFactory;
import net.pascalbrandt.dsm.RegaService;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.PatientAttributeValue;

public class OtherComorbiditiesRuleImpl extends Rule {

	public OtherComorbiditiesRuleImpl(RuleService rs) {
		super(rs);
	}

	@Override
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient) {
		
		RegaService rs = ruleService.getRegaService();

		PatientAttributeValue pav;

		pav = rs.getPatientAttributeValue(patient, OtherAttributeFactory.OTHER_REGA_ATTRIBUTE_OTHER_COMORBIDITIES);

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
