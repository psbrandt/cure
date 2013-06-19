package net.pascalbrandt.dsm.rule.impl;

import weka.core.Attribute;
import weka.core.Instance;
import net.pascalbrandt.dsm.ClinicalAttributeFactory;
import net.pascalbrandt.dsm.RuleService;
import net.pascalbrandt.dsm.rule.Rule;
import net.sf.regadb.db.Patient;

public class VirusEverSuppressedRuleImpl extends Rule {

	public VirusEverSuppressedRuleImpl(RuleService rs) {
		super(rs);
	}

	@Override
	public void setAttributeValue(Attribute attribute, Instance instance,
			Patient patient) {
		
		if (ruleService.getRegaService().virusEverSuppressed(patient)) {
			instance.setValue(attribute, ClinicalAttributeFactory.ATTRIBUTE_VALUE_YES);
		} else {
			instance.setValue(attribute, ClinicalAttributeFactory.ATTRIBUTE_VALUE_NO);
		}
	}

}
